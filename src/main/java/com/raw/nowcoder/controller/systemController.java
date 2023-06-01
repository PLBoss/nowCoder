package com.raw.nowcoder.controller;

import com.google.code.kaptcha.Producer;
import com.raw.nowcoder.Util.StringUtil;
import com.raw.nowcoder.Util.constant;
import com.raw.nowcoder.service.userService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
public class systemController implements constant {

    @Autowired
    private userService userService;

    @Autowired
    private Producer kaptchaProducer;


    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String getLoginPage(){

        return "/site/login";
    }

    @RequestMapping("/kapthca")
    public void kapthca(HttpServletResponse response, HttpSession Session) throws IOException {


        String text = kaptchaProducer.createText();

        //将验证码放进session中,登陆的时候再从session中取出来
        /*Session.setAttribute("kapthca",text);
         * */

        //使用redis进行优化,将验证码
        String key = "code:"+StringUtil.gennerUUID();

        Cookie cookie=new Cookie("key",key);
        cookie.setPath("/");
        cookie.setMaxAge(60);
        response.addCookie(cookie);
        BufferedImage image = kaptchaProducer.createImage(text);



        response.setContentType("image/jpeg");


        System.out.println(text);

        //将生成的图片以io流的形式输出给前端

        ServletOutputStream outputStream = response.getOutputStream();

        ImageIO.write(image,"jpeg",outputStream);





        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(key,text,60, TimeUnit.SECONDS);



    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String Login(Model model, String username, String password, String code,
                        @CookieValue("key") String key,
                        HttpServletResponse response, HttpSession session){
//        System.out.println(username+""+password);
        /*
        * 登陆逻辑:根据用户名查询用户，存在在校验密码，不存在直接返回错误，
        * 优化：这里要生成登陆凭证传入数据库中，并且设置过期时间，下次查询用户登陆时，看凭着是否过期
        * 这里也要放进cookies中实现免密登陆
        * */

//        System.out.println(session.getAttribute("kapthca"));
//        System.out.println(code);

        System.out.println(key);


        Map<String, Object> map = userService.login(username, password,code,key,EXPIRED_TIME,session);

        if(map.containsKey("ticket")){

            Cookie cookie=new Cookie("ticket",map.get("ticket").toString());
            cookie.setPath("/");
            cookie.setMaxAge(EXPIRED_TIME);
            response.addCookie(cookie);


            return "redirect:/index";


        }else{


            model.addAttribute("usernameMsg",map.get("usernameMsg"));
            model.addAttribute("passwordMsg",map.get("passwordMsg"));
            model.addAttribute("codeMsg",map.get("codeMsg"));

            return "/site/login";

        }



    }


}
