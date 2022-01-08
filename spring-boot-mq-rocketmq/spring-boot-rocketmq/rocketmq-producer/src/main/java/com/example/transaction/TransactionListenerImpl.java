package com.example.transaction;

import com.alibaba.fastjson.JSON;
import com.example.dto.OrderPaidEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 * RocektMQ 事务消息监听器
 * </p>
 *
 * @author MrWen
 **/
@Slf4j
@RocketMQTransactionListener
public class TransactionListenerImpl implements RocketMQLocalTransactionListener {


    /**
     * 检测半消息，在该方法中，执行本地事务
     *
     * @param msg 发送消息
     * @param arg 外部参数
     * @return commit：提交事务，它允许消费者消费此消息。bollback：回滚事务，它代表该消息将被删除，不允许被消费。 unknown：中间状态，它代表需要检查消息队列来确定状态（checkLocalTransaction方法）。
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        log.info(">>>> MQ事务执行器，执行本地事务 message={},args={} <<<<", msg, arg);

        try {
            String jsonString = new String((byte[]) msg.getPayload(), StandardCharsets.UTF_8);
            OrderPaidEvent payload = JSON.parseObject(jsonString, OrderPaidEvent.class);

            //模拟业务操作，当paidMoney >5 则提交，否则等事务会查
            if (payload.getPaidMoney().compareTo(new BigDecimal("5")) > 0) {
                //提交事务
                log.info("MQ提交事务啦！payload ={} ", payload);
                return RocketMQLocalTransactionState.COMMIT;
            }

            //不知道状态，转 checkLocalTransaction 回查执行
            log.info("MQ无法确定，等回查！payload ={} ", payload);
            return RocketMQLocalTransactionState.UNKNOWN;
        } catch (Exception e) {
            log.error("事务消息出错啦~ e:{}", e.getMessage(), e);
            //回滚
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }


    /**
     * 该方法时MQ进行消息事务状态回查、
     * <p>
     *
     * @param msg
     * @return bollback, commit or unknown
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        log.info(">>>> MQ事务执行器，事务状态回查 message={} <<<<", msg);
        try {
            String jsonString = new String((byte[]) msg.getPayload(), StandardCharsets.UTF_8);
            OrderPaidEvent payload = JSON.parseObject(jsonString, OrderPaidEvent.class);

            log.info("事务回查：checkLocalTransaction提交事务啦！payload ={} ", payload);
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            log.error("回调的事务出错啦~ e:{}", e.getMessage(), e);
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }
}
