package cn.appsys.service.developer;

import cn.appsys.dao.appinfo.AppInfoMapper;
import cn.appsys.dao.appversion.AppVersionMapper;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import com.mysql.jdbc.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * @author 小未来leo
 * @date 2019/8/23 14:15
 * @params
 * @return
 */
@Transactional
@Service
public class AppInfoServiceImpl implements AppInfoService {
    @Resource
    private AppInfoMapper appInfoMapper;
    @Resource
    private AppVersionMapper appVersionMapper;
    @Override
    public List<AppInfo> getAppInfoList(String querySoftwareName, Integer queryStatus, Integer queryCategoryLevel1, Integer queryCategoryLevel2, Integer queryCategoryLevel3, Integer queryFlatformId, Integer devId, Integer currentPageNo, Integer pageSize) throws Exception {
        return appInfoMapper.getAppInfoList(querySoftwareName,queryStatus,queryCategoryLevel1,queryCategoryLevel2,queryCategoryLevel3,queryFlatformId,devId,currentPageNo,pageSize);
    }

    @Override
    public int getAppInfoCount(String querySoftwareName, Integer queryStatus, Integer queryCategoryLevel1, Integer queryCategoryLevel2, Integer queryCategoryLevel3, Integer queryFlatformId, Integer devId) throws Exception {
        return appInfoMapper.getAppInfoCount(querySoftwareName,queryStatus,queryCategoryLevel1,queryCategoryLevel2,queryCategoryLevel3,queryFlatformId,devId);
    }

    @Override
    public AppInfo getAppInfoByAPKName(String APKName,Integer id) {
        return appInfoMapper.getAppInfoByAPKName(APKName,id);
    }

    @Override
    public boolean addAppInfo(AppInfo appInfo) {
        boolean flag=false;
        if (appInfoMapper.addAppInfo(appInfo)>0)flag=true;
        return flag;
    }

    @Override
    public boolean deleteAppLogo(Integer id) {
        if (appInfoMapper.deleteAppLogo(id)>0)return true;
        return false;
    }

    @Override
    public boolean modify(AppInfo appInfo) {
        if (appInfoMapper.modify(appInfo)>0)return  true;
        return false;
    }

    @Override
    public boolean appsysdeleteAppById(Integer id)throws Exception{
        boolean flag=false;
        int versionCount=appVersionMapper.getVersionCountByAppId(id);
        List<AppVersion> appVersionList=null;
        if (versionCount>0){
            //删除上传的apk文件
            appVersionList=appVersionMapper.getAppVersionList(id);
            for(AppVersion appVersion:appVersionList){
                if (appVersion.getApkLocPath()!=null&&!"".equals(appVersion.getApkLocPath())){
                    File file=new File(appVersion.getApkLocPath());
                    if (file.exists()){
                        if (!file.delete()){
                            throw new Exception();
                        }
                    }
                }
            }
            appVersionMapper.deleteVersionByAppId(id);
        }
        AppInfo appInfo=appInfoMapper.getAppInfoByAPKName(null,id);
        if (!StringUtils.isNullOrEmpty(appInfo.getLogoLocPath())){
            File file=new File(appInfo.getLogoLocPath());
            if (file.exists()){
                if (!file.delete()){
                    throw new Exception();
                }
            }
        }
        if (appInfoMapper.deleteAppInfoById(id)>0){
            flag=true;
        }
        return flag;
    }

    @Override
    public boolean appsysUpdateSaleStatusByAppId(AppInfo appInfo) throws Exception {
        /*
		 * 上架：
			1 更改status由【2 or 5】 to 4 ， 上架时间
			2 根据versionid 更新 publishStauts 为 2

			下架：
			更改status 由4给为5
		 */
        Integer operator=appInfo.getModifyBy();
        if (operator<0||appInfo.getId()<0){
            throw new Exception();
        }

        AppInfo appInfo1=appInfoMapper.getAppInfoByAPKName(null,appInfo.getId());
        if (null==appInfo1){
            return false;
        }else{
            switch (appInfo1.getStatus()){
                case 2: //当状态为审核通过时，可以进行上架操作
                    onSale(appInfo,operator,4,2);
                    break;
                case 5://当状态为下架时，可以进行上架操作
                    onSale(appInfo,operator,4,2);
                    break;
                case 4://当状态为上架时，可以进行下架操作
                    offSale(appInfo,operator,5);
                    break;

                default:
                    return false;
            }
        }
        return true;
    }
    private void onSale(AppInfo appInfo,Integer operator,Integer appInfStatus,Integer versionStatus) throws Exception{
        offSale(appInfo,operator,appInfStatus);
        setSaleSwitchToAppVersion(appInfo,operator,versionStatus);
    }
    private boolean offSale(AppInfo appInfo,Integer operator,Integer appInfStatus) throws Exception{
        AppInfo _appInfo = new AppInfo();
        _appInfo.setId(appInfo.getId());
        _appInfo.setStatus(appInfStatus);
        _appInfo.setModifyBy(operator);
        _appInfo.setOffSaleDate(new Date(System.currentTimeMillis()));
        appInfoMapper.modify(_appInfo);
        return true;
    }
    private boolean setSaleSwitchToAppVersion(AppInfo appInfo,Integer operator,Integer saleStatus) throws Exception{
        AppVersion appVersion = new AppVersion();
        appVersion.setId(appInfo.getVersionId());
        appVersion.setPublishStatus(saleStatus);
        appVersion.setModifyBy(operator);
        appVersion.setModifyDate(new Date(System.currentTimeMillis()));
        appVersionMapper.modify(appVersion);
        return false;
    }
}
