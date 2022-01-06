package com.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>
 * rabbitmq测试
 * </p>
 *
 * @author MrWen
 * @since 2022-01-06 16:37
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RocketMQSimpleProducerApplication.class)
public class ProducerSimpleTest {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    @Test
    public void sendMessage() {
        String key = "simple";
        for (int i = 0; i < 50; i++) {
            String message = "发送同步消息,msg=" + i;
            /*
            第一个参数，主题名:标签 topicName:tags
            第二个参数：发送对象
             */
            SendResult sendResult = this.rocketMQTemplate.syncSend(key, message);
            log.info("MQ发送同步消息成功,key={} msg={},sendResult={}", key, message, sendResult);
        }
    }


}
