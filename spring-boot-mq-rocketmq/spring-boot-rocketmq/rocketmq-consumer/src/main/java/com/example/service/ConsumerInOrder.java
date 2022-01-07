package com.example.service;

import com.example.dto.OrderStep;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 顺序消息消费
 * </p>
 *
 * @author MrWen
 */
@Slf4j
//@Component
@RocketMQMessageListener(topic = "ProducerInOrder",//主题
        consumerGroup = "ProducerInOrder_group",//消费组
        consumeMode = ConsumeMode.ORDERLY//消费类型  ORDERLY 一个队列一个线程，即分区有序
)
public class ConsumerInOrder implements RocketMQListener<OrderStep>, RocketMQPushConsumerLifecycleListener {

    /**
     * 消费者
     * 程序报错则进行重试  todo 顺序消息报错？？
     *
     * @param msg
     */
    @Override
    public void onMessage(OrderStep msg) {
        try {
            //模拟业务逻辑处理中...
            TimeUnit.SECONDS.sleep(10);
            log.info("Receive message: {}  ThreadName: {}", msg, Thread.currentThread().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * consumer配置都是通过这个
     *
     * @param consumer
     */
    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        //设 最大重试次数  默认16次
        //距离上一次重试间隔：  1 10s  2 30s 3 1min 4 2min 5 3min 5 4min
        //如果消息重试 16 次后仍然失败，消息将不再投递。如果严格按照上述重试时间间隔计算，
        //某条消息在一直消费失败的前提下，将会在接下来的 4 小时 46 分钟之内进行 16 次重试，超过这个时间范围消息将不再重试投递。
        //最大重试次数大于 16 次，超过 16 次的重试时间间隔均为每次 2 小时。
        consumer.setMaxReconsumeTimes(3);

        /**
         * 关于消费位点  默认
         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
         * 如果非第一次启动，那么按照上次消费的位置继续消费
         * 可以 CONSUME_FROM_TIMESTAMP 来消费在指定时间戳后产生的消息。
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        // set consumer consume message from now
//        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_TIMESTAMP);
        // 设置消费位点时间
//        consumer.setConsumeTimestamp(UtilAll.timeMillisToHumanString3(System.currentTimeMillis()));
    }
}
