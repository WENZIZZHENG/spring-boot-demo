package com.exanmple;

import com.example.RocketMQProducerApplication;
import com.example.service.IRocketMQService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

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
        for (int i = 0; i < 10; i++) {
            //广播消费端下的同步消息
            rocketMQService.sendMessage("ConsumerBroadcast", "广播同步消息" + i);
            //集群消费端下的同步消息
            rocketMQService.sendMessage("ConsumerCluster", "集群同步消息" + i);
        }
    }

    /**
     * 发送异步消息
     */
    @SneakyThrows
    @Test
    public void sendAsyncMessage() {
        for (int i = 0; i < 10; i++) {
            rocketMQService.sendAsyncMessage("ConsumerCluster", "集群异步消息" + i, new SendCallback() {
                //发送成功
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.info("集群异步消息发送成功啦！sendResult={}", sendResult);
                }

                //发送失败
                @Override
                public void onException(Throwable e) {
                    log.error("集群异步消息发送失败啦！原因={}", e.getMessage(), e);
                }
            });
        }

        //先睡20秒，避免还没发送完毕就关闭了
        TimeUnit.SECONDS.sleep(20L);
    }


    /**
     * 发送单向消息
     */
    @Test
    public void sendOneway() {
        for (int i = 0; i < 10; i++) {
            rocketMQService.sendOneway("ConsumerCluster", "集群单向消息" + i);
        }
    }


}
