package com.example.jeffrey.rocketmqstudyone.service.consumer.filter;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(
    topic = "filter-tag-topic",
    consumerGroup = "tag-consumer-group-B",
    selectorExpression = "pay"   // 只消费 pay 标签
)
public class TagPayConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        System.out.println("【消费者B - 只收pay】收到：" + message);
    }
}