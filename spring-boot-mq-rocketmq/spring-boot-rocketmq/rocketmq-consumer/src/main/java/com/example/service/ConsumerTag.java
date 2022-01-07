package com.example.service;

import com.example.dto.OrderPaidEvent;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;

/**
 * <p>
 * 过滤消息消费，Tag模式
 * </p>
 *
 * @author MrWen
 **/
@Slf4j
//@Component
@RocketMQMessageListener(topic = "synchronously",//主题
        consumerGroup = "synchronously_group",//消费组  唯一
        /* 下面都有默认值,可选  */
        selectorType = SelectorType.TAG,//过滤选项类型 默认TAG 还有 SQL92
        selectorExpression = "*",//过滤选项 默认*    Tag多个时，"tag1 || tag2 || tag3"
        consumeMode = ConsumeMode.CONCURRENTLY,//消费类型  默认CONCURRENTLY同步  还有有序 ORDERLY 一个队列一个线程
        messageModel = MessageModel.CLUSTERING, //消费模式 默认CLUSTERING集群  还有 广播CLUSTERING（接收所有信息）
        consumeThreadMax = 64,//最大线程数，默认64
        consumeTimeout = 30000L//超时时间，默认30s
)
//@RocketMQMessageListener(topic = "${demo.rocketmq.topic}", consumerGroup = "string_consumer", selectorExpression = "${demo.rocketmq.tag}") //常用
public class ConsumerTag implements RocketMQListener<OrderPaidEvent>, RocketMQPushConsumerLifecycleListener {


    /**
     * 消费者
     * 程序报错则进行重试
     *
     * @param msg
     */
    @SneakyThrows
    @Override
    public void onMessage(OrderPaidEvent msg) {
        log.info("Receive message: {}  ThreadName: {}", msg, Thread.currentThread().getName());
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
