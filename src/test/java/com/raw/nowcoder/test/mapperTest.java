package com.raw.nowcoder.test;

import com.raw.nowcoder.NowCoderApplication;

import com.raw.nowcoder.entity.User;
import com.raw.nowcoder.mapper.userMapper;
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

}
