package com.example.jeffrey.rocketmqstudyone.service.producer.filter;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lucksoul
 * @version 1.0
 * @date 2026/4/11 10:02
 */

@Service
public class TagFilterProducer {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 发送带 TAG 的消息
     * topic:tag 格式
     */
    public void sendWithTag(String topic, String tag, String msg) {
        // 重点：topic + ":" + tag
        rocketMQTemplate.convertAndSend(topic + ":" + tag, msg);
    }
}
