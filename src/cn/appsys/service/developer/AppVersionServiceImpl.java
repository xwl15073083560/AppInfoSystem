package cn.appsys.service.developer;

import cn.appsys.dao.appinfo.AppInfoMapper;
import cn.appsys.dao.appversion.AppVersionMapper;
import cn.appsys.pojo.AppVersion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 小未来leo
 * @date 2019/8/26 11:54
 * @params
 * @return
 */
@Transactional
@Service
public class AppVersionServiceImpl implements AppVersionService {
    @Resource
    private AppVersionMapper appVersionMapper;
    @Resource
    private AppInfoMapper appInfoMapper;
    @Override
    public List<AppVersion> getAppVersionList(Integer appId) throws Exception {
        return appVersionMapper.getAppVersionList(appId);
    }

    @Override
    public boolean add(AppVersion appVersion) {
        boolean flag = false;
        Integer versionId = null;
        if(appVersionMapper.add(appVersion) > 0){
            versionId = appVersion.getId();
            flag = true;
        }
        if(appInfoMapper.updateVersionId(versionId, appVersion.getAppId()) > 0 && flag){
            flag = true;
        }
        return flag;
    }

    @Override
    public AppVersion getAppVersionById(Integer id) {
        return appVersionMapper.getAppVersionById(id);
    }

    @Override
    public boolean modify(AppVersion appVersion) {
        if (appVersionMapper.modify(appVersion)>0)return true;
        return false;
    }

    @Override
    public boolean deleteApkFile(Integer id) {
        if (appVersionMapper.deleteApkFile(id)>0)return true;
        return false;
    }
}
