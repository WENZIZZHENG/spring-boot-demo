package com.example.test;

import com.example.BaseTest;
import com.example.service.IRedisService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * redis测试
 * </p>
 *
 * @author MrWen
 **/
public class RedisTest extends BaseTest {

    @Autowired
    private IRedisService redisService;

    @Test
    public void test() {

    }

}
