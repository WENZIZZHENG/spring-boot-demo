package com.example.consumer;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 消费着
 * </p>
 *
 * @author MrWen
 * @since 2022-01-05 11:38
 */
@Component
public class Consumer {

    /**
     * 消息监听方法
     * queuesToDeclare: 声明队列
     * durable: 是否为持久化队列
     */
    @RabbitListener(queuesToDeclare = @Queue(name = "work-queue", durable = "true"))
    public void handlerMessage(String msg) {
        System.out.println("=====消费者===: " + msg);
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
