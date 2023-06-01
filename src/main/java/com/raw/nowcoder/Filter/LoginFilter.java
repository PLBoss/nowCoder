package com.raw.nowcoder.Filter;


import com.raw.nowcoder.entity.LoginTicket;
import com.raw.nowcoder.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter("/*")
public class LoginFilter implements Filter {


    @Autowired
    private userService userService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
       //获取用户登产生的token,然后利用token查询登陆token表，存在就代表登陆过，直接放行，不存再就表示没有登陆
        //这里针对静态资源予以放行

        String[] urls={"/login","/register","/kapthca","/static","/js","/css"};

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String url = req.getRequestURL().toString();

        for (String s : urls) {


            if(url.contains(s)){
                filterChain.doFilter(servletRequest,servletResponse);
                return;

            }

        }

        //判断用户是否登陆

        Cookie[] cookies = req.getCookies();

        for (Cookie cookie : cookies) {

            if(cookie.getName().equals("ticket")){

                String ticket = cookie.getValue();

                LoginTicket loginTicket = userService.selectTicke(ticket);

                if(loginTicket==null){
                    req.setAttribute("error_Msg","请登陆后尝试");
                    req.getRequestDispatcher("/login").forward(req,servletResponse);
                    return;
                }

                req.setAttribute("LoginUser","User");

                filterChain.doFilter(servletRequest,servletResponse);
                return;


            }

        }

        response.sendRedirect("/login");








    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
