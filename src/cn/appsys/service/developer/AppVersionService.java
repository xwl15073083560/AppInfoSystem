package cn.appsys.service.developer;

import cn.appsys.pojo.AppVersion;

import java.util.List;

/**
 * @author 小未来leo
 * @date 2019/8/23 13:36
 * @params
 * @return
 */
public interface AppVersionService {
    /**
     * 根据appId查询相应的app版本列表
     * @param appId
     * @return
     * @throws Exception
     */
    public List<AppVersion> getAppVersionList(Integer appId)throws Exception;
    public boolean add(AppVersion appVersion);
    public AppVersion getAppVersionById(Integer id);
    public boolean modify(AppVersion appVersion);
    public boolean deleteApkFile(Integer id);
}
