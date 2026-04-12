package com.example.jeffrey.rocketmqstudyone.service.consumer.filter;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(
    topic = "filter-tag-topic",
    consumerGroup = "tag-consumer-group-A",
    selectorExpression = "order"  // 只消费 order 标签
)
public class TagOrderConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        System.out.println("【消费者A - 只收order】收到：" + message);
    }
}