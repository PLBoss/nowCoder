package com.raw.nowcoder.controller;

import com.raw.nowcoder.entity.DiscussPost;
import com.raw.nowcoder.entity.PageRequest;
import com.raw.nowcoder.entity.PageResult;

import com.raw.nowcoder.entity.User;
import com.raw.nowcoder.service.discussPostService;
import com.raw.nowcoder.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class homeController {

    @Autowired
    private discussPostService discussPostService;


    @Autowired
    private userService userService;



    @RequestMapping(value = "/index")
    public String index(@RequestParam(value = "PageNum",required = false,defaultValue = "1") Integer  PageNum, Model model){
//        System.out.println(PageNum);

        PageRequest pageRequest=new PageRequest();
        pageRequest.setPageNum(PageNum);
        pageRequest.setPageSize(4);

        PageResult pageResult = discussPostService.selectPage(pageRequest);
//        System.out.println(pageResult);
//        System.out.println(pageResult.getContent());
//        System.out.println(pageResult.getTotalSize());

        List<DiscussPost> lists = (List<DiscussPost>) pageResult.getContent();


        List<Map<String,Object>> discussPosts=new ArrayList<>();

        if (lists!=null) {
            for (DiscussPost discussPost : lists) {
                Map<String,Object> map=new HashMap<>();

                String userId = discussPost.getUserId();
                User user=userService.selectUserById(userId);

                map.put("user",user);

                map.put("discussPost",discussPost);

                discussPosts.add(map);


            }
        }

        System.out.println(pageResult);
        model.addAttribute("pageResult",pageResult);
        model.addAttribute("discussPosts",discussPosts);


        return "index";
    }







}
