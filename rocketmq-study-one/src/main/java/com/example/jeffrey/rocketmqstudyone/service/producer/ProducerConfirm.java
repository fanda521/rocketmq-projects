package com.example.jeffrey.rocketmqstudyone.service.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class ProducerConfirm {
    public static void main(String[] args) throws Exception {
        // 1. 创建生产者（指定生产者组）
        DefaultMQProducer producer = new DefaultMQProducer("producer_group_confirm_test");
        // 2. 设置 NameServer 地址
        producer.setNamesrvAddr("127.0.0.1:9876");
        // 3. 启动生产者
        producer.start();
        // 4. 创建消息（Topic、Tag、内容）
        Message msg = new Message(
                "TopicConfirmTest",
                "TagA",
                "Hello RocketMQ Confirm".getBytes(RemotingHelper.DEFAULT_CHARSET)
        );

        // --------------------------
        // 方式1：同步发送（推荐可靠场景）
        // --------------------------
        SendResult syncResult = producer.send(msg);
        System.out.printf("同步发送成功：%s%n", syncResult);

        // --------------------------
        // 方式2：异步发送（高吞吐场景）
        // --------------------------
        producer.send(msg, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.printf("异步发送成功：%s%n", sendResult);
            }
            @Override
            public void onException(Throwable e) {
                System.err.printf("异步发送失败：%s%n", e.getMessage());
                // 失败重试/降级逻辑
            }
        });

        // --------------------------
        // 方式3：单向发送（无确认）
        // --------------------------
        producer.sendOneway(msg);
        System.out.println("单向发送完成（无确认）");

        // 等待异步回调完成
        Thread.sleep(3000);
        // 关闭生产者
        producer.shutdown();
    }
}