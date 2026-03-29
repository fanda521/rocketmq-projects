package com.example.jeffrey.rocketmqstudyone.service.consumer.confirm;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(
        topic = "topic-auto-ack",
        consumerGroup = "consumer-group-confirm-auto-test"
)
public class ConsumerConfirmAuto implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        System.out.println("自动ACK消费：" + message);

        // 正常返回 = 提交offset
        // 抛出异常 = 不提交，消息重试
    }
}