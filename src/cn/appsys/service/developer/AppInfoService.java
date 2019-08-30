package cn.appsys.service.developer;

import cn.appsys.pojo.AppInfo;

import java.util.List;

/**
 * @author 小未来leo
 * @date 2019/8/23 13:35
 * @params
 * @return
 */
public interface AppInfoService {
    /**
     * 根据条件查询出app列表
     * @param querySoftwareName
     * @param queryStatus
     * @param queryCategoryLevel1
     * @param queryCategoryLevel2
     * @param queryCategoryLevel3
     * @param queryFlatformId
     * @param devId
     * @param currentPageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<AppInfo> getAppInfoList(String querySoftwareName, Integer queryStatus,
                                        Integer queryCategoryLevel1, Integer queryCategoryLevel2,
                                        Integer queryCategoryLevel3, Integer queryFlatformId,
                                        Integer devId, Integer currentPageNo, Integer pageSize)throws Exception;

    /**
     * 根据条件查询appInfo表记录数
     * @param querySoftwareName
     * @param queryStatus
     * @param queryCategoryLevel1
     * @param queryCategoryLevel2
     * @param queryCategoryLevel3
     * @param queryFlatformId
     * @param devId
     * @return
     * @throws Exception
     */
    public int getAppInfoCount(String querySoftwareName,Integer queryStatus,
                               Integer queryCategoryLevel1,Integer queryCategoryLevel2,
                               Integer queryCategoryLevel3,Integer queryFlatformId,Integer devId)throws Exception;

    /**
     * 根据APKName查询是否存在
     * @param APKName
     * @return
     */
    public AppInfo getAppInfoByAPKName(String APKName,Integer id);

    /**
     * 新增APPInfo
     * @param appInfo
     * @return
     */
    public boolean addAppInfo(AppInfo appInfo);

    /**
     * 根据Appinfo id修改清空2个路径
     * @param id
     * @return
     */
    public boolean deleteAppLogo(Integer id);
    public boolean modify(AppInfo appInfo);
    public boolean appsysdeleteAppById(Integer id)throws Exception;
    public boolean appsysUpdateSaleStatusByAppId(AppInfo appInfo) throws Exception;
}
