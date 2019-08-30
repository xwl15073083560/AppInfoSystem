package cn.appsys.service.backend;

import cn.appsys.dao.appinfo.AppInfoMapper;
import cn.appsys.pojo.AppInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 小未来leo
 * @date 2019/8/29 8:12
 * @params
 * @return
 */
@Service
@Transactional
public class AppServiceImpl implements AppService {
    @Resource
    private AppInfoMapper appInfoMapper;
    @Override
    public List<AppInfo> getAppInfoList(String querySoftwareName, Integer queryCategoryLevel1, Integer queryCategoryLevel2, Integer queryCategoryLevel3, Integer queryFlatformId, Integer currentPageNo, Integer pageSize) throws Exception {
        return appInfoMapper.getAppInfoList(querySoftwareName,1,queryCategoryLevel1,queryCategoryLevel2,queryCategoryLevel3,queryFlatformId,null,(currentPageNo-1)*pageSize,pageSize);
    }

    @Override
    public int getAppInfoCount(String querySoftwareName, Integer queryCategoryLevel1, Integer queryCategoryLevel2, Integer queryCategoryLevel3, Integer queryFlatformId) throws Exception {
        return appInfoMapper.getAppInfoCount(querySoftwareName,1,queryCategoryLevel1,queryCategoryLevel2,queryCategoryLevel3,queryFlatformId,null);
    }

    @Override
    public AppInfo getAppInfo(Integer id) throws Exception {
        return appInfoMapper.getAppInfoByAPKName(null,id);
    }

    @Override
    public boolean updateStatus(Integer status, Integer id) throws Exception {
        return appInfoMapper.updateSatus(status,id)>0?true:false;
    }
}
