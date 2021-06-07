package wx.filter;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import wx.poj.Admin;
import wx.poj.Doctor;
import wx.poj.User;

public class LoginIntercepter implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // TODO Auto-generated method stub
        String requestURI = request.getRequestURI();
        if(requestURI.indexOf("/admin")>=0){
            HttpSession session = request.getSession();
            Admin admin= (Admin) session.getAttribute("admin");
            if(admin!=null){
                return true;
            }else{
                return false;
            }
        }


        if(requestURI.indexOf("/order")>=0){
            HttpSession session = request.getSession();
            User user= (User) session.getAttribute("user");
            if(user!=null){
                return true;
            }else{
                return false;
            }
        }
        if(requestURI.indexOf("/authentication")>=0){
            HttpSession session = request.getSession();
            Doctor doctor= (Doctor) session.getAttribute("doctor");
            if(doctor!=null){
                return true;
            }else{
                return false;
            }
        }

        if(requestURI.indexOf("/ask")>=0||requestURI.indexOf("/message")>=0){
            HttpSession session = request.getSession();
            User user= (User) session.getAttribute("user");
            Doctor doctor= (Doctor) session.getAttribute("doctor");
            if(user!=null||doctor!=null){
                return true;
            }else{
                return false;
            }
        }

        if(requestURI.indexOf("/login")<=0){
            HttpSession session = request.getSession();
            User user= (User) session.getAttribute("user");
            Doctor doctor= (Doctor) session.getAttribute("doctor");
            Admin admin= (Admin) session.getAttribute("admin");
            if(user!=null||doctor!=null||admin!=null){
                return true;
            }else{
                return false;
            }
        }else{
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}
