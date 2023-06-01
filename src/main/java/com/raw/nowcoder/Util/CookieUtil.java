package com.raw.nowcoder.Util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtil {

    public static String getCookie(HttpServletRequest request, String name) {


        if (request == null || name == null) {

           throw new IllegalArgumentException("参数不能为空");
        }

        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {

            if (cookie.getName().equals(name)) {

                String s = cookie.getValue();

                return  s;
            }

        }

        return null;


    }

}
