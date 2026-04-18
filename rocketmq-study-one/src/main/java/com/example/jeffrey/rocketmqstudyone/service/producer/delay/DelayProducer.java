package com.example.jeffrey.rocketmqstudyone.service.producer.delay;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

public class DelayProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("DELAY_PRODUCER_GROUP");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();

        // 消息内容：订单超时关闭
        Message msg = new Message(
                "OrderDelayTopic",
                "TAG",
                "orderId_123456 超时未支付，自动关闭".getBytes()
        );

        // ====================== 核心 ======================
        // 设置延迟等级：3 = 10s
        msg.setDelayTimeLevel(3);
        // ==================================================

        producer.send(msg);
        System.out.println("延迟消息已发送，延迟等级：3（10秒）");

        producer.shutdown();
    }
}