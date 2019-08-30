package cn.appsys.dao.appcategory;

import cn.appsys.pojo.AppCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 小未来leo
 * @date 2019/8/23 11:28
 * @params
 * @return
 */
public interface AppCategoryMapper {
    public List<AppCategory> getAppCategoryListByParentId(@Param("parentId")Integer parentId)throws Exception;
}
