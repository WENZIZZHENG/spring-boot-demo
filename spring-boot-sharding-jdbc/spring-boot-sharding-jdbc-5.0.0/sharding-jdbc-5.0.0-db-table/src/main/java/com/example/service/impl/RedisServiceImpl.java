package com.example.service.impl;

import com.example.service.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * redis相关的
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
    public Long increment(String key, long delta) {
        return stringRedisTemplate.opsForValue().increment(key, delta);
    }
}
