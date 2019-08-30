package cn.appsys.service.backend;

import cn.appsys.pojo.AppInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 小未来leo
 * @date 2019/8/28 20:22
 * @params
 * @return
 */
public interface AppService {
    /**
     * 根据条件查询出待审核的APP列表并分页显示
     * @param querySoftwareName
     * @param queryCategoryLevel1
     * @param queryCategoryLevel2
     * @param queryCategoryLevel3
     * @param queryFlatformId
     * @param currentPageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    List<AppInfo> getAppInfoList(@Param(value="softwareName")String querySoftwareName,
                                 @Param(value="categoryLevel1")Integer queryCategoryLevel1,
                                 @Param(value="categoryLevel2")Integer queryCategoryLevel2,
                                 @Param(value="categoryLevel3")Integer queryCategoryLevel3,
                                 @Param(value="flatformId")Integer queryFlatformId,
                                 @Param(value="from")Integer currentPageNo,
                                 @Param(value="pageSize")Integer pageSize)throws Exception;
    /**
     * 查询出待审核（status=1）的APP数量
     * @param querySoftwareName
     * @param queryCategoryLevel1
     * @param queryCategoryLevel2
     * @param queryCategoryLevel3
     * @param queryFlatformId
     * @return
     * @throws Exception
     */
     int getAppInfoCount(@Param(value="softwareName")String querySoftwareName,
                               @Param(value="categoryLevel1")Integer queryCategoryLevel1,
                               @Param(value="categoryLevel2")Integer queryCategoryLevel2,
                               @Param(value="categoryLevel3")Integer queryCategoryLevel3,
                               @Param(value="flatformId")Integer queryFlatformId)throws Exception;
    /**
     * 根据id获取app详细信息
     * @param id
     * @return
     * @throws Exception
     */
    AppInfo getAppInfo(@Param(value = "id")Integer id)throws Exception;
    /**
     * 根据id更新app的satus
     * @param status
     * @param id
     * @return
     * @throws Exception
     */
    boolean updateStatus(@Param(value = "status")Integer status,@Param(value = "id")Integer id)throws Exception;
}
