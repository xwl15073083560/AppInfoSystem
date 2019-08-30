package cn.appsys.dao.devuser;

import cn.appsys.pojo.DevUser;
import org.apache.ibatis.annotations.Param;

/**
 * @author 小未来leo
 * @date 2019/8/21 16:50
 * @params
 * @return
 */
public interface DevUserMapper {
    /**
     * 根据devCode获取用户记录
     * @param devCode
     * @return
     */
    DevUser getLoginUser(@Param("devCode")String devCode);
}
