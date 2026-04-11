package com.example.jeffrey.rocketmqstudyone.service.producer.broadcast;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class BroadcastProducer {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public void send(String msg) {
        // topic 和普通发送一样
        rocketMQTemplate.convertAndSend("broadcast-topic", msg);
    }
}