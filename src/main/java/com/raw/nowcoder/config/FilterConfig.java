package com.raw.nowcoder.config;

import com.raw.nowcoder.Filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    public FilterRegistrationBean registFilter(){
        FilterRegistrationBean filterRegistration=new FilterRegistrationBean();
        filterRegistration.setFilter(new LoginFilter());
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.setName("LoginFilter");

        return  filterRegistration;
    }

}
