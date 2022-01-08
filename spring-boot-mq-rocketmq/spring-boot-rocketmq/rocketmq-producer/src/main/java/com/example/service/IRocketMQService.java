package com.example.service;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * RocektMQ生产者常用发送消息方法
 * 最佳实践:https://github.com/apache/rocketmq/blob/master/docs/cn/best_practice.md
 * </p>
 *
 * @author MrWen
 * @since 2022-01-06 17:10
 **/
public interface IRocketMQService {

    /**
     * 发送同步消息（这种可靠性同步地发送方式使用的比较广泛，比如：重要的消息通知，短信通知。）
     * <p>
     * （send消息方法只要不抛异常，就代表发送成功。但不保证100%可靠投递(所有消息都一样，后面不在叙述)。
     * 要确保不会丢失任何消息，还应启用同步Master服务器或同步刷盘，即SYNC_MASTER或SYNC_FLUSH。
     * 解析看：https://github.com/apache/rocketmq/blob/master/docs/cn/best_practice.md
     * ）
     *
     * @param destination 主题名:标签 topicName:tags
     * @param msg         发送对象
     * @return 发送结果，只有为SEND_OK且同步Master服务器或同步刷盘才保证100%投递成功
     */
    SendResult sendMessage(String destination, Object msg);

    /**
     * 发送同步消息（这种可靠性同步地发送方式使用的比较广泛，比如：重要的消息通知，短信通知。）
     *
     * @param topicName 主题名 topicName
     * @param tags      标签 tags
     * @param msg       发送对象
     * @return 发送结果，只有为SEND_OK且同步Master服务器或同步刷盘才保证100%投递成功
     */
    SendResult sendMessage(String topicName, String tags, Object msg);

    /**
     * 发送同步消息（这种可靠性同步地发送方式使用的比较广泛，比如：重要的消息通知，短信通知。）
     *
     * @param topicName 主题名 topicName
     * @param tags      标签 tags
     * @param key       唯一标识码要设置到keys字段，方便将来定位消息丢失问题
     * @param msg       发送对象
     * @return 发送结果，只有为SEND_OK且同步Master服务器或同步刷盘才保证100%投递成功
     */
    SendResult sendMessage(String topicName, String tags, String key, Object msg);

    /**
     * 发送同步消息-SQL92模式
     * 需要配置RocketMQ服务器  vim conf/broker.conf  ##支持sql语句过滤  enablePropertyFilter=true
     * 在console控制台查看集群状态  enablePropertyFilter=true 才正常
     *
     * @param topicName 主题名 topicName
     * @param map       自定义属性
     * @param msg       发送对象
     * @return 发送结果，只有为SEND_OK且同步Master服务器或同步刷盘才保证100%投递成功
     */
    SendResult sendMessageBySql(String topicName, Map<String, Object> map, Object msg);


    /**
     * 发送同步消息-SQL92模式
     * 需要配置RocketMQ服务器  vim conf/broker.conf  ##支持sql语句过滤  enablePropertyFilter=true
     * 在console控制台查看集群状态  enablePropertyFilter=true 才正常
     *
     * @param topicName 主题名 topicName
     * @param map       自定义属性
     * @param key       唯一标识码要设置到keys字段，方便将来定位消息丢失问题
     * @param msg       发送对象
     * @return 发送结果，只有为SEND_OK且同步Master服务器或同步刷盘才保证100%投递成功
     */
    SendResult sendMessageBySql(String topicName, Map<String, Object> map, String key, Object msg);


    /**
     * 发生异步消息（异步消息通常用在对响应时间敏感的业务场景，即发送端不能容忍长时间地等待Broker的响应。）
     *
     * @param destination  主题名:标签 topicName:tags
     * @param msg          发送对象
     * @param sendCallback 异步回调函数
     */
    void sendAsyncMessage(String destination, Object msg, SendCallback sendCallback);

    /**
     * 发送单向消息（这种方式主要用在不特别关心发送结果的场景，例如日志发送。）
     *
     * @param destination 主题名:标签 topicName:tags
     * @param msg         发送对象
     */
    void sendOneway(String destination, Object msg);

    /**
     * 发送批量消息(发送超过1MB,做了自动分割,超时时间设置30s（默认3s）)，注：默认最大是4MB，为了避免ListSplitter.calcMessageSize计算不精确及大批量数据发送超时才设置1MB
     *
     * @param destination 主题名:标签 topicName:tags
     * @param list        批量消息
     */
    void sendBatchMessage(String destination, List<?> list);


    /**
     * 发送批量消息(发送超过1MB,做了自动分割。)，注：默认最大是4MB，为了避免ListSplitter.calcMessageSize计算不精确及大批量数据发送超时才设置1MB
     *
     * @param topicName 主题名 topicName
     * @param tags      标签 tags
     * @param timeout   超时时间,空则默认设为30s
     * @param list      批量消息
     */
    void sendBatchMessage(String topicName, String tags, Long timeout, List<?> list);

    /**
     * 发送延时消息（超时时间，设置30s（默认3s））
     * 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     * 1  2  3   4   5  6  7  8  9  10 11 12 13 14  15  16  17 18
     *
     * @param destination    主题名:标签 topicName:tags
     * @param msg            发送对象
     * @param delayTimeLevel 延时等级(从1开始)
     * @return 发送结果，只有为SEND_OK且同步Master服务器或同步刷盘才保证100%投递成功
     */
    SendResult sendDelayLevel(String destination, Object msg, int delayTimeLevel);

    /**
     * 发送延时消息
     * 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     * 1  2  3   4   5  6  7  8  9  10 11 12 13 14  15  16  17 18
     *
     * @param destination    主题名:标签 topicName:tags
     * @param msg            发送对象
     * @param timeout        超时时间（单位毫秒）
     * @param delayTimeLevel 延时等级(从1开始)
     * @return 发送结果，只有为SEND_OK且同步Master服务器或同步刷盘才保证100%投递成功
     */
    SendResult sendDelayLevel(String destination, Object msg, int timeout, int delayTimeLevel);


    /**
     * 发送顺序消息(分区有序,多个queue参与，即相对每个queue，消息都是有序的。)
     *
     * @param destination 主题名:标签 topicName:tags
     * @param msg         发送对象
     * @param hashKey     根据其哈希值取模后确定发送到哪一个queue队列
     * @return 发送结果，只有为SEND_OK且同步Master服务器或同步刷盘才保证100%投递成功
     */
    SendResult sendInOrder(String destination, Object msg, String hashKey);


    /**
     * 发送事务消息
     * 事务消息使用上的限制
     * 1:事务消息不支持延时消息和批量消息。
     * 2:为了避免单个消息被检查太多次而导致半队列消息累积，我们默认将单个消息的检查次数限制为 15 次，但是用户可以通过 Broker 配置文件的 transactionCheckMax参数来修改此限制。如果已经检查某条消息超过 N 次的话（ N = transactionCheckMax ） 则 Broker 将丢弃此消息，并在默认情况下同时打印错误日志。用户可以通过重写 AbstractTransactionalMessageCheckListener 类来修改这个行为。
     * 3:事务消息将在 Broker 配置文件中的参数 transactionTimeout 这样的特定时间长度之后被检查。当发送事务消息时，用户还可以通过设置用户属性 CHECK_IMMUNITY_TIME_IN_SECONDS 来改变这个限制，该参数优先于 transactionTimeout 参数。
     * 4:事务性消息可能不止一次被检查或消费。
     * 5:提交给用户的目标主题消息可能会失败，目前这依日志的记录而定。它的高可用性通过 RocketMQ 本身的高可用性机制来保证，如果希望确保事务消息不丢失、并且事务完整性得到保证，建议使用同步的双重写入机制。
     * 6:事务消息的生产者 ID 不能与其他类型消息的生产者 ID 共享。与其他类型的消息不同，事务消息允许反向查询、MQ服务器能通过它们的生产者 ID 查询到消费者。
     *
     * @param destination 主题名:标签 topicName:tags
     * @param msg         发送对象
     * @param arg         arg
     * @return 发送结果，只有为SEND_OK且同步Master服务器或同步刷盘才保证100%投递成功
     */
    SendResult sendMessageInTransaction(String destination, Object msg, Object arg);
}
