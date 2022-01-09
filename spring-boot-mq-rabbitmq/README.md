# RabbitMQ

## 1 如何保障消息可靠生产(消息100%投递成功)

**方案：消息落库，对消息状态进行打标**

**步骤:**

1. 把消息发送MQ一份，同时存储到数据库(Redis)一份。开启消息发送确定机制，然后进行消息的状态控制，发送成功1，发送失败0。
2. 必须结合应答ACK(确认字符)来完成。对于那些如果没有发送成功的消息，可以采用定时器进行轮询发送。

**示例**

[application.yaml](spring-boot-rabbitmq/producer/src/main/resources/application.yml)

[定义生产者确认回调对象](spring-boot-rabbitmq/producer/src/main/java/com/example/config/ProducerAckConfirmCallback.java)





## 2 消息可靠消费

**步骤：**

1. 消费者开始手动ack消息确定
2. 尝试消息重投
3. 对重投还是消费失败的消息拒绝确认
4. 把拒绝确认的消息扔到死信队列中，并记录到数据库，再做好消息预警

**示例**

[application.yaml](spring-boot-rabbitmq/consumer/src/main/resources/application.yml)

需要开启下面的配置

```properties
spring.rabbitmq.listener.acknowledge-mode=manual  		 #消费者开启手动ack消息确认
spring.rabbitmq.listener.default-requeue-rejected=false  #设置为false，会重发消息到死信队列
```

**Consumer示例**

消费端手动确认[AckConsumer](spring-boot-rabbitmq/consumer/src/main/java/com/example/ack/AckConsumer.java)

死信队列[DeadLetterConsumer](spring-boot-rabbitmq/consumer/src/main/java/com/example/ack/DeadLetterConsumer.java)





## 3：待补充

1. 延时消息
