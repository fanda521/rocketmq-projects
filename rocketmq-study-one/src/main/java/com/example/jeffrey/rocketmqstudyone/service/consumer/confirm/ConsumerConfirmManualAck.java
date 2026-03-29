package com.example.jeffrey.rocketmqstudyone.service.consumer.confirm;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import java.util.List;

/**
 * 原生 RocketMQ 消费者
 * 纯手动 ACK，main 方法直接跑
 */
public class ConsumerConfirmManualAck {

    public static void main(String[] args) throws Exception {
        // 1. 创建消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer-group-confirm-manual-ack-test  ");
        consumer.setNamesrvAddr("127.0.0.1:9876");

        // 2. 订阅 Topic
        consumer.subscribe("topic-concurrently-manual-ack", "*");

        // 3. 注册监听器 → 手动确认核心在这里
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs) {
                    try {
                        String body = new String(msg.getBody());
                        System.out.println("收到消息：" + body);

                        // =================================
                        // 你的业务逻辑（DB、接口、处理）
                        // =================================

                        // =================================
                        // 手动确认：业务成功 → 提交 ACK
                        // =================================
                        System.out.println("✅ 手动 ACK 提交成功");
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;

                    } catch (Exception e) {
                        e.printStackTrace();

                        // =================================
                        // 手动确认：业务失败 → 不 ACK，重试
                        // =================================
                        System.out.println("❌ 不 ACK，消息稍后重试");
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        // 4. 启动消费者
        consumer.start();
        System.out.println("=== 手动 ACK 消费者已启动 ===");
    }
}