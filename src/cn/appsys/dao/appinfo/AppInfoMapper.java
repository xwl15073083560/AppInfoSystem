package cn.appsys.dao.appinfo;

import cn.appsys.pojo.AppInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 小未来leo
 * @date 2019/8/23 11:29
 * @params
 * @return
 */
public interface AppInfoMapper {
    public List<AppInfo> getAppInfoList(@Param(value="softwareName")String querySoftwareName,
                                        @Param(value="status")Integer queryStatus,
                                        @Param(value="categoryLevel1")Integer queryCategoryLevel1,
                                        @Param(value="categoryLevel2")Integer queryCategoryLevel2,
                                        @Param(value="categoryLevel3")Integer queryCategoryLevel3,
                                        @Param(value="flatformId")Integer queryFlatformId,
                                        @Param(value="devId")Integer devId,
                                        @Param(value="from")Integer currentPageNo,
                                        @Param(value="pageSize")Integer pageSize)throws Exception;
    public int getAppInfoCount(@Param(value="softwareName")String querySoftwareName,
                               @Param(value="status")Integer queryStatus,
                               @Param(value="categoryLevel1")Integer queryCategoryLevel1,
                               @Param(value="categoryLevel2")Integer queryCategoryLevel2,
                               @Param(value="categoryLevel3")Integer queryCategoryLevel3,
                               @Param(value="flatformId")Integer queryFlatformId,
                               @Param(value="devId")Integer devId)throws Exception;
    public AppInfo getAppInfoByAPKName(@Param(value = "APKName")String APKName,@Param(value = "id")Integer id);
    public int addAppInfo(AppInfo appInfo);
    public int deleteAppLogo(@Param("id")Integer id);
    public  int modify(AppInfo appInfo);
    public int updateVersionId(@Param(value="versionId")Integer versionId,@Param(value="id")Integer appId);
    public int deleteAppInfoById(@Param(value="id")Integer delId);
    public int updateSatus(@Param(value="status")Integer status,@Param(value="id")Integer id)throws Exception;
}
