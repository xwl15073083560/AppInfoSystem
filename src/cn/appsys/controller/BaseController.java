package cn.appsys.controller;

import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author 小未来leo
 * @date 2019/8/21 10:51
 * @params
 * @return
 */
public class BaseController {
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;
    @ModelAttribute
    public void set(HttpServletRequest request, HttpServletResponse response)throws  Exception {
        this.request = request;
        this.response = response;
        this.session=this.request.getSession();
        this.request.setCharacterEncoding("utf-8");
        this.response.setCharacterEncoding("utf-8");
    }
}
