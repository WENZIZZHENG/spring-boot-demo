package com.example.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 消费者
 * </p>
 *
 * @author MrWen
 * @since 2022-01-06 16:29
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = "simple",//主题
        consumerGroup = "simple-consumer-group"//消费组
)
public class Consumer implements RocketMQListener<String> {


    @Override
    public void onMessage(String message) {
        Random random = new Random();
        try {
            //模拟业务逻辑处理中...
            TimeUnit.SECONDS.sleep(random.nextInt(10));
            log.info("Receive message: {}  ThreadName: {}", message, Thread.currentThread().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
