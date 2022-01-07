package com.example.service;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;

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
     * 要确保不会丢失任何消息，还应启用同步Master服务器或同步刷盘，即SYNC_MASTER或SYNC_FLUSH。）
     *
     * @param key 主题名:标签 topicName:tags
     * @param msg 发送对象
     * @return 发送结果，只有为SEND_OK且同步Master服务器或同步刷盘才保证100%投递成功
     */
    SendResult sendMessage(String key, Object msg);

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
     * 发生异步消息（异步消息通常用在对响应时间敏感的业务场景，即发送端不能容忍长时间地等待Broker的响应。）
     *
     * @param key          主题名:标签 topicName:tags
     * @param msg          发送对象
     * @param sendCallback 异步回调函数
     */
    void sendAsyncMessage(String key, Object msg, SendCallback sendCallback);

    /**
     * 发送单向消息（这种方式主要用在不特别关心发送结果的场景，例如日志发送。）
     *
     * @param key 主题名:标签 topicName:tags
     * @param msg 发送对象
     */
    void sendOneway(String key, Object msg);

    /**
     * 发送批量消息
     *
     * @param key  主题名:标签 topicName:tags
     * @param list 批量消息（小批量）
     */
//    void sendBatchMsg(String key, List<Object> list);

    /**
     * 发送延时消息（超时时间，默认30s）
     * 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     * 1  2  3   4   5  6  7  8  9  10 11 12 13 14  15  16  17 18
     *
     * @param key            主题名:标签 topicName:tags
     * @param msg            发送对象
     * @param delayTimeLevel 延时等级(从1开始)
     * @return 发送结果，只有为SEND_OK且同步Master服务器或同步刷盘才保证100%投递成功
     */
    SendResult sendDelayLevel(String key, Object msg, int delayTimeLevel);

    /**
     * 发送延时消息
     * 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     * 1  2  3   4   5  6  7  8  9  10 11 12 13 14  15  16  17 18
     *
     * @param key            主题名:标签 topicName:tags
     * @param msg            发送对象
     * @param timeout        超时时间（单位毫秒）
     * @param delayTimeLevel 延时等级(从1开始)
     * @return 发送结果，只有为SEND_OK且同步Master服务器或同步刷盘才保证100%投递成功
     */
    SendResult sendDelayLevel(String key, Object msg, int timeout, int delayTimeLevel);


    /**
     * 发送顺序消息(分区有序,多个queue参与，即相对每个queue，消息都是有序的。)
     *
     * @param key     主题名:标签 topicName:tags
     * @param msg     发送对象
     * @param hashKey 根据其哈希值取模后确定发送到哪一个queue队列
     * @return 发送结果，只有为SEND_OK且同步Master服务器或同步刷盘才保证100%投递成功
     */
    SendResult sendInOrder(String key, Object msg, String hashKey);

}
