package com.example.jeffrey.rocketmqstudyone.service.consumer.filter;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(
    topic = "filter-tag-topic",
    consumerGroup = "tag-consumer-group-C",
    selectorExpression = "order||pay"   // 消费两个标签
)
public class TagAllConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        System.out.println("【消费者C - 收order/pay】收到：" + message);
    }
}