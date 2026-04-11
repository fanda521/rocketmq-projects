package com.example.jeffrey.rocketmqstudyone.controller;

import com.example.jeffrey.rocketmqstudyone.service.producer.offset.ConsumerOffsetProducerService;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lucksoul
 * @version 1.0
 * @date 2026/3/29 20:03
 */
@RestController
@RequestMapping("/offset")
public class OffsetProducerController {

    @Autowired
    private ConsumerOffsetProducerService consumerOffsetProducerService;

    @RequestMapping("/send")
    public SendResult consumerOffsetSend() {
        SendResult sendResult = consumerOffsetProducerService.consumerOffsetSend();
        System.out.println("同步发送结果：" + sendResult);
        return sendResult;
    }
}
