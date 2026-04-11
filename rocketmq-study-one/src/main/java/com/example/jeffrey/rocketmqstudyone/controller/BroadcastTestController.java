package com.example.jeffrey.rocketmqstudyone.controller;

import com.example.jeffrey.rocketmqstudyone.service.producer.broadcast.BroadcastProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class BroadcastTestController {

    @Resource
    private BroadcastProducer producerService;

    @GetMapping("/send/broadcast")
    public String sendBroadcast(@RequestParam(defaultValue = "广播消息测试") String msg) {
        producerService.send(msg);
        return "发送成功：" + msg;
    }
}