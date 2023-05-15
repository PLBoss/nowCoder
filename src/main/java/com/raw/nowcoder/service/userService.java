package com.raw.nowcoder.service;


import com.raw.nowcoder.entity.LoginTicket;
import com.raw.nowcoder.entity.User;
import com.raw.nowcoder.mapper.userMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class userService {


    @Autowired

    private userMapper userMapper;

    public Map<String,Object> login(String username, String password, String code, int expiredTime, HttpSession session){

        Map<String,Object> map=new HashMap<>();
        //先判断验证码



//
//        System.out.println(kapacha);
//        System.out.println(code);

        String kapacha = (String) session.getAttribute("kapthca");
        System.out.println(kapacha);
        System.out.println(code);
        if(StringUtils.isBlank(code)||StringUtils.isBlank(kapacha)||!code.equalsIgnoreCase(kapacha)){

            map.put("codeMsg","验证码错误");
           return  map;
        }




        //判断输入的账号密码是否为空



        if (StringUtils.isBlank(username)) {
            map.put("usernameMsg","用户名为空");
            return map;
        }

        if (StringUtils.isBlank(password)) {
            map.put("passwordMsg","密码为空");
            return map;
        }


        //根据用户名查询
        User user = userMapper.selectByName(username);

        if(user==null){
            map.put("usernameMsg","用户不存在");
            return  map;
        }

        String salt = user.getSalt();

        //md5加密，然后与数据库中的密码进行比较

        password = DigestUtils.md5DigestAsHex((password + salt).getBytes());

        String pass = user.getPassword();
        if(!pass.equals(password)){

            map.put("passwordMsg","密码错误");
            return map;
        }

        //生成token
        LoginTicket loginTicket=new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(UUID.randomUUID().toString().replace("-",""));
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis()+expiredTime*1000));
        //将数据保存到数据库中
        userMapper.saveTickt(loginTicket);


        map.put("ticket",loginTicket);

        return map;


    }
}
