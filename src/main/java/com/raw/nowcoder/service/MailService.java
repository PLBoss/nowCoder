package com.raw.nowcoder.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.websocket.MessageHandler;

@Service
public class MailService {

    //日志记录
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;


    public void sendMail(String to ,String topic ,String content){



        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper;

        try {
            mimeMessageHelper =new MimeMessageHelper (message,true);

            //设置发件人
            mimeMessageHelper.setFrom(from);

            //设置收件人

            mimeMessageHelper.setTo(to);

            //设置在主题

            mimeMessageHelper.setSubject(topic);

            //设置内容
            //设置内容的格式:html

            mimeMessageHelper.setText(content,true);


            //发送信息

            mailSender.send(message);

            //
            logger.info("邮箱发送成功");
        } catch (MessagingException e) {

          logger.error("邮箱发送错误",e);
        }


    }


}
