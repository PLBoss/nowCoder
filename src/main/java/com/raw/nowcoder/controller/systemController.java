package com.raw.nowcoder.controller;

import com.google.code.kaptcha.Producer;
import com.raw.nowcoder.Util.constant;
import com.raw.nowcoder.service.userService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class systemController implements constant {

    @Autowired
    private userService userService;

    @Autowired
    private Producer kaptchaProducer;

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String getLoginPage(){

        return "/site/login";
    }

    @RequestMapping("/kapthca")
    public void kapthca(HttpServletResponse response, HttpSession Session) throws IOException {

        response.setContentType("image/jpeg");

        String text = kaptchaProducer.createText();

//        System.out.println(text);

        BufferedImage image = kaptchaProducer.createImage(text);


        //将生成的图片以io流的形式输出给前端

        ServletOutputStream outputStream = response.getOutputStream();

        ImageIO.write(image,"jpeg",outputStream);

        //将验证码放进session中,登陆的时候再从session中取出来
        Session.setAttribute("kapthca",text);

//        System.out.println(Session.getAttribute("kapthca"));




    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String Login(Model model, String username, String password,String code, HttpServletResponse response,HttpSession session){
        System.out.println(username+""+password);
        /*
        * 登陆逻辑:根据用户名查询用户，存在在校验密码，不存在直接返回错误，
        * 优化：这里要生成登陆凭证传入数据库中，并且设置过期时间，下次查询用户登陆时，看凭着是否过期
        * 这里也要放进cookies中实现免密登陆
        * */

        System.out.println(session.getAttribute("kapthca"));
        System.out.println(code);

        Map<String, Object> map = userService.login(username, password,code,EXPIRED_TIME,session);

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
