package com.example.jeffrey.rocketmqstudyone.controller;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ProducerController {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("/send")
    public String send() {
        // 主题:tag 格式（tag可选）
        String topic = "test-topic";
        
        // 发送消息
        rocketMQTemplate.convertAndSend(topic, "Hello RocketMQ!");

        return "发送成功";
    }
}