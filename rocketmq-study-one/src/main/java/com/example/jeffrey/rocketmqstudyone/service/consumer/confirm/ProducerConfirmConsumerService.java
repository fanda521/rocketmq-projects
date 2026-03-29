package com.example.jeffrey.rocketmqstudyone.service.consumer.confirm;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author lucksoul
 * @version 1.0
 * @date 2026/3/29 9:58
 */

@Component
// consumerGroup 随便写，topic 必须和发送方一致
@RocketMQMessageListener(
        topic = "TopicConfirmTest",
        consumerGroup = "producer_group_confirm_test"
)
public class ProducerConfirmConsumerService implements RocketMQListener<String> {
    @Override
    public void onMessage(String s) {
        System.out.println("ProducerConfirmConsumerService,收到消息：" + s);
    }
}
