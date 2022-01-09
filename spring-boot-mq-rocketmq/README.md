# RocketMQ

## 1 如何保障消息可靠生产(消息100%投递成功)

官网描述，参考最佳实践：https://github.com/apache/rocketmq/blob/master/docs/cn/best_practice.md

#### 3 日志的打印

消息发送成功或者失败要打印消息日志，务必要打印SendResult和key字段。**send消息方法只要不抛异常，就代表发送成功**。发送成功会有多个状态，在sendResult里定义。以下对每个状态进行说明：

- **SEND_OK**

消息发送成功。要注意的是消息发送成功也不意味着它是可靠的。要确保不会丢失任何消息，还应启用**同步Master服务器或同步刷盘，即SYNC_MASTER或SYNC_FLUSH。**

- **FLUSH_DISK_TIMEOUT**

消息发送成功但是服务器刷盘超时。此时消息已经进入服务器队列（内存），只有服务器宕机，消息才会丢失。消息存储配置参数中可以设置刷盘方式和同步刷盘时间长度，如果Broker服务器设置了刷盘方式为同步刷盘，即FlushDiskType=SYNC_FLUSH（默认为异步刷盘方式），当Broker服务器未在同步刷盘时间内（默认为5s）完成刷盘，则将返回该状态——刷盘超时。

- **FLUSH_SLAVE_TIMEOUT**

消息发送成功，但是服务器同步到Slave时超时。此时消息已经进入服务器队列，只有服务器宕机，消息才会丢失。如果Broker服务器的角色是同步Master，即SYNC_MASTER（默认是异步Master即ASYNC_MASTER），并且从Broker服务器未在同步刷盘时间（默认为5秒）内完成与主服务器的同步，则将返回该状态——数据同步到Slave服务器超时。

- **SLAVE_NOT_AVAILABLE**

消息发送成功，但是此时Slave不可用。如果Broker服务器的角色是同步Master，即SYNC_MASTER（默认是异步Master服务器即ASYNC_MASTER），但没有配置slave Broker服务器，则将返回该状态——无Slave服务器可用。

**小结：**

**只要sendResult为SEND_OK状态，且同步Master服务器或同步刷盘，就可确认成功。**



## 2 消息可靠消费

相对RabbitMQ，RocketMQ的可靠消费并不需要我们做过多的处理。会自动把消费失败的消息扔到死信队列中。只需要做好消息预警即可。

[Consumer示例](spring-boot-rocketmq/rocketmq-consumer/src/main/java/com/example/service/ConsumerCluster.java)





## 3: 参考资料

官方中文文档：https://github.com/apache/rocketmq/tree/master/docs/cn