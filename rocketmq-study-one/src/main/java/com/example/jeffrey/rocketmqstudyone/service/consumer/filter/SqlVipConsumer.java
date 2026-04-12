package com.example.jeffrey.rocketmqstudyone.service.consumer.filter;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(
    topic = "filter-sql-topic",
    consumerGroup = "sql-consumer-group-1",
    selectorType = SelectorType.SQL92,    // SQL过滤模式
    selectorExpression = "age > 18 and vip = true"
)
public class SqlVipConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        System.out.println("【SQL消费者1 - 成年VIP】收到：" + message);
    }
}