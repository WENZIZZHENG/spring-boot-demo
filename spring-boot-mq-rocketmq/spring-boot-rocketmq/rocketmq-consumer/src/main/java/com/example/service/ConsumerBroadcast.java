package com.example.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 广播模式消费，每个消费者消费的消息都是相同的
 * </p>
 *
 * @author MrWen
 **/
@Slf4j
@Component
@RocketMQMessageListener(topic = "Consumer_Broadcast",//主题
        consumerGroup = "Consumer_Broadcast_group",//消费组  唯一
        messageModel = MessageModel.BROADCASTING //消费模式 默认CLUSTERING集群  BROADCASTING:广播（接收所有信息）
)
public class ConsumerBroadcast implements RocketMQListener<String> {

    //todo 广播消费模式宽带消费仍然确保消息至少被消费一次，但是没有提供重发选项。

    /**
     * 消费者
     * 程序报错则进行重试
     *
     * @param message 接收的消息
     */
    @Override
    public void onMessage(String message) {
        try {
            //模拟业务逻辑处理中...
            log.info("ConsumerBroadcast  广播模式消费 message: {}  ", message);
            TimeUnit.SECONDS.sleep(10);
            //模拟出错，触发重试
//            int i = 1 / 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }


}
