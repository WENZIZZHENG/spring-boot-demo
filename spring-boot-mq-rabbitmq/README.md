# RabbitMQ







## 2 如何保障消息可靠生产(消息100%投递成功)

**方案：消息落库，对消息状态进行打标**

**步骤:**

1. 把消息发送MQ一份，同时存储到数据库(Redis)一份。开启消息发送确定机制，然后进行消息的状态控制，发送成功1，发送失败0。
2. 必须结合应答ACK(确认字符)来完成。对于那些如果没有发送成功的消息，可以采用定时器进行轮询发送。

**示例**

[application.yaml](spring-boot-rabbitmq/producer/src/main/resources/application.yml)

[定义生产者确认回调对象](spring-boot-rabbitmq/producer/src/main/java/com/example/config/ProducerAckConfirmCallback.java)

