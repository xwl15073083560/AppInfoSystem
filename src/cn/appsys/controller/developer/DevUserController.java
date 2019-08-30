package cn.appsys.controller.developer;

import cn.appsys.controller.BaseController;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.developer.DevUserService;
import cn.appsys.tools.Constants;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * @author 小未来leo
 * @date 2019/8/21 17:02
 * @params
 * @return
 */
@Controller
@RequestMapping(value = "/dev")
public class DevUserController extends BaseController {
    @Resource
    private DevUserService service;
    private Logger log=Logger.getLogger(DevUserController.class);

    /**
     * 进入开发者登录页面
     * @return
     */
    @RequestMapping(value = "/login")
    public Object login(){
        log.debug("LoginController welcome AppInfoSys developer============");
        return "devlogin";
    }

    /**
     * 验证账号密码是否正确
     * @param devCode
     * @param devPassword
     * @return
     */
    @RequestMapping(value = "/dologin")
    public Object dologin(@RequestParam String devCode,@RequestParam String devPassword){
        DevUser user=service.login(devCode,devPassword);
        if (null!=user){
            session.setAttribute(Constants.DEV_USER_SESSION,user);
            return "redirect:/dev/flatform/main";
        }else{
            request.setAttribute("error","用户名或密码不正确！");
            return "devlogin";
        }
    }

    /**
     * 判断是否登录
     * @return
     */
    @RequestMapping(value="/flatform/main")
    public Object main(){
        if (session.getAttribute(Constants.DEV_USER_SESSION)==null){
            return "redirect:/dev/login";
        }
        return "devoloper/main";
    }

    /**
     * 注销
     * @return
     */
    @RequestMapping(value = "/logout")
    public Object logout(){
        session.removeAttribute(Constants.DEV_USER_SESSION);
        return "devlogin";
    }

}
