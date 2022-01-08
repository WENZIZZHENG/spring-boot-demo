package com.example.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 过滤消息消费，SQL92模式
 * 需要配置RocketMQ服务器  vim conf/broker.conf  ##支持sql语句过滤  enablePropertyFilter=true
 * 在console控制台查看集群状态  enablePropertyFilter=true 才正常
 * </p>
 * <p>
 * <p>
 * 数值比较，比如：>，>=，<，<=，BETWEEN，=；
 * 字符比较，比如：=，<>，IN；
 * IS NULL 或者 IS NOT NULL；
 * 逻辑符号 AND，OR，NOT；
 * <p>
 * 常量支持类型为：
 * <p>
 * 数值，比如：123，3.1415；
 * 字符，比如：'abc'，必须用单引号包裹起来；
 * NULL，特殊的常量
 * 布尔值，TRUE 或 FALSE
 *
 * @author MrWen
 **/
@Slf4j
@Component
@RocketMQMessageListener(topic = "Consumer_SQL",//主题
        consumerGroup = "Consumer_SQL_group",//消费组  唯一
        /* 下面都有默认值,可选  */
        selectorType = SelectorType.SQL92,//过滤选项类型 默认TAG 还有 SQL92，Brro
        selectorExpression = "a between 0 and 3 or b='sql'"
)
public class ConsumerSQL implements RocketMQListener<String>, RocketMQPushConsumerLifecycleListener {


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
            log.info("ConsumerSQL 消费 message: {}  ", message);
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
