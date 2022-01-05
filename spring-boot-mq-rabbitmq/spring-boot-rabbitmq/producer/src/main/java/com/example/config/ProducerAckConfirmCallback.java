package com.example.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * <p>
 * 定义生产者确认回调对象
 * 需要配置spring.rabbitmq.publisher-confirms=true  低版本
 * 或spring.rabbitmq.publisher-confirm-type=correlated 高版本
 * 每个RabbitTemplate只支持一个ConfirmCallback
 * </p>
 *
 * @author MrWen
 **/
@Slf4j
@Component
public class ProducerAckConfirmCallback implements RabbitTemplate.ConfirmCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    private void init() {
        //设置生产者确认回调对象
        rabbitTemplate.setConfirmCallback(this);
    }


    /**
     * 发送ack确认回调
     *
     * @param correlationData 这里获取唯一id
     * @param ack             是否确认收到(true已确认收到，false未确认收到)
     * @param cause           失败原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        //有些没有设置发送应答ack的，不需要走后续的逻辑
        if (correlationData == null) {
            return;
        }
        // 确认方法
        log.info("是否确认发送成功ack = {}  失败原因cause={}", ack, cause);
        // 如果为true，代表mq已成功接收消息
        if (ack) {
            // 从Redis数据中删除消息
            log.info("消息确认发送成功：correlationDataId = {}", correlationData.getId());
        } else {
            // 如果为false，代表mq没有接收到消息(消息生产失败)
            // 业务处理
            log.error("消息确认发送失败：correlationDataId = {}", correlationData.getId());
        }
    }
}
