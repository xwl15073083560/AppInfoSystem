package cn.appsys.controller.backend;

import cn.appsys.controller.BaseController;
import cn.appsys.pojo.BackendUser;
import cn.appsys.service.backend.BackEndUserService;
import cn.appsys.tools.Constants;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * @author 小未来leo
 * @date 2019/8/22 11:09
 * @params
 * @return
 */
@Controller
@RequestMapping(value = "/manager")
public class UserLoginController  extends BaseController {
    private Logger logger = Logger.getLogger(UserLoginController.class);
    @Resource
    private BackEndUserService backEndUserService;

    @RequestMapping(value = "/login")
    public Object login(){
        logger.debug("LoginController welcome AppInfoSystem backend==================");
        return "backendlogin";
    }

    @RequestMapping(value = "/dologin",method = RequestMethod.POST)
    public Object doLogin(@RequestParam(value = "userCode")String userCode,
                          @RequestParam(value = "userPassword")String userPassword){
        BackendUser user=null;
        try {
            user=backEndUserService.login(userCode,userPassword);
            if (user!=null){
                session.setAttribute(Constants.USER_SESSION,user);
                return "redirect:/manager/backend/main";
            }else{
                request.setAttribute("error", "用户名或密码不正确");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "backendlogin";
    }
    @RequestMapping(value = "/backend/main")
    public Object main(){
        if (null!=session.getAttribute(Constants.USER_SESSION)){
            return "backend/main";
        }
        return "redirect:/manager/login";
    }

    @RequestMapping(value = "/logout")
    public Object logout(){
        session.removeAttribute(Constants.USER_SESSION);
        return "backendlogin";
    }
}
