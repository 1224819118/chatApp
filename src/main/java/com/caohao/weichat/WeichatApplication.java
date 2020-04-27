package com.caohao.weichat;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.caohao.weichat.dao")
public class WeichatApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeichatApplication.class, args);
    }

}
