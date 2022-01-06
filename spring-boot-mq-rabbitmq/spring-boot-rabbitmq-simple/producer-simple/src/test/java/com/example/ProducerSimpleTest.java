package com.example;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>
 * 生产者
 * </p>
 *
 * @author MrWen
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitMQSimpleProducerApplication.class)
public class ProducerSimpleTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void sendMessage() {
        for (int i = 0; i < 10; i++) {
            /**
             * String exchange: 交换机
             * String routingKey: 路由key(队列名称)
             * Object message: 消息内容
             */
            rabbitTemplate.convertAndSend("", "work-queue", "工作队列模式的消息！" + i);
        }
    }
}
