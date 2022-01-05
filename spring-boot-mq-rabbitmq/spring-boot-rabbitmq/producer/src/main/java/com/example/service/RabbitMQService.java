package com.example.service;

/**
 * <p>
 * RabbitMQ生产端常用方法整合
 * </p>
 *
 * @author MrWen
 **/
public interface RabbitMQService {

    /**
     * 发送队列模式消息(工作模式)
     *
     * @param queue 队列名称
     * @param msg   消息内容(所有发送MQ的消息，需要序列化，下面不再叙述)
     */
    void sendMessageByWork(String queue, Object msg);

    /**
     * 发送队列模式消息(工作模式)
     *
     * @param queue 队列名称
     * @param msg   消息内容(所有发送MQ的消息，需要序列化，下面不再叙述)
     * @param ackId 开启消息发送确认机制，应答ackId，建议根据业务确认唯一值。配置参考{@link com.example.config.ProducerAckConfirmCallback}
     *              (需要配置spring.rabbitmq.publisher-confirms=true)：低版本
     *              或(spring.rabbitmq.publisher-confirm-type=correlated):高版本
     */
    void sendMessageByWork(String queue, Object msg, String ackId);

    /**
     * 发送队列模式消息（发布与订阅模式，路由，主题模式模式，主要看消费者）
     *
     * @param exchange   交换机
     * @param routingKey 路由key，为空则是 发布与订阅模式（还是看消费者）
     * @param msg        消息内容(所有发送MQ的消息，需要序列化，下面不再叙述)
     */
    void sendMessageByExchange(String exchange, String routingKey, Object msg);

    /**
     * 发送队列模式消息（发布与订阅模式，路由，主题模式模式，主要看消费者）
     *
     * @param exchange   交换机
     * @param routingKey 路由key，为空则是 发布与订阅模式（还是看消费者）
     * @param msg        消息内容(所有发送MQ的消息，需要序列化，下面不再叙述)
     * @param ackId      开启消息发送确认机制，应答ackId，建议根据业务确认唯一值。配置参考{@link com.example.config.ProducerAckConfirmCallback}
     *                   (需要配置spring.rabbitmq.publisher-confirms=true)：低版本
     *                   或(spring.rabbitmq.publisher-confirm-type=correlated):高版本
     */
    void sendMessageByExchange(String exchange, String routingKey, Object msg, String ackId);
}
