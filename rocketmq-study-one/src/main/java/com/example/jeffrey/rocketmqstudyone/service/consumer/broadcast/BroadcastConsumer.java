package com.example.jeffrey.rocketmqstudyone.service.consumer.broadcast;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(
        topic = "broadcast-topic",
        consumerGroup = "broad-consumer-group",
        // 重点：广播模式
        messageModel = MessageModel.BROADCASTING
)
public class BroadcastConsumer implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt message) {
        String body = new String(message.getBody());
        System.out.println("【广播消费者】收到消息: " + body);
    }
}