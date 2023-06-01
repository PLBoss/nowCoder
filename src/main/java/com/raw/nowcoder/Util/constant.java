package com.raw.nowcoder.Util;

public interface constant {

     int EXPIRED_TIME=1000;

     //普通用户
     int USER=0;
     //超级管理员
     int ADMIN=1;
     //版主
     int MODERATER=2;

     //未激活
     int ACTIVATE=1;

     //激活
     int UNACTIVATE=0;


     //邮件主题
     String TOPIC="激活邮件";


     String ContextPath="http://localhost:8080/user/";


     //头像存放地址
     String dest="D:\\Desktop\\项目\\stock\\nowCoder\\src\\main\\resources\\static\\img\\head";
}
