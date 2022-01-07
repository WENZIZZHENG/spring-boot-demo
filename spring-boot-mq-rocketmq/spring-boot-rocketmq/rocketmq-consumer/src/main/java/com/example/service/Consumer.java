package com.example.service;

import com.example.dto.OrderPaidEvent;
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
 * 测试
 * </p>
 *
 * @author MrWen
 */
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
        consumeTimeout = 15L//超时时间，以分钟为单位，默认15L
)
public class Consumer implements RocketMQListener<OrderPaidEvent>, RocketMQPushConsumerLifecycleListener {


    /**
     * 消费者
     * 程序报错则进行重试
     *
     * @param message 接收的消息
     */
    @Override
    public void onMessage(OrderPaidEvent message) {
        log.info("Receive message: {}  ThreadName: {}", message, Thread.currentThread().getName());
    }


    /**
     * consumer配置都是通过这个
     *
     * @param consumer consumer配置
     */
    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        //设最大重试次数，默认16次
        //距离上一次重试间隔
        //第1次：10s    第2次：30s     第3次：1min    第4次：2min     第5次：3min     第6次：4min     第7次：5min    第8次：6min
        //第9次：7min   第10次：8min   第11次：9min   第12次：10min   第13次：20min   第14次：30min   第15次：1h     第16次：2h   16次以后：都是2h
        //某条消息在一直消费失败的前提下，将会在接下来的 4 小时 46 分钟之内进行 16 次重试，超过这个时间范围消息将不再重试投递。
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
