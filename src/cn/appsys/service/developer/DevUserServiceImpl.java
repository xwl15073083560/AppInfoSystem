package cn.appsys.service.developer;

import cn.appsys.dao.devuser.DevUserMapper;
import cn.appsys.pojo.DevUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author 小未来leo
 * @date 2019/8/21 16:58
 * @params
 * @return
 */
@Transactional
@Service
public class DevUserServiceImpl implements DevUserService {
    @Resource
    private DevUserMapper devUserMapper;
    @Override
    public DevUser login(String devCode, String password) {
        DevUser user=null;
        user=devUserMapper.getLoginUser(devCode);
        if (null!=user){
            if (!user.getDevPassword().equals(password)){
                user=null;
            }
        }
        return user;
    }
}
