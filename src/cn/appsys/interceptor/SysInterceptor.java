package cn.appsys.interceptor;

import cn.appsys.pojo.DevUser;
import cn.appsys.tools.Constants;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import cn.appsys.pojo.BackendUser;

/**
 * @author 小未来leo
 * @date 2019/8/21 10:29
 * @params
 * @return
 */
public class SysInterceptor extends HandlerInterceptorAdapter {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler)throws Exception{
        HttpSession session=request.getSession();
        BackendUser backendUser=(BackendUser)session.getAttribute(Constants.USER_SESSION);
        DevUser devUser=(DevUser)session.getAttribute(Constants.DEV_USER_SESSION);
        if (devUser!=null){
            return true;
        }else if (backendUser!=null){
            return true;
        }else{
            response.sendRedirect(request.getContextPath()+"/403.jsp");
            return false;
        }
    }
}
