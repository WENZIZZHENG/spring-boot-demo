package com.example.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * <p>
 * redis常用方法
 * 对象和数组都以json形式进行存储
 * </p>
 *
 * @author MrWen
 **/
public interface IRedisService {

    /**
     * 存储数据
     *
     * @param key   key
     * @param value value
     */
    void set(String key, String value);

    /**
     * 存储数据
     *
     * @param key     key
     * @param value   value
     * @param seconds 有效时间。单位秒
     */
    void set(String key, String value, Long seconds);

    /**
     * 存储数据（如果key不在，设置key保存字符串值。才返回true）
     *
     * @param key   key
     * @param value value
     * @return 是否存储成功
     */
    boolean setnx(String key, String value);

    /**
     * 存储数据（如果key不在，设置key保存字符串值。才返回true）
     *
     * @param key     key
     * @param value   value
     * @param seconds 有效时间。单位秒
     * @return 是否存储成功
     */
    boolean setnx(String key, String value, Long seconds);

    /**
     * 存储数据
     *
     * @param key     key
     * @param value   value
     * @param timeout 有效时间
     * @param unit    时间单位
     */
    void setnx(String key, String value, long timeout, TimeUnit unit);


    /**
     * 获取锁（非阻塞）
     *
     * @param key     key
     * @param seconds 有效时间。单位秒
     * @return 是否成功
     */
    boolean setLock(String key, Long seconds);

    /**
     * 获取数据
     *
     * @param key key
     * @return result
     */
    String get(String key);

    /**
     * 获取数值型数据（不存在时，为0）
     *
     * @param key 自减步长
     * @return 不存在时，为0
     */
    Long getLong(String key);

    /**
     * 判断时候有值
     *
     * @param key key
     * @return 是否存在
     */
    boolean exists(String key);

    /**
     * 获取数据（多个）
     *
     * @param keys keys
     * @return result
     */
    List<String> get(List<String> keys);

    /**
     * 设置过期时间
     *
     * @param key    key
     * @param expire 过期时间。单位秒
     * @return 是否成功
     */
    boolean expire(String key, long expire);

    /**
     * 获取过期时间
     *
     * @param key key
     * @return 过期时间
     */
    Long getExpire(String key);

    /**
     * 删除数据(固定key)
     *
     * @param key key
     */
    void remove(String key);

    /**
     * 删除数据(支持正则表达式key，如：key:*，支持删除多条数据)
     *
     * @param key key:*
     */
    void removeByRegexKey(String key);

    /**
     * 自增操作
     *
     * @param key   key
     * @param delta 自增步长(一般都为正数)
     * @return result
     */
    Long increment(String key, long delta);

    /**
     * 自增操作
     *
     * @param key    key
     * @param delta  自增步长(一般都为正数)
     * @param expire 过期时间。单位秒（不会刷新）
     * @return result
     */
    Long increment(String key, long delta, long expire);

    /**
     * 自减操作
     *
     * @param key   key
     * @param delta 自减步长(一般都为正数)
     * @return result
     */
    Long decrement(String key, long delta);

    /**
     * 自减操作
     *
     * @param key    key
     * @param delta  自减步长(一般都为正数)
     * @param expire 过期时间。单位秒（不会刷新）
     * @return result
     */
    Long decrement(String key, long delta, long expire);


    /**
     * 获取对象集合
     *
     * @param key      Key
     * @param clazz    返回结果的类型
     * @param supplier 数据提供者，当缓存中不存在时，则数据提供者生成
     * @param <T>      结果类型
     * @return list结果
     */
    <T> List<T> getList(final String key, final Class<T> clazz, final Supplier<List<T>> supplier);

    /**
     * 获取对象集合(有过期时间)
     *
     * @param key      Key
     * @param clazz    返回结果的类型
     * @param supplier 数据提供者，当缓存中不存在时，则数据提供者生成
     * @param timeout  过期时间，单位秒
     * @param <T>      结果类型
     * @return list结果
     */
    <T> List<T> getList(final String key, final Class<T> clazz, final Supplier<List<T>> supplier, long timeout);

    /**
     * 获取对象集合(有过期时间，重试次数)
     *
     * @param key      Key
     * @param clazz    返回结果的类型
     * @param supplier 数据提供者，当缓存中不存在时，则数据提供者生成
     * @param timeout  过期时间，单位秒
     * @param number   重试次数
     * @param <T>      结果类型
     * @return list结果
     */
    <T> List<T> getList(final String key, final Class<T> clazz, final Supplier<List<T>> supplier, long timeout, int number);

    /**
     * 获取对象
     *
     * @param key      Key
     * @param clazz    返回结果的类型
     * @param supplier 数据提供者，当缓存中不存在时，则数据提供者生成
     * @param <T>      结果类型
     * @return 对象结果
     */
    <T> T getObject(final String key, final Class<T> clazz, final Supplier<T> supplier);

    /**
     * 获取对象(有过期时间)
     *
     * @param key      Key
     * @param clazz    返回结果的类型
     * @param supplier 数据提供者，当缓存中不存在时，则数据提供者生成
     * @param timeout  过期时间，单位秒
     * @param <T>      结果类型
     * @return 对象结果
     */
    <T> T getObject(final String key, final Class<T> clazz, final Supplier<T> supplier, long timeout);

    /**
     * 获取对象(有过期时间，重试次数)
     *
     * @param key      Key
     * @param clazz    返回结果的类型
     * @param supplier 数据提供者，当缓存中不存在时，则数据提供者生成
     * @param timeout  过期时间，单位秒
     * @param number   重试次数
     * @param <T>      结果类型
     * @return 对象结果
     */
    <T> T getObject(final String key, final Class<T> clazz, final Supplier<T> supplier, long timeout, int number);


    /**
     * set添加元素
     *
     * @param key    key
     * @param values value
     * @return result
     */
    Long setAdd(String key, String... values);

    /**
     * 添加元素,有序集合是按照元素的score值由小到大排列
     *
     * @param key   key
     * @param value value
     * @param score 分数（排序，升序,由小到大）
     * @return 是否添加成功
     */
    Boolean setAdd(String key, String value, double score);

    /**
     * 获取集合的大小
     *
     * @param key key
     * @return 集合的大小
     */
    Long setSize(String key);

    /**
     * hash设值
     *
     * @param key  key
     * @param maps map
     */
    void hashPutAll(String key, Map<String, String> maps);

    /**
     * hash设值
     *
     * @param key     key
     * @param hashKey map的key
     * @param value   map的value
     */
    void hashPut(String key, String hashKey, String value);

    /**
     * 查看哈希表 key 中，指定的字段是否存在
     *
     * @param key   key
     * @param field map的key
     * @return 是否存在
     */
    boolean hashExists(String key, String field);
}
