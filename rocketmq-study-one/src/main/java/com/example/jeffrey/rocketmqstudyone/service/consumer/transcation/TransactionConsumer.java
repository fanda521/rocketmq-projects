package com.example.jeffrey.rocketmqstudyone.service.consumer.transcation;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

public class TransactionConsumer {
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("TRAN_MULTI_TAG_CONSUMER");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.subscribe("TransactionMultiTagTopic", "*");

        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            for (MessageExt msg : msgs) {
                System.out.printf("【已消费】TAG=%s → %s%n", msg.getTags(), new String(msg.getBody()));
            }
            return org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        consumer.start();
        System.out.println("事务消息消费者已启动");
    }
}