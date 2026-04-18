package com.example.jeffrey.rocketmqstudyone.service.consumer.order;

import com.example.jeffrey.rocketmqstudyone.constant.RocketMQConstant;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 多实例消费者
 * 同一个队列只会被一个消费者处理
 * 消息严格 FIFO
 */
public class SingleQueueConsumer02 {

    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(RocketMQConstant.CONSUMER_GROUP);
        consumer.setNamesrvAddr(RocketMQConstant.NAMESRV_ADDR);
        consumer.subscribe(RocketMQConstant.TOPIC, "*");

        // ====================== 核心：顺序监听器 ======================
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                for (MessageExt msg : msgs) {
                    String body = new String(msg.getBody());
                    long queueId = msg.getQueueId();
                    String thread = Thread.currentThread().getName();

                    System.out.printf("消费者02【消费】队列=%d | 线程=%s | 内容=%s%n", queueId, thread, body);
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });

        consumer.start();
        System.out.println("【顺序消费者】启动成功");
    }
}