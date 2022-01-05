package com.example.ack;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * <p>
 * 死信队列消费者
 * </p>
 *
 * @author MrWen
 **/
@Component
public class DeadLetterConsumer {

    /**
     * 监听消息的方法
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "${dead.queue}", durable = "true"), // 队列
            exchange = @Exchange(name = "${dead.exchange}", type = ExchangeTypes.TOPIC),// 交换机
            key = {"#"}// 路由key
    ))
    public void handlerMessage(String msg, Channel channel, Message message) throws IOException {
        //其实这里也要像AckConsumer一样处理，这里只是简单的确认即可
        System.out.println("死信队列接收到的消息：" + msg);
        // 手动ack确认
        //参数1：deliveryTag:消息唯一传输ID
        //参数2：multiple：true: 手动批量处理，false: 手动单条处理
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        System.out.println("死信队列接已手动确认，消息：" + msg);
    }

}
