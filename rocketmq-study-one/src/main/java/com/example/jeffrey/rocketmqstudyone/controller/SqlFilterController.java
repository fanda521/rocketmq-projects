package com.example.jeffrey.rocketmqstudyone.controller;

import com.example.jeffrey.rocketmqstudyone.service.producer.filter.SqlFilterProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SqlFilterController {

    @Resource
    private SqlFilterProducer sqlFilterProducer;

    @GetMapping("/send/sql")
    public String sendSql(
            @RequestParam(defaultValue = "测试SQL消息") String msg,
            @RequestParam(defaultValue = "20") int age,
            @RequestParam(defaultValue = "true") boolean vip,
            @RequestParam(defaultValue = "beijing") String city
    ) {
        SendResult result = sqlFilterProducer.sendWithSqlAttr(msg, age, vip, city);
        return "SQL属性消息发送成功：" + result;
    }
}