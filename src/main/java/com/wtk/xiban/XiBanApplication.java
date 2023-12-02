package com.wtk.xiban;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.wtk.xiban.mapper")
@EnableScheduling
public class XiBanApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiBanApplication.class, args);
    }

}
