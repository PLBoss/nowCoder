package com.raw.nowcoder.controller.intercepter;

import com.raw.nowcoder.Util.CookieUtil;
import com.raw.nowcoder.Util.HostHolder;
import com.raw.nowcoder.entity.LoginTicket;
import com.raw.nowcoder.entity.User;
import com.raw.nowcoder.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class LoginTicketIntercepter implements HandlerInterceptor {

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private userService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //拦截

        String ticket = CookieUtil.getCookie(request, "ticket");

//        if (ticket==null) {
//            response.sendRedirect(request.getContextPath()+"/login");
//
//            return false;
//        }

        if(ticket!=null){
            LoginTicket loginTicket = userService.selectTicke(ticket);

            if(loginTicket!=null&&loginTicket.getStatus()==0&&loginTicket.getExpired().after(new Date())){

                User user = userService.selectUserById(loginTicket.getUserId().toString());

                hostHolder.setUsers(user);

            }else {
                response.sendRedirect(request.getContextPath()+"/login");

                return false;
            }


        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //验证用户是否登陆
        User user = hostHolder.getUsers();

        if(user!=null&&modelAndView!=null){
            modelAndView.addObject("loginUser",user);
        }



    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
       hostHolder.clear();
    }
}
