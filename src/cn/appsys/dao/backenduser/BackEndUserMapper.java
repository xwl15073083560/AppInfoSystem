package cn.appsys.dao.backenduser;

import cn.appsys.pojo.BackendUser;
import org.apache.ibatis.annotations.Param;

/**
 * @author 小未来leo
 * @date 2019/8/27 13:51
 * @params
 * @return
 */
public interface BackEndUserMapper {
    BackendUser getLoginUser(@Param("userCode")String userCode)throws Exception;
}
