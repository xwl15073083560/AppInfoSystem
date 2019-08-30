package cn.appsys.dao.appversion;

import cn.appsys.pojo.AppVersion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 小未来leo
 * @date 2019/8/23 11:38
 * @params
 * @return
 */
public interface AppVersionMapper {
    public List<AppVersion> getAppVersionList(@Param("appId")Integer appId) throws Exception;
    public int add(AppVersion appVersion);
    public AppVersion getAppVersionById(@Param("id")Integer id);
    public int modify(AppVersion appVersion);
    public int deleteApkFile(@Param("id")Integer id);
    public int deleteVersionByAppId(@Param("appId")Integer appId);
    public int getVersionCountByAppId(@Param("appId")Integer appId)throws Exception;
}
