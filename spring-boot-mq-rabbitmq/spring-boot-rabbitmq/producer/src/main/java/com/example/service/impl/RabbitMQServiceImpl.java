package com.example.service.impl;

import com.example.service.RabbitMQService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * RabbitMQ生产端常用方法整合
 * </p>
 *
 * @author MrWen
 **/
@Service
public class RabbitMQServiceImpl implements RabbitMQService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendMessageByWork(String queue, Object msg) {
        rabbitTemplate.convertAndSend("", queue, msg);
    }

    @Override
    public void sendMessageByWork(String queue, Object msg, String ackId) {
        rabbitTemplate.convertAndSend("", queue, msg, new CorrelationData(ackId));
    }

    @Override
    public void sendMessageByExchange(String exchange, String routingKey, Object msg) {
        rabbitTemplate.convertAndSend(exchange, routingKey == null ? "" : routingKey, msg);
    }

    @Override
    public void sendMessageByExchange(String exchange, String routingKey, Object msg, String ackId) {
        rabbitTemplate.convertAndSend(exchange, routingKey == null ? "" : routingKey, msg, new CorrelationData(ackId));
    }
}
