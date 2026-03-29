package com.example.jeffrey.rocketmqstudyone.service.producer.confirm;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lucksoul
 * @version 1.0
 * @date 2026/3/29 12:50
 */

@Service
public class ConsumerConfirmManualProducerService {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 同步发送（等待Broker确认）
     */
    public SendResult consumerConfirmManualAckConcurrentlySend() {
        String topic = "topic-concurrently-manual-ack";
        String message = "Hello RocketMQ topic-concurrently-manual-ack";
        SendResult sendResult = rocketMQTemplate.syncSend(topic, message);
        System.out.println("同步发送结果：" + sendResult);
        return sendResult;
    }

    public SendResult consumerConfirmManualAckOrderlySend() {
        String topic = "topic-orderly-manual-ack";
        String message = "Hello RocketMQ topic-orderly-manual-ack";
        SendResult sendResult = rocketMQTemplate.syncSend(topic, message);
        System.out.println("同步发送结果：" + sendResult);
        return sendResult;
    }

}
