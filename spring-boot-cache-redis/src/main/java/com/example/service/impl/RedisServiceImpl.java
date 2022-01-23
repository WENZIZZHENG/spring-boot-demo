package com.example.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.constant.Constants;
import com.example.service.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * <p>
 * redis常用方法
 * </p>
 *
 * @author MrWen
 **/
@Slf4j
@Service
public class RedisServiceImpl implements IRedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, String value, Long seconds) {
        stringRedisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
    }

    @Override
    public boolean setnx(String key, String value) {
        return Boolean.TRUE.equals(stringRedisTemplate.opsForValue().setIfAbsent(key, value));
    }

    @Override
    public boolean setnx(String key, String value, Long seconds) {
        return Boolean.TRUE.equals(stringRedisTemplate.opsForValue().setIfAbsent(key, value, seconds, TimeUnit.SECONDS));
    }

    @Override
    public void setnx(String key, String value, long timeout, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    @Override
    public boolean setLock(String key, Long seconds) {
        return this.setnx(key, "1", seconds);
    }


    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public Long getLong(String key) {
        long value = 0L;
        String str = get(key);
        if (StrUtil.isNotBlank(str)) {
            value = Long.parseLong(str);
        }
        return value;
    }

    @Override
    public boolean exists(String key) {
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(key));
    }


    @Override
    public List<String> get(List<String> keys) {
        return stringRedisTemplate.opsForValue().multiGet(keys);
    }

    @Override
    public boolean expire(String key, long expire) {
        return Boolean.TRUE.equals(stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS));
    }

    @Override
    public Long getExpire(String key) {
        return stringRedisTemplate.getExpire(key);
    }

    @Override
    public void remove(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public void removeByRegexKey(String key) {
        Set<String> keys = stringRedisTemplate.keys(key);
        if (keys != null) {
            stringRedisTemplate.delete(keys);
        }
    }

    @Override
    public Long increment(String key, long delta) {
        return stringRedisTemplate.opsForValue().increment(key, delta);
    }

    @Override
    public Long increment(String key, long delta, long expire) {
        Long inrCount;
        if (getExpire(key) == -2) {
            //不存在，新增
            inrCount = stringRedisTemplate.opsForValue().increment(key, delta);
            expire(key, expire);
        } else {
            inrCount = stringRedisTemplate.opsForValue().increment(key, delta);
        }
        return inrCount;
    }

    @Override
    public Long decrement(String key, long delta) {
        return stringRedisTemplate.opsForValue().decrement(key, delta);
    }

    @Override
    public Long decrement(String key, long delta, long expire) {
        Long inrCount;
        if (getExpire(key) == -2) {
            delta = 0L;
            inrCount = stringRedisTemplate.opsForValue().decrement(key, delta);
            expire(key, expire);
        } else {
            Long origin = getLong(key);
            if (delta > origin) {
                delta = origin;
            }
            inrCount = stringRedisTemplate.opsForValue().decrement(key, delta);
        }
        return inrCount;
    }

    @Override
    public <T> List<T> getList(final String key, final Class<T> clazz, final Supplier<List<T>> supplier) {
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(key))) {
            String value = stringRedisTemplate.opsForValue().get(key);
            if (StrUtil.isNotEmpty(value)) {
                return JSON.parseArray(value, clazz);
            }
            return Collections.emptyList();
        } else {
            List<T> result = supplier.get();
            if (!Objects.isNull(result)) {
                stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(result));
                return result;
            }
            return Collections.emptyList();
        }
    }

    @Override
    public <T> List<T> getList(final String key, final Class<T> clazz, final Supplier<List<T>> supplier, long timeout) {
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(key))) {
            String value = stringRedisTemplate.opsForValue().get(key);
            if (StrUtil.isNotEmpty(value)) {
                return JSONObject.parseArray(value, clazz);
            } else {
                return getResult(key, supplier, timeout);
            }
        } else {
            return getResult(key, supplier, timeout);
        }
    }

    @Override
    public <T> List<T> getList(final String key, final Class<T> clazz, final Supplier<List<T>> supplier, long timeout, int number) {
        String value = stringRedisTemplate.opsForValue().get(key);
        if (StrUtil.isEmpty(value)) {
            String uuid = UUID.randomUUID().toString();
            String lockKey = key.concat(Constants.LOCK_SUFFIX);
            List<T> result = null;
            try {
                if (setnx(lockKey, uuid, (long) number + 1)) {
                    result = getResult(key, supplier, timeout);
                } else {
                    if (number > 0) {
                        Thread.sleep(1000);
                        number--;
                        result = getList(key, clazz, supplier, timeout, number);
                    }

                }
            } catch (Exception e) {
                log.error("get cache error:", e);
            } finally {
                // 删除锁自己的锁
                String oldLock = stringRedisTemplate.opsForValue().get(lockKey);
                if (StrUtil.isNotEmpty(oldLock) && uuid.equals(stringRedisTemplate.opsForValue().get(lockKey))) {
                    stringRedisTemplate.delete(lockKey);
                }
            }
            return result;
        } else {
            return JSONObject.parseArray(value, clazz);
        }
    }


    @Override
    public <T> T getObject(String key, Class<T> clazz, Supplier<T> supplier) {
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(key))) {
            String value = stringRedisTemplate.opsForValue().get(key);
            if (StrUtil.isNotEmpty(value)) {
                return JSONObject.parseObject(value, clazz);
            } else {
                return getObjectResult(key, supplier);
            }
        } else {
            return getObjectResult(key, supplier);
        }
    }


    private <T> List<T> getResult(String key, Supplier<List<T>> supplier, long timeout) {
        List<T> result = supplier.get();
        if (!Objects.isNull(result)) {
            stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(result), timeout, TimeUnit.SECONDS);
            return result;
        }
        return Collections.emptyList();
    }


    @Override
    public <T> T getObject(final String key, final Class<T> clazz, final Supplier<T> supplier, long timeout) {
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(key))) {
            String value = stringRedisTemplate.opsForValue().get(key);
            if (StrUtil.isNotEmpty(value)) {
                return JSONObject.parseObject(value, clazz);
            } else {
                return getObjectResult(key, supplier, timeout);
            }
        } else {
            String keyLock = key + ":lock";
            if (Boolean.TRUE.equals(stringRedisTemplate.opsForValue().setIfAbsent(keyLock, "1", 60L, TimeUnit.SECONDS))) {
                T objectResult = getObjectResult(key, supplier, timeout);
                stringRedisTemplate.delete(keyLock);
                return objectResult;
            }
            return null;
        }
    }

    @Override
    public <T> T getObject(final String key, final Class<T> clazz, final Supplier<T> supplier, long timeout, int number) {
        String value = stringRedisTemplate.opsForValue().get(key);
        if (StrUtil.isEmpty(value)) {
            String uuid = UUID.randomUUID().toString();
            String lockKey = key.concat(Constants.LOCK_SUFFIX);
            T result = null;
            try {
                if (setnx(lockKey, uuid, (long) number + 1)) {
                    result = getObjectResult(key, supplier, timeout);
                } else {
                    if (number > 0) {
                        Thread.sleep(1000);
                        number--;
                        result = getObject(key, clazz, supplier, timeout, number);
                    }
                }
            } catch (Exception e) {
                log.error("get cache error:", e);
            } finally {
                // 删除锁自己的锁
                String oldLock = stringRedisTemplate.opsForValue().get(lockKey);
                if (StrUtil.isNotEmpty(oldLock) && uuid.equals(stringRedisTemplate.opsForValue().get(lockKey))) {
                    stringRedisTemplate.delete(lockKey);
                }
            }
            return result;
        } else {
            return JSONObject.parseObject(value, clazz);
        }
    }

    private <T> T getObjectResult(String key, Supplier<T> supplier, long timeout) {
        T result = supplier.get();
        if (!Objects.isNull(result)) {
            stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(result), timeout, TimeUnit.SECONDS);
            return result;
        }
        return result;
    }

    private <T> T getObjectResult(String key, Supplier<T> supplier) {
        T result = supplier.get();
        if (!Objects.isNull(result)) {
            stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(result));
            return result;
        }
        return result;
    }

    @Override
    public Long setAdd(String key, String... values) {
        return stringRedisTemplate.opsForSet().add(key, values);
    }

    @Override
    public Boolean setAdd(String key, String value, double score) {
        return stringRedisTemplate.opsForZSet().add(key, value, score);
    }

    @Override
    public Long setSize(String key) {
        return stringRedisTemplate.opsForSet().size(key);
    }

    @Override
    public void hashPutAll(String key, Map<String, String> maps) {
        stringRedisTemplate.opsForHash().putAll(key, maps);
    }

    @Override
    public void hashPut(String key, String hashKey, String value) {
        stringRedisTemplate.opsForHash().put(key, hashKey, value);
    }

    @Override
    public boolean hashExists(String key, String field) {
        return stringRedisTemplate.opsForHash().hasKey(key, field);
    }


}
