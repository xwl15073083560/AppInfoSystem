package cn.appsys.service.backend;

import cn.appsys.pojo.BackendUser;

/**
 * @author 小未来leo
 * @date 2019/8/27 14:01
 * @params
 * @return
 */
public interface BackEndUserService {
    BackendUser login(String userCode,String userPassword)throws Exception;
}
