package com.raw.nowcoder.test;


import com.raw.nowcoder.NowCoderApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = NowCoderApplication.class)
public class RedisTest {


    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    public  void setTest(){
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        ops.set("username:1","rawcode");


        String s = ops.get("username:1");

        System.out.println(s);

    }
}
