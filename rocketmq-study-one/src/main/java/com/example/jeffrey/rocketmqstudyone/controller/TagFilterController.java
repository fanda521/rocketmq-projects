package com.example.jeffrey.rocketmqstudyone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.jeffrey.rocketmqstudyone.service.producer.filter.TagFilterProducer;

@RestController
public class TagFilterController {

    @Autowired
    private  TagFilterProducer tagFilterProducer;

    @GetMapping("/send/tag")
    public String sendTag(
            @RequestParam(defaultValue = "order") String tag,
            @RequestParam(defaultValue = "测试TAG消息") String msg
    ) {
        tagFilterProducer.sendWithTag("filter-tag-topic", tag, msg);
        return "带TAG消息发送成功 tag=" + tag + " msg=" + msg;
    }
}