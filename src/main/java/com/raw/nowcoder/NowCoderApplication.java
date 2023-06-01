package com.raw.nowcoder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan("com/raw/nowcoder/Filter")
public class NowCoderApplication {

    public static void main(String[] args) {
        SpringApplication.run(NowCoderApplication.class, args);
    }

}
