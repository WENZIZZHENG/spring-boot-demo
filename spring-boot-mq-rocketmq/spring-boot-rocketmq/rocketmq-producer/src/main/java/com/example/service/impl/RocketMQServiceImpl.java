package com.example.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.service.IRocketMQService;
import com.example.service.util.ListSplitter;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 原生的producer
     */
    @Autowired
    private DefaultMQProducer producer;

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
        log.info("MQ发送异步消息,key={} msg={}", key, msg);
    }

    @Override
    public void sendOneway(String key, Object msg) {
        this.rocketMqTemplate.sendOneWay(key, msg);
        log.info("MQ发送单向消息,key={} msg={}", key, msg);
    }

    @Override
    public void sendBatchMessage(String key, List<?> list) {
        String topicName = key;
        String tags = "";

        String[] split = key.split(":");
        if (split.length == 2) {
            topicName = split[0];
            tags = split[1];
        }
        this.sendBatchMessage(topicName, tags, 30000L, list);
    }

    @Override
    public void sendBatchMessage(String topicName, String tags, Long timeout, List<?> list) {
        //转为message
        List<org.apache.rocketmq.common.message.Message> messages = list.stream().map(x ->
                new org.apache.rocketmq.common.message.Message(topicName, tags,
                        //String类型不需要转JSON，其它类型都要转为JSON模式
                        x instanceof String ? ((String) x).getBytes(StandardCharsets.UTF_8) : JSON.toJSONBytes(x))
        ).collect(Collectors.toList());

        //自动分割发送，把大的消息分裂成若干个小的消息(每次发送最大只能4MB)
        ListSplitter splitter = new ListSplitter(messages);

        while (splitter.hasNext()) {
            try {
                List<org.apache.rocketmq.common.message.Message> listItem = splitter.next();
                SendResult sendResult = producer.send(listItem, timeout == null ? 30000L : timeout);
                if (SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
                    log.info("MQ发送批量消息成功,topicName={}  tags={}， size={},sendResult={}", topicName, tags, listItem.size(), sendResult);
                } else {
                    log.warn("MQ发送批量消息不一定成功,topicName={}  tags={}， size={},sendResult={}", topicName, tags, listItem.size(), sendResult);
                }
            } catch (Exception e) {
                //处理error
                log.error("MQ发送批量消息失败,topicName={}  tags={}，,errorMessage={}", topicName, tags, e.getMessage(), e);
                throw new RuntimeException("MQ发送批量消息失败,原因：" + e.getMessage());
            }
        }
    }

    @Override
    public SendResult sendDelayLevel(String key, Object msg, int delayTimeLevel) {
        Message<?> message = MessageBuilder
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
        Message<?> message = MessageBuilder
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
        Message<?> message = MessageBuilder
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
