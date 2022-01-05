package com.example;

import com.example.service.RabbitMQService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>
 * 生产者
 * </p>
 *
 * @author MrWen
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitMQProducerApplication.class)
public class ProducerTest {
    @Autowired
    private RabbitMQService rabbitMQService;


    /*
    发送消息时候，需要创建对应的队列和交换机
    可以手动在MQ控制台创建
    可以直接启动消费端即可
     */

    /**
     * 工作队列模式
     * 只有一个消费者能接收到消息(竞争的消费者模式)
     */
    @Test
    public void sendWord() {
        for (int i = 0; i < 50; i++) {
            rabbitMQService.sendMessageByWork("work-queue", "工作队列模式的消息！" + i);
        }
    }

    /**
     * 工作队列模式,演示发送应答ack，其它模式也一样
     */
    @Test
    public void sendWordAndAck() {
        for (int i = 0; i < 50; i++) {
            rabbitMQService.sendMessageByWork("work-queue", "工作队列模式的消息！" + i, "工作模式ackId" + i);
        }
    }


    /**
     * 发布与订阅模式-生产者通过交换机,同时向多个消费者发送消息
     * 所有消费者都能收到消息
     */
    @Test
    public void sendFanout() {
        for (int i = 0; i < 50; i++) {
            rabbitMQService.sendMessageByExchange("fanout-exchange", null, "发布与订阅模式的消息！" + i);
        }
    }


    /**
     * 路由模式-交换机和队列进行绑定，并且指定routing key，当发送消息到交换机后，交换机会根据routing key将消息发送到对应的队列
     * 符合路由（routing key，固定）的消费者收到
     */
    @Test
    public void sendDirect() {
        for (int i = 0; i < 50; i++) {
            String routingKey = i % 2 == 0 ? "error" : "info";
            rabbitMQService.sendMessageByExchange("direct-exchange", routingKey, "路由模式的消息，路由routingKey=" + routingKey + "  i=" + i);
        }
    }

    /**
     * 主题模式-通配符/主题模式-交换机和队列进行绑定，并且通配符方式routing key，当发送消息到交换机后，交换机会根据routing key将消息发送到对应的队列
     * 符合路由（routing key，通配符）的消费者收到
     */
    @Test
    public void sendTopic() {
        for (int i = 0; i < 50; i++) {
            String routingKey = i % 2 == 0 ? "topic.log.info" : "topic.log.error";
            rabbitMQService.sendMessageByExchange("topic-exchange", routingKey, "主题模式的消息，路由routingKey=" + routingKey + "  i=" + i);
        }
    }

    /**
     * 主题模式-测试消费端的ack
     * 消费端需要配置 spring.rabbitmq.listener.acknowledge-mode=manual  消费者开启手动ack消息确认,所有队列都会生效
     * 消费端需要配置 spring.rabbitmq.listener.default-requeue-rejected=false  设置为false，会重发消息到死信队列,所有队列都会生效
     */
    @Test
    public void sendTopicByConsumerAck() {
        //消费端最大消息堆积是3，这里发10条会有几条瞬间被扔到死信队列中，剩下的消费失败被拒绝确认后，才会扔到死信队列中
        //当然，也可以设置为3，测试消费失败被拒绝确认，扔到死信队列中的情景
        for (int i = 0; i < 10; i++) {
            String routingKey = i % 2 == 0 ? "topicAck.log.info" : "topicAck.log.error";
            rabbitMQService.sendMessageByExchange("topicAck-exchange", routingKey, "主题模式-测试消费端的ack，路由routingKey=" + routingKey + "  i=" + i);
        }
    }
}
