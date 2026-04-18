package com.example.jeffrey.rocketmqstudyone.service.consumer.batch;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.common.message.MessageExt;
import java.util.List;

public class BatchConsumer {
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("BATCH_CONSUMER_GROUP");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.subscribe("BatchTopic", "*");

        // ====================== 批量消费监听器 ======================
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                // 一次拿到一批！
                System.out.println("本次批量消费条数：" + msgs.size());

                for (MessageExt msg : msgs) {
                    System.out.println("内容：" + new String(msg.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
        System.out.println("批量消费者启动");
    }
}