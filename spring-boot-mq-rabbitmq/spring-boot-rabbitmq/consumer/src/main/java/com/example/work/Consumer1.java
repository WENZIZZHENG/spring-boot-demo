package com.example.work;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * <p>
 * work工作队列模式的消费者-只有一个消费者能接收到消息(竞争的消费者模式)
 * </p>
 *
 * @author MrWen
 **/
@Component
public class Consumer1 {

    /**
     * 消息监听方法
     * queuesToDeclare: 声明队列
     * durable: 是否为持久化队列
     */
    @RabbitListener(queuesToDeclare = @Queue(name = "work-queue", durable = "true"))
    public void handlerMessage(String msg) {
        System.out.println("=====消费者1: " + msg);
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //模拟消费失败，触发重试
//        int i = 1 / 0;
    }

}
