package com.raw.nowcoder.config;

import com.raw.nowcoder.controller.intercepter.LoginTicketIntercepter;
import com.raw.nowcoder.controller.intercepter.LoginRequiredInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class IntercepterConfig implements WebMvcConfigurer {

    @Autowired
    private LoginTicketIntercepter loginTicketIntercepter;

    @Autowired
    private LoginRequiredInterceptor loginRequiredInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //定义需要拦截的路径
        String[] addPathPatterns = {
                "/user/upload",
               "/user/setting",
                "/index",


        };
        //定义不需要拦截的路径
        String[] excludePathPatterns = {

                "/login",
                "/user/register"

        };
        //用于同于生成登陆令牌的拦截器,这个拦截器将拦截所有请求
        registry.addInterceptor(loginTicketIntercepter)
                .excludePathPatterns(excludePathPatterns);

        //用于拦截非法访问的拦截器 ，这个拦截只是拦截需要登陆的
        registry.addInterceptor(loginRequiredInterceptor)
                .excludePathPatterns(excludePathPatterns);


    }
}
