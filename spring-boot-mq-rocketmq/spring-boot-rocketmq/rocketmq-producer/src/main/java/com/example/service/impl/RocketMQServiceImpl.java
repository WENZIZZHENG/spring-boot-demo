package com.example.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.service.IRocketMQService;
import com.example.service.util.ListSplitter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
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
    public SendResult sendMessage(String destination, Object msg) {
        String[] split = destination.split(":");
        if (split.length == 2) {
            return this.sendMessage(split[0], split[1], msg);
        }
        return this.sendMessage(destination, null, msg);
    }

    @Override
    public SendResult sendMessage(String topicName, String tags, Object msg) {
        return this.sendMessage(topicName, tags, null, msg);
    }

    @Override
    public SendResult sendMessage(String topicName, String tags, String key, Object msg) {
        MessageBuilder<?> messageBuilder = MessageBuilder.withPayload(msg);
        //设置key,唯一标识码要设置到keys字段，方便将来定位消息丢失问题
        if (StringUtils.isNotBlank(key)) {
            messageBuilder.setHeader(MessageConst.PROPERTY_KEYS, key);
        }
        Message<?> message = messageBuilder.build();
        SendResult sendResult = this.rocketMqTemplate.syncSend(StringUtils.isBlank(tags) ? topicName : (topicName + ":" + tags), message);
        if (SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
            log.info("MQ发送同步消息成功,topicName={},tags={},msg={},sendResult={}", topicName, tags, msg, sendResult);
        } else {
            log.warn("MQ发送同步消息不一定成功,topicName={},tags={},msg={},sendResult={}", topicName, tags, msg, sendResult);
        }
        return sendResult;
    }

    @Override
    public SendResult sendMessageBySql(String topicName, Map<String, Object> map, Object msg) {
        return this.sendMessageBySql(topicName, map, null, msg);
    }

    @Override
    public SendResult sendMessageBySql(String topicName, Map<String, Object> map, String key, Object msg) {
        MessageBuilder<?> messageBuilder = MessageBuilder.withPayload(msg);
        //设置key,唯一标识码要设置到keys字段，方便将来定位消息丢失问题
        if (StringUtils.isNotBlank(key)) {
            messageBuilder.setHeader(MessageConst.PROPERTY_KEYS, key);
        }
        //设置自定义属性
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                messageBuilder.setHeader(entry.getKey(), entry.getValue());
            }
        }
        Message<?> message = messageBuilder.build();
        SendResult sendResult = this.rocketMqTemplate.syncSend(topicName, message);
        if (SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
            log.info("发送同步消息-SQL92模式成功,topicName={},map={},msg={},sendResult={}", topicName, map, msg, sendResult);
        } else {
            log.warn("发送同步消息-SQL92模式不一定成功,topicName={},map={},msg={},sendResult={}", topicName, map, msg, sendResult);
        }
        return sendResult;
    }

    @Override
    public void sendAsyncMessage(String destination, Object msg, SendCallback sendCallback) {
        this.rocketMqTemplate.asyncSend(destination, msg, sendCallback);
        log.info("MQ发送异步消息,destination={} msg={}", destination, msg);
    }

    @Override
    public void sendOneway(String destination, Object msg) {
        this.rocketMqTemplate.sendOneWay(destination, msg);
        log.info("MQ发送单向消息,destination={} msg={}", destination, msg);
    }

    @Override
    public void sendBatchMessage(String destination, List<?> list) {
        String topicName = destination;
        String tags = "";

        String[] split = destination.split(":");
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
    public SendResult sendDelayLevel(String destination, Object msg, int delayTimeLevel) {
        return this.sendDelayLevel(destination, msg, 30000, delayTimeLevel);
    }

    @Override
    public SendResult sendDelayLevel(String destination, Object msg, int timeout, int delayTimeLevel) {
        Message<?> message = MessageBuilder
                .withPayload(msg)
                .build();
        SendResult sendResult = this.rocketMqTemplate.syncSend(destination, message, timeout, delayTimeLevel);
        if (SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
            log.info("MQ发送延时消息成功,destination={} msg={} sendResult={}", destination, message, sendResult);
        } else {
            log.warn("MQ发送延时消息不一定成功,destination={} msg={} sendResult={}", destination, message, sendResult);
        }
        return sendResult;
    }

    @Override
    public SendResult sendInOrder(String destination, Object msg, String hashKey) {
        Message<?> message = MessageBuilder
                .withPayload(msg)
                .build();
        //hashKey:  根据其哈希值取模后确定发送到哪一个队列
        SendResult sendResult = this.rocketMqTemplate.syncSendOrderly(destination, message, hashKey);
        if (SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
            log.info("MQ发送顺序消息成功,destination={} msg={} sendResult={}", destination, message, sendResult);
        } else {
            log.warn("MQ发送顺序消息不一定成功,destination={} msg={} sendResult={}", destination, message, sendResult);
        }
        return sendResult;
    }

    @Override
    public SendResult sendMessageInTransaction(String destination, Object msg, Object arg) {
        Message<?> message = MessageBuilder
                //转为JSON格式
                .withPayload(msg instanceof String ? msg : JSON.toJSONString(msg))
                .build();

        TransactionSendResult sendResult = rocketMqTemplate.sendMessageInTransaction(destination, message, arg);

        if (SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
            log.info("MQ发送事务消息成功,destination={} msg={} sendResult={}", destination, message, sendResult);
        } else {
            log.warn("MQ发送事务消息不一定成功,destination={} msg={} sendResult={}", destination, message, sendResult);
        }
        return sendResult;
    }
}
