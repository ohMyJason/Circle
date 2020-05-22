//package com.lanqiao.circle.controller;
//
//import com.lanqiao.circle.service.impl.ProducerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @Author ohMyJasonq
// * @Date 2019/9/19 10:07
// */
//@RestController
//@RequestMapping("/rocket")
//public class RocketController {
//    @Autowired
//    private ProducerService producer;
//
//    @RequestMapping("/push")
//    public String pushMsg(String msg) {
//        return producer.send("testTopic", "push", msg);
//    }
//}
