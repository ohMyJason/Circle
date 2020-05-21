package com.lanqiao.circle;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@MapperScan("com.lanqiao.circle.mapper")
@EnableTransactionManagement
@SpringBootApplication
@EnableScheduling
public class CircleApplication {

    public static void main(String[] args) {
        SpringApplication.run(CircleApplication.class, args);
    }


//    @Scheduled(cron = "0/2 * * * * ? ")
//    private void process()
//    {
//        System.out.println(111);
//    }
}
