package com.example.service;

import com.example.dto.OrderPaidEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 事务消息演示
 * <p>
 * 事务消息使用上的限制
 * 1:事务消息不支持延时消息和批量消息。
 * 2:为了避免单个消息被检查太多次而导致半队列消息累积，我们默认将单个消息的检查次数限制为 15 次，但是用户可以通过 Broker 配置文件的 transactionCheckMax参数来修改此限制。如果已经检查某条消息超过 N 次的话（ N = transactionCheckMax ） 则 Broker 将丢弃此消息，并在默认情况下同时打印错误日志。用户可以通过重写 AbstractTransactionalMessageCheckListener 类来修改这个行为。
 * 3:事务消息将在 Broker 配置文件中的参数 transactionTimeout 这样的特定时间长度之后被检查。当发送事务消息时，用户还可以通过设置用户属性 CHECK_IMMUNITY_TIME_IN_SECONDS 来改变这个限制，该参数优先于 transactionTimeout 参数。
 * 4:事务性消息可能不止一次被检查或消费。
 * 5:提交给用户的目标主题消息可能会失败，目前这依日志的记录而定。它的高可用性通过 RocketMQ 本身的高可用性机制来保证，如果希望确保事务消息不丢失、并且事务完整性得到保证，建议使用同步的双重写入机制。
 * 6:事务消息的生产者 ID 不能与其他类型消息的生产者 ID 共享。与其他类型的消息不同，事务消息允许反向查询、MQ服务器能通过它们的生产者 ID 查询到消费者。
 * </p>
 *
 * @author MrWen
 **/
@Slf4j
@Component
@RocketMQMessageListener(topic = "Consumer_Transaction",//主题
        consumerGroup = "Consumer_Transaction_group"//消费组  唯一
)
public class ConsumerTransaction implements RocketMQListener<OrderPaidEvent>, RocketMQPushConsumerLifecycleListener {

    /**
     * 消费者
     * 程序报错则进行重试
     *
     * @param orderPaidEvent 接收的消息,订单支付事件
     */
    @Override
    public void onMessage(OrderPaidEvent orderPaidEvent) {
        try {
            //模拟业务逻辑处理中...
            log.info("ConsumerTransaction 事务消息消费 message: {}  ", orderPaidEvent);
            TimeUnit.SECONDS.sleep(10);
            //模拟出错，触发重试
//            int i = 1 / 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
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
