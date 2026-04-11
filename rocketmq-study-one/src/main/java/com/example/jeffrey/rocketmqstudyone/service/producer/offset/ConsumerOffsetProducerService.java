package com.example.jeffrey.rocketmqstudyone.service.producer.offset;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lucksoul
 * @version 1.0
 * @date 2026/3/29 20:02
 */

@Service
public class ConsumerOffsetProducerService {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 同步发送（等待Broker确认）
     */
    public SendResult consumerOffsetSend() {
        String topic = "topic-consumer-offset";
        String message = "Hello RocketMQ topic-consumer-offset";
        SendResult sendResult = rocketMQTemplate.syncSend(topic, message);
        System.out.println("同步发送结果：" + sendResult);
        return sendResult;
    }
}
