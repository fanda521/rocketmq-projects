package com.example.jeffrey.rocketmqstudyone;

import com.example.jeffrey.rocketmqstudyone.service.producer.broadcast.BroadcastProducer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;

@SpringBootTest
public class BroadcastTest {

    @Resource
    private BroadcastProducer broadcastProducer;

    @Test
    public void testSend() {
        broadcastProducer.send("这条消息所有消费者都会收到 ~");
    }
}