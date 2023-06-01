package com.raw.nowcoder.test;

import com.raw.nowcoder.NowCoderApplication;
import com.raw.nowcoder.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = NowCoderApplication.class)
public class MailTets {

    @Autowired
    private MailService mailService;


    @Test
    public void  sendMail(){

        mailService.sendMail("3309327228@qq.com","test","<h1>你好</h1>");
    }
}
