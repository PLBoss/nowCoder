package com.raw.nowcoder.controller.intercepter;

import com.raw.nowcoder.Util.HostHolder;
import com.raw.nowcoder.annotation.LoginRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {


    @Autowired
    private HostHolder hostHolder;

    /*
    * 用于校验是否已经登陆，通过拦截所有请求，然后获取该方法，判断该方法是否有需要登陆的注解，然后在判断该
    * 请求是否拥有用户身份令牌，没有的话就需要登陆了才能访问
    * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {

            HandlerMethod handlerMethod= (HandlerMethod) handler;

            Method method = handlerMethod.getMethod();//获取来拦截到的方法，然后获取他的注解

            LoginRequired annotation = method.getAnnotation(LoginRequired.class);


            if(annotation!=null&&hostHolder.getUsers()==null){

                response.sendRedirect(request.getContextPath()+"/login");
                return  false;
            }


        }



        return true;
    }
}
