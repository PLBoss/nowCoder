package com.raw.nowcoder.Util;


import org.springframework.util.DigestUtils;

import java.util.UUID;


public class StringUtil {

    public static String MD5(String s){

        return DigestUtils.md5DigestAsHex(s.getBytes());
    }

    public static String gennerUUID(){

        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
