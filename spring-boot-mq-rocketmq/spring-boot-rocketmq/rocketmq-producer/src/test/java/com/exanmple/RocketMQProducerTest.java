package com.exanmple;

import com.example.RocketMQProducerApplication;
import com.example.service.IRocketMQService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>
 * 测试类
 * </p>
 *
 * @author MrWen
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RocketMQProducerApplication.class)
public class RocketMQProducerTest {
    @Autowired
    private IRocketMQService rocketMQService;


    /**
     * 发送同步消息
     */
    @Test
    public void sendMessage() {
        for (int i = 0; i < 1; i++) {
            //广播消费端下的同步消息
            rocketMQService.sendMessage("ConsumerBroadcast", "广播同步消息" + i);
            //集群消费端下的同步消息
            //rocketMQService.sendMessage("ConsumerCluster", "集群同步消息" + i);
        }
    }

}
