package com.raw.nowcoder.test;

import com.raw.nowcoder.NowCoderApplication;

import com.raw.nowcoder.entity.DiscussPost;
import com.raw.nowcoder.entity.PageRequest;
import com.raw.nowcoder.entity.PageResult;
import com.raw.nowcoder.entity.User;
import com.raw.nowcoder.mapper.userMapper;
import com.raw.nowcoder.service.discussPostService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;


@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = NowCoderApplication.class)
public class mapperTest {

    @Autowired
    private userMapper userMapper;

    @Autowired
    public discussPostService discussPostService;

        @Test
    public  void user(){
            List<User> users = userMapper.selectAll();

            User zhangsan = userMapper.selectByName("zhangsan");

            System.out.println(zhangsan);



            System.out.println(users);


        }

        @Test
        public void login(){

            String s = UUID.randomUUID().toString();
            System.out.println(s.replaceAll("-",""));

        }


        @Test
        public  void discussPost(){

            List<DiscussPost> discussPosts = discussPostService.selectAll();
            System.out.println(discussPosts.size());


        }

    @Test
    public  void selectPage(){
        PageRequest request=new PageRequest();
        request.setPageNum(1);
        request.setPageSize(5);

        PageResult pageResult = discussPostService.selectPage(request);




    }

}
