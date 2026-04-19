package com.example.jeffrey.rocketmqstudyone.service.producer.transcation;

import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

public class TransactionMultiTagProducer {

    public static void main(String[] args) throws Exception {
        // 1. 创建事务生产者
        TransactionMQProducer producer = new TransactionMQProducer("TRAN_MULTI_TAG_GROUP");
        producer.setNamesrvAddr("127.0.0.1:9876");

        // ====================== 核心：事务监听器 ======================
        producer.setTransactionListener(new TransactionListener() {

            /**
             * 第一步：执行本地事务
             */
            @Override
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                String tag = msg.getTags();
                String body = new String(msg.getBody());
                System.out.printf("【执行本地事务】TAG=%s, 内容=%s%n", tag, body);

                switch (tag) {
                    case "TAG1":
                        // 1. 直接提交
                        System.out.println(" → TAG1：本地事务成功，直接提交");
                        return LocalTransactionState.COMMIT_MESSAGE;

                    case "TAG2":
                        // 2. 直接回滚
                        System.out.println(" → TAG2：本地事务失败，直接回滚");
                        return LocalTransactionState.ROLLBACK_MESSAGE;

                    case "TAG3":
                    case "TAG4":
                    case "TAG5":
                        // 3/4/5 都返回 UNKNOWN → 触发 BROKER 回查
                        System.out.println(" → " + tag + "：状态未知，等待Broker回查");
                        return LocalTransactionState.UNKNOW;

                    default:
                        return LocalTransactionState.UNKNOW;
                }
            }

            /**
             * 第二步：Broker 回查事务（关键！）
             */
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                String tag = msg.getTags();
                String body = new String(msg.getBody());
                System.out.printf("【Broker回查事务】TAG=%s, 内容=%s%n", tag, body);

                switch (tag) {
                    case "TAG3":
                        // 3. 回查提交
                        System.out.println(" → TAG3：回查确认 → 提交");
                        return LocalTransactionState.COMMIT_MESSAGE;

                    case "TAG4":
                        // 4. 回查回滚
                        System.out.println(" → TAG4：回查确认 → 回滚");
                        return LocalTransactionState.ROLLBACK_MESSAGE;

                    case "TAG5":
                        // 5. 回查仍然未知（继续等待下一次回查）
                        System.out.println(" → TAG5：回查仍然未知 → 继续UNKNOWN");
                        return LocalTransactionState.UNKNOW;

                    default:
                        return LocalTransactionState.UNKNOW;
                }
            }
        });

        // 启动
        producer.start();
        System.out.println("======== 开始发送 5 条不同TAG的事务消息 ========");

        // ====================== 发送 5 条不同 TAG 的消息 ======================
        sendMsg(producer, "TAG1", "订单创建-直接提交");
        sendMsg(producer, "TAG2", "订单创建-直接回滚");
        sendMsg(producer, "TAG3", "订单创建-回查提交");
        sendMsg(producer, "TAG4", "订单创建-回查回滚");
        sendMsg(producer, "TAG5", "订单创建-永远UNKNOWN");

        // 注意：事务生产者不能关闭，否则回查失效！
        // producer.shutdown();
    }

    // 发送工具方法
    private static void sendMsg(TransactionMQProducer producer, String tag, String content) throws Exception {
        Message msg = new Message(
                "TransactionMultiTagTopic",
                tag,
                content.getBytes()
        );
        TransactionSendResult result = producer.sendMessageInTransaction(msg, null);
        System.out.printf("发送 %s → 结果：%s%n", tag, result.getLocalTransactionState());
        Thread.sleep(200);
    }
}