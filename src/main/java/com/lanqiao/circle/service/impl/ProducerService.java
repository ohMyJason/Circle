//package com.lanqiao.circle.service.impl;
//
//
//import org.apache.rocketmq.client.producer.DefaultMQProducer;
//import org.apache.rocketmq.client.producer.SendResult;
//import org.apache.rocketmq.common.message.Message;
//import org.apache.rocketmq.remoting.common.RemotingHelper;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;
//
///**
// * @Author ohMyJasonq
// * @Date 2019/9/19 2:10
// */
//@Service
//public class ProducerService {
//    @Value("${apache.rocketmq.producer.producerGroup}")
//    private String producerGroup;
//
//    @Value("${apache.rocketmq.namesrvAddr}")
//    private String namesrvAddr;
//
//    private DefaultMQProducer producer;
//
//    @PostConstruct
//    public void initProducer() {
//        producer = new DefaultMQProducer(producerGroup);
//        producer.setNamesrvAddr(namesrvAddr);
//        producer.setRetryTimesWhenSendFailed(3);
//        try {
//            producer.start();
//            System.out.println("[Producer 已启动]");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public String send(String topic, String tags, String msg) {
//        SendResult result = null;
//        try {
//            //在producer中使用Topic
//            Message message = new Message(topic, tags, msg.getBytes(RemotingHelper.DEFAULT_CHARSET));
//            result = producer.send(message,10000);
//            System.out.println("[Producer] msgID(" + result.getMsgId() + ") " + result.getSendStatus());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
////        System.out.println(result.toString());
//        return "{\"MsgId\":\"" + result.getMsgId() + "\"}";
//    }
//
//
//    @PreDestroy
//    public void shutDownProducer() {
//        if (producer != null) {
//            producer.shutdown();
//        }
//    }
//}
