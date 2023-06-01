package com.raw.nowcoder.service;


import com.raw.nowcoder.Util.StringUtil;
import com.raw.nowcoder.Util.constant;
import com.raw.nowcoder.entity.LoginTicket;
import com.raw.nowcoder.entity.User;
import com.raw.nowcoder.mapper.userMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.CookieValue;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class userService implements constant {


    @Autowired

    private userMapper userMapper;


    @Autowired
    private MailService mailService;


    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    public Map<String,Object> login(String username, String password, String code,
                                     String key,
                                    int expiredTime, HttpSession session){

        Map<String,Object> map=new HashMap<>();
        //先判断验证码

//        System.out.println(kapacha);
//        System.out.println(code);

//        String kapacha = (String) session.getAttribute("kapthca");

        //优化：从cookie中获取验证码的key,然后利用key获取对应的值
        String kapacha = redisTemplate.opsForValue().get(key);
        System.out.println(kapacha);


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
            map.put("usernameMsg","用户不存在或未激活");
            return  map;
        }

        String salt = user.getSalt();

        //md5加密，然后与数据库中的密码进行比较

        password =StringUtil.MD5(password+salt);

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


        map.put("ticket",loginTicket.getTicket());

        return map;


    }

    public User selectUserById(String userId) {

        return  userMapper.selectById(userId);
    }

    public LoginTicket selectTicke(String ticket) {
        return  userMapper.selectTicket(ticket);
    }

    public Map<String,Object> insertUser(String username, String password, String email) {
            Map<String,Object> map=new HashMap<>();

         if(username.isEmpty()){
             map.put("usernameMsg","用户名不能为空");
             return  map;
         }

        if (email.isEmpty()) {
            map.put("emailMsg","邮箱不能为空");
            return  map;
        }


        //用户已经存在，
        User user = userMapper.selectByName(username);

        if(user!=null){
            map.put("usernameMsg","用户名已存在");
            return  map;
        }

        //邮箱存在

        user= userMapper.selectByEmail(email);

        if(user!=null){

            map.put("emailMsg","邮箱已经存在");

            return map;
        }

        //生成盐码

        String salt = StringUtil.gennerUUID().substring(0, 5);

        //密码加密:注意拼接字符串的顺序要和注册一致，否则将会出现校验错误
//        System.out.println(password);
        password=StringUtil.MD5(password+salt);

        //activation_code

        //用户状态未激活0，用户类型0普通用户

        //用户头像
        String headUrl="http://images.nowcoder.com/head/"+new Random().nextInt(1000) +"t.png";

        User u=new User();
        u.setEmail(email);
        u.setStatus(UNACTIVATE);
        u.setType(USER);
        u.setUsername(username);
        u.setSalt(salt);
        u.setPassword(password);
        u.setHeaderUrl(headUrl);
        u.setCreateTime(new Date());
        u.setActivationCode(StringUtil.gennerUUID());

        userMapper.inserUser(u);



        //将用户的id查出来,出现问题，写入数据库的速度慢与程序允许你的速度，容易造成获取不到Id
        //Integer id = userMapper.selectByName(username).getId();


        //激活发送邮件,利用给的激活连接带上的激活码，如果点击的是正确的链接，激活码和该注册用户的激活码是一致的
        //0478bcc059094312a9811d6a153eaaeb


        String url=ContextPath+"activation/"+u.getId()+"/"+u.getActivationCode();

        String content="<a href="+url+">激活</a>";

        mailService.sendMail(u.getEmail(),TOPIC,content);

        //http://images.nowcoder.com/head/677t.png

        return  map;
    }

    public int updateStatus(String id) {

        return userMapper.updateStatus(id);

    }

    public int  updateHeadUrl(int id, String headUrl) {

        return  userMapper.updateHeadUrl(id,headUrl);
    }
}
