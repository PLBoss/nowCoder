package com.raw.nowcoder.controller;

import com.raw.nowcoder.entity.User;
import com.raw.nowcoder.mapper.userMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class homeController {

    @RequestMapping("/index")
    public String index(){
        return "index";
    }




}
