package com.example.jeffrey.rocketmqstudyone.service.producer.order;

import com.example.jeffrey.rocketmqstudyone.constant.RocketMQConstant;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;
import java.util.concurrent.*;

public class OrderProducer {
    public static void main(String[] args) throws Exception {
        // 1. 创建生产者
        DefaultMQProducer producer = new DefaultMQProducer(RocketMQConstant.PRODUCER_GROUP);
        producer.setNamesrvAddr(RocketMQConstant.NAMESRV_ADDR);
        producer.start();

        // 2. 创建自定义线程池
        int corePoolSize = 3;
        int maximumPoolSize = 6;
        long keepAliveTime = 60L;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(100);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                threadFactory,
                handler
        );

        // 3. 模拟订单：同一订单ID的消息必须顺序
        String[] orderSteps = {"创建", "付款", "发货", "完成"};
        int orderCount = 3;

        // 使用CountDownLatch等待所有订单发送完成
        CountDownLatch latch = new CountDownLatch(orderCount);

        int queueindex = 1;
        // 4. 多线程并行处理每个订单
        for (int orderId = 1; orderId <= orderCount; orderId++) {
            final int currentOrderId = orderId;

            executor.submit(() -> {
                try {
                    for (String step : orderSteps) {
                        String body = "订单" + currentOrderId + "：" + step;
                        Message msg = new Message(
                                RocketMQConstant.TOPIC,       // Topic
                                "OrderTag",         // Tag
                                ("KEY-" + currentOrderId), // Key
                                body.getBytes(RemotingHelper.DEFAULT_CHARSET)
                        );

                        // 🔥 核心：按订单ID选Queue（同ID → 同Queue）
                        SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                            @Override
                            public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                                int id = (int) arg;
                                System.out.println(Thread.currentThread().getName() + " , id =" + id);
                                // 哈希取模：固定路由到一个Queue
                                return mqs.get(id % mqs.size());
                            }
                        }, queueindex); // 传入订单ID作为分区键

                        System.out.printf("发送成功：%s, 队列ID=%d%n",
                                body, sendResult.getMessageQueue().getQueueId());
                    }
                } catch (Exception e) {
                    System.err.println("订单" + currentOrderId + "发送失败: " + e.getMessage());
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        // 5. 等待所有订单发送完成
        latch.await();
        System.out.println("所有订单消息发送完成！");
        Thread.sleep(60000);
        // 6. 优雅关闭线程池
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        // 7. 关闭生产者
        producer.shutdown();
    }
}
