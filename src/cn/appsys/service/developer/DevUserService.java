package cn.appsys.service.developer;

import cn.appsys.pojo.DevUser;

/**
 * @author 小未来leo
 * @date 2019/8/21 16:58
 * @params
 * @return
 */
public interface DevUserService {
    /**
     * 登录验证
     * @param devCode
     * @param password
     * @return
     */
    DevUser login(String devCode,String password);
}
