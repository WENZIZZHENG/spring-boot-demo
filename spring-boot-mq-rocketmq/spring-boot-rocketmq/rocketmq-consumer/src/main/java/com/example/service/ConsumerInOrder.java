package com.example.service;

import com.example.dto.OrderStep;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 顺序消息消费-分区有序
 * </p>
 *
 * @author MrWen
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "Consumer_InOrder",//主题
        consumerGroup = "Consumer_InOrder_group",//消费组
        consumeMode = ConsumeMode.ORDERLY//消费类型  ORDERLY 一个队列一个线程，即分区有序
)
public class ConsumerInOrder implements RocketMQListener<OrderStep>, RocketMQPushConsumerLifecycleListener {

    /**
     * 消费者
     * 程序报错则进行重试
     *
     * @param msg
     */
    @Override
    public void onMessage(OrderStep msg) {
        log.info("Receive message: {}  ThreadName: {}", msg, Thread.currentThread().getName());
        //模拟出错，触发重试
//        int i = 1 / 0;
    }


    /**
     * consumer配置都是通过这个
     *
     * @param consumer consumer配置
     */
    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        //程序报错，顺序消息这里不会等待重试，会立即执行。不设最大重试次数，会一直不断重试执行。
        consumer.setMaxReconsumeTimes(3);

        //关于消费位点,默认CONSUME_FROM_LAST_OFFSET(从上一个偏移量消费)
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

        //关于消费位点,从第一个偏移量消费（即全量消费,正常消息相同存储均为 3 天，3 天后会被自动删除）
        //consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        //关于消费位点,以秒精度回溯消费时间，按指定回溯时间开始消费,默认回溯半小时前的消费时间。
        //时间格式为20131223171201<br>暗示2013年12月23日17点12分01秒<br>
        //consumer.setConsumeTimestamp(UtilAll.timeMillisToHumanString3(System.currentTimeMillis()));
        //consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_TIMESTAMP);
    }
}
