package com.example.jeffrey.rocketmqstudyone.service.producer.batch;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import java.util.ArrayList;
import java.util.List;

public class BatchProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("BATCH_PRODUCER_GROUP");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();

        // 构造批量消息
        List<Message> messageList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Message msg = new Message(
                "BatchTopic",
                "BatchTag",
                ("批量消息-" + i).getBytes()
            );
            messageList.add(msg);
        }

        // ====================== 核心：批量发送 ======================
        producer.send(messageList);
        // ===========================================================

        System.out.println("批量发送 10 条消息成功");
        producer.shutdown();
    }
}