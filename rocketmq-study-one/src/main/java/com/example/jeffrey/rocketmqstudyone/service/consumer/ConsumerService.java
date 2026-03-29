package com.example.jeffrey.rocketmqstudyone.service.consumer;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
// consumerGroup 随便写，topic 必须和发送方一致
@RocketMQMessageListener(
    topic = "test-topic",
    consumerGroup = "test-consumer-group"
)
public class ConsumerService implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        // 收到消息直接进入这里
        System.out.println("收到消息：" + message);
    }
}