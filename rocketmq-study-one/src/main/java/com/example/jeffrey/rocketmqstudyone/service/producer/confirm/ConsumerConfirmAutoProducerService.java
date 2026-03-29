package com.example.jeffrey.rocketmqstudyone.service.producer.confirm;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lucksoul
 * @version 1.0
 * @date 2026/3/29 12:50
 */

@Service
public class ConsumerConfirmAutoProducerService {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 同步发送（等待Broker确认）
     */
    public SendResult consumerConfirmAutoAckSend() {
        String topic = "topic-auto-ack";
        String message = "Hello RocketMQ topic-auto-ack";
        SendResult sendResult = rocketMQTemplate.syncSend(topic, message);
        System.out.println("同步发送结果：" + sendResult);
        return sendResult;
    }

    public SendResult consumerConfirmAutoAckSendWithTag() {
        String topic = "topic-auto-ack:tag12";
        String message = "Hello RocketMQ topic-auto-ack tag12";
        SendResult sendResult = rocketMQTemplate.syncSend(topic, message);
        System.out.println("同步发送结果：" + sendResult);
        return sendResult;
    }

}
