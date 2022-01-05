package com.example.ack;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * <p>
 * 消费端-手动确认ack，
 * 需要配置 spring.rabbitmq.listener.acknowledge-mode=manual  消费者开启手动ack消息确认
 * 需要配置 spring.rabbitmq.listener.default-requeue-rejected=false  设置为false，会重发消息到死信队列
 *
 * </p>
 *
 * @author MrWen
 **/
@Component
public class AckConsumer {

    /**
     * 消息监听方法
     * bindings: 完成队列与交换机的绑定
     * Queue: 队列属性，超过最大值，超时未被消费，消费失败超过重试次数，都会被仍到信息队列中
     * exchange：交换机属性
     * key：路由key,通配符
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "${topicAck.ack.queue}", durable = "true", arguments = {
                    @Argument(name = "x-max-length", value = "3", type = "java.lang.Long"), // 队列的最大存储界限,这里示例设为3
                    @Argument(name = "x-message-ttl", value = "5000000", type = "java.lang.Long"), // 消息过期时间，多久没有被消费
                    @Argument(name = "x-dead-letter-exchange", value = "${dead.exchange}"), // 死信队列交换机-dead-exchange
                    @Argument(name = "x-dead-letter-routing-key", value = "xxx")}), // 死信队列路由key
            exchange = @Exchange(name = "${topicAck.exchange}", type = ExchangeTypes.TOPIC),// 交换机
            key = {"#"}// 路由key,通配符
    ))
    public void handlerMessage(String msg, Channel channel, Message message) throws IOException {
        try {
            System.out.printf("================AckConsumer接受到消息，准备消费,msg=%s,================", msg);
            System.out.println();
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("AckConsumer--->接受到的消息是：" + msg);

            // 业务处理
            int i = 10 / 0;

            // 手动ack确认
            //参数1：deliveryTag:消息唯一传输ID
            //参数2：multiple：true: 手动批量处理，false: 手动单条处理
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.printf("================AckConsumer消费成功,msg=%s,================", msg);
            System.out.println();
        } catch (Exception ex) {
            // 如果真得出现了异常，我们采用消息重投,获取redelivered，判断是否为重投: false没有重投，true重投
            Boolean redelivered = message.getMessageProperties().getRedelivered();
            System.out.println("redelivered = " + redelivered);

            try {
                // (已重投)拒绝确认
                if (redelivered) {
                    /**
                     * 拒绝确认，从队列中删除该消息，防止队列阻塞(消息堆积)
                     * boolean requeue: false不重新入队列(丢弃消息)
                     */
                    channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
                    System.out.printf("================AckConsumer消费消息已投入死信队列,msg=%s,================", msg);
                    System.out.println();
                } else { // (没有重投) 消息重投
                    /**
                     * 消息重投，重新把消息放回队列中
                     * boolean multiple: 单条或批量
                     * boolean requeue: true重回队列
                     */
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                    System.out.println("=========消息重投了=======");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
