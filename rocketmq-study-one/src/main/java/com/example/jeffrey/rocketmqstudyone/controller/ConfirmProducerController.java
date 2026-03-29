package com.example.jeffrey.rocketmqstudyone.controller;

import com.example.jeffrey.rocketmqstudyone.service.producer.confirm.ConsumerConfirmAutoProducerService;
import com.example.jeffrey.rocketmqstudyone.service.producer.confirm.ConsumerConfirmManualProducerService;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lucksoul
 * @version 1.0
 * @date 2026/3/29 13:35
 */
@RestController
@RequestMapping("/confirm")
public class ConfirmProducerController {

    @Autowired
    private ConsumerConfirmAutoProducerService consumerConfirmAutoProducerService;

    @Autowired
    private ConsumerConfirmManualProducerService consumerConfirmManualProducerService;

    @RequestMapping("/auto")
    public SendResult producerConsumerConfirmAuto() {
        SendResult sendResult = consumerConfirmAutoProducerService.consumerConfirmAutoAckSend();
        return sendResult;
    }

    @RequestMapping("/autoWithTag")
    public SendResult producerConsumerConfirmAutoWithTag() {
        SendResult sendResult = consumerConfirmAutoProducerService.consumerConfirmAutoAckSendWithTag();
        return sendResult;
    }

    @RequestMapping("/manualConcurrently")
    public SendResult producerConsumerConfirmManualConcurrently() {
        SendResult sendResult = consumerConfirmManualProducerService.consumerConfirmManualAckConcurrentlySend();
        return sendResult;
    }
    @RequestMapping("/manualOrderly")
    public SendResult producerConsumerConfirmManualOrderly() {
        SendResult sendResult = consumerConfirmManualProducerService.consumerConfirmManualAckOrderlySend();
        return sendResult;
    }
}
