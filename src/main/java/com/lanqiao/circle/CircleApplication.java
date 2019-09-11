package com.lanqiao.circle;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@MapperScan("com.lanqiao.circle.mapper")
@EnableTransactionManagement
@SpringBootApplication
public class CircleApplication {

    public static void main(String[] args) {
        SpringApplication.run(CircleApplication.class, args);
    }

}
