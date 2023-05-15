package com.raw.nowcoder.config;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;


@Configuration
public class KaptchaConfig {

    @Bean
    public Producer kaptchaProducer(){

        Properties properties=new Properties();

        properties.setProperty("kaptcha.image.width","100");

        properties.setProperty("kaptcha.image.height","40");


        properties.setProperty("kaptcha.textproducer.char.string","12345678hghfdsf");

        properties.setProperty("kaptcha.textproducer.char.length","4");

        properties.setProperty("kaptcha.textproducer.front.size","10");


        DefaultKaptcha defaultKaptcha=new DefaultKaptcha();
        Config config=new Config(properties);

        defaultKaptcha.setConfig(config);

        return  defaultKaptcha;

    }


}
