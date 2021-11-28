package com.example.config.sharding.key;

import cn.hutool.extra.spring.SpringUtil;
import com.example.service.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.spi.KeyGenerateAlgorithm;


/**
 * <p>
 * sharding-jdbc自定义主键,这里使用redis进行自增操作
 * </p>
 *
 * @author MrWen
 **/
@Slf4j
public class ShardingTableKeyGenerator implements KeyGenerateAlgorithm {

    private IRedisService redisService;

    private String redisKey;


    /**
     * mall_admin表的主键生成器
     *
     * @return 自定义主键
     */
    @Override
    public Comparable<?> generateKey() {
        //手动注入redisService,在init初始化时，Bean并没有加载
        if (redisService == null) {
            redisService = SpringUtil.getBean(IRedisService.class);
        }

        Long increment = redisService.increment(redisKey, 1L);
        log.info("自增主键为{}", increment);
        return increment;
    }

    @Override
    public void init() {
        redisKey = getProps().getProperty("redis-prfix");
        log.info("==================初始化ShardingTableKeyGenerator成功==================");
    }


    /**
     * 声明类型
     */
    @Override
    public String getType() {
        return "CUSTOM";
    }
}
