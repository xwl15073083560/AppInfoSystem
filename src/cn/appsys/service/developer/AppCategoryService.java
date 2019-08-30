package cn.appsys.service.developer;

import cn.appsys.pojo.AppCategory;

import java.util.List;

/**
 * @author 小未来leo
 * @date 2019/8/23 13:36
 * @params
 * @return
 */
public interface AppCategoryService {
    /**
     * 根据父节点parentId获取相应的分类列表
     * @param parentId
     * @return
     * @throws Exception
     */
    public List<AppCategory> getAppCategoryListByParentId(Integer parentId)throws Exception;
}
