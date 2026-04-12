package com.example.jeffrey.rocketmqstudyone.service.consumer.filter;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(
    topic = "filter-sql-topic",
    consumerGroup = "sql-consumer-group-2",
    selectorType = SelectorType.SQL92,
    selectorExpression = "city = 'shanghai'"
)
public class SqlShanghaiConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        System.out.println("【SQL消费者2 - 上海】收到：" + message);
    }
}