package com.example.service.impl;

import com.example.service.IRocketMQService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * <p>
 * RocektMQ生产者常用发送消息方法
 * 最佳实践:https://github.com/apache/rocketmq/blob/master/docs/cn/best_practice.md
 * </p>
 *
 * @author MrWen
 * @since 2022-01-06 17:10
 */
@Slf4j
@Service
public class RocketMQServiceImpl implements IRocketMQService {

    @Autowired
    private RocketMQTemplate rocketMqTemplate;

    @Override
    public SendResult sendMessage(String key, Object msg) {
        SendResult sendResult = this.rocketMqTemplate.syncSend(key, msg);
        if (SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
            log.info("MQ发送同步消息成功,key={} msg={},sendResult={}", key, msg, sendResult);
        } else {
            log.warn("MQ发送同步消息不一定成功,key={} msg={},sendResult={}", key, msg, sendResult);
        }
        return sendResult;
    }

    @Override
    public SendResult sendMessage(String topicName, String tags, Object msg) {
        SendResult sendResult = this.rocketMqTemplate.syncSend(topicName + ":" + tags, msg);
        if (SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
            log.info("MQ发送同步消息成功,topicName={},tags={},msg={},sendResult={}", topicName, tags, msg, sendResult);
        } else {
            log.warn("MQ发送同步消息不一定成功,topicName={},tags={},msg={},sendResult={}", topicName, tags, msg, sendResult);
        }
        return sendResult;
    }

    @Override
    public void sendAsyncMessage(String key, Object msg, SendCallback sendCallback) {
        this.rocketMqTemplate.asyncSend(key, msg, sendCallback);
        log.info("MQ发送异步消息成功,key={} msg={}", key, msg);
    }

    @Override
    public void sendOneway(String key, Object msg) {
        this.rocketMqTemplate.sendOneWay(key, msg);
        log.info("MQ发送单向消息成功,key={} msg={}", key, msg);
    }

    @Override
    public SendResult sendDelayLevel(String key, Object msg, int delayTimeLevel) {
        Message<? extends Object> message = MessageBuilder
                .withPayload(msg)
                //设置key
                .setHeader(MessageConst.PROPERTY_KEYS, key)
                .build();
        SendResult sendResult = this.rocketMqTemplate.syncSend(key, message, 30000, delayTimeLevel);
        if (SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
            log.info("MQ发送延时消息成功,key={} msg={} sendResult={}", key, message, sendResult);
        } else {
            log.warn("MQ发送延时消息不一定成功,key={} msg={} sendResult={}", key, message, sendResult);
        }
        return sendResult;
    }

    @Override
    public SendResult sendDelayLevel(String key, Object msg, int timeout, int delayTimeLevel) {
        Message<? extends Object> message = MessageBuilder
                .withPayload(msg)
                //设置key
                .setHeader(MessageConst.PROPERTY_KEYS, key)
                .build();
        SendResult sendResult = this.rocketMqTemplate.syncSend(key, message, timeout, delayTimeLevel);
        if (SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
            log.info("MQ发送延时消息成功,key={} msg={} sendResult={}", key, message, sendResult);
        } else {
            log.warn("MQ发送延时消息不一定成功,key={} msg={} sendResult={}", key, message, sendResult);
        }
        return sendResult;
    }

    @Override
    public SendResult sendInOrder(String key, Object msg, String hashKey) {
        Message<? extends Object> message = MessageBuilder
                .withPayload(msg)
                //设置key
                .setHeader(MessageConst.PROPERTY_KEYS, key)
                .build();
        //hashKey:  根据其哈希值取模后确定发送到哪一个队列
        SendResult sendResult = this.rocketMqTemplate.syncSendOrderly(key, message, hashKey);
        if (SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
            log.info("MQ发送顺序消息成功,key={} msg={} sendResult={}", key, message, sendResult);
        } else {
            log.warn("MQ发送顺序消息不一定成功,key={} msg={} sendResult={}", key, message, sendResult);
        }
        return sendResult;
    }


}
