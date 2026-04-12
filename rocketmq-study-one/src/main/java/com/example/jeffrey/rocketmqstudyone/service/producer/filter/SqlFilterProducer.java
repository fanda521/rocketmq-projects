package com.example.jeffrey.rocketmqstudyone.service.producer.filter;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class SqlFilterProducer {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public SendResult sendWithSqlAttr(String msg, int age, boolean vip, String city) {
        // 构建消息 + 自定义属性（用于SQL过滤）
        Message<String> message = MessageBuilder.withPayload(msg)
                .setHeader("age", age)
                .setHeader("vip", vip)
                .setHeader("city", city)
                .build();

        return rocketMQTemplate.syncSend("filter-sql-topic", message);
    }
}