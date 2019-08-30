package cn.appsys.service.backend;

import cn.appsys.dao.backenduser.BackEndUserMapper;
import cn.appsys.pojo.BackendUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author 小未来leo
 * @date 2019/8/27 14:02
 * @params
 * @return
 */
@Service
@Transactional
public class BackEndUserServiceImpl implements BackEndUserService {
    @Resource
    private BackEndUserMapper backEndUserMapper;
    @Override
    public BackendUser login(String userCode, String userPassword) throws Exception {
        BackendUser backendUser=backEndUserMapper.getLoginUser(userCode);
        if (backendUser!=null){
            if (backendUser.getUserPassword().equals(userPassword)){
                return backendUser;
            }
        }
        return null;
    }
}
