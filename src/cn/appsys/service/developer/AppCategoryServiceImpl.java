package cn.appsys.service.developer;

import cn.appsys.dao.appcategory.AppCategoryMapper;
import cn.appsys.pojo.AppCategory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 小未来leo
 * @date 2019/8/23 14:56
 * @params
 * @return
 */
@Service
@Transactional
public class AppCategoryServiceImpl implements AppCategoryService {
    @Resource
    private AppCategoryMapper appCategoryMapper;
    @Override
    public List<AppCategory> getAppCategoryListByParentId(Integer parentId) throws Exception {
        return appCategoryMapper.getAppCategoryListByParentId(parentId);
    }
}
