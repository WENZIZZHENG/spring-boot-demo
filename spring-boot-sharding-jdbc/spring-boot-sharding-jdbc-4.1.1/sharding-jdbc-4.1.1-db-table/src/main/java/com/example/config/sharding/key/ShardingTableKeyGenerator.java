package com.example.config.sharding.key;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.example.service.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.spi.keygen.ShardingKeyGenerator;

import java.util.Properties;


/**
 * <p>
 * sharding-jdbc自定义主键,这里使用redis进行自增操作
 * </p>
 *
 * @author MrWen
 **/
@Slf4j
public class ShardingTableKeyGenerator implements ShardingKeyGenerator {

    private IRedisService redisService = null;

    /**
     * 自定义属性，看配置文件
     */
    private Properties properties = new Properties();

    private String redisKey;

    /**
     * 自定义主键生成器
     *
     * @return 自定义主键
     */
    @Override
    public Comparable<?> generateKey() {
        if (redisService == null) {
            redisService = SpringUtil.getBean(IRedisService.class);
        }
        if (StrUtil.isBlank(redisKey)) {
            this.redisKey = properties.getProperty("redis-prfix");
        }
        //手动注入redisService
        Long increment = redisService.increment(redisKey, 1L);
        log.info("redisKey={}，自增主键为{}", redisKey, increment);
        return increment;
    }

    /**
     * 声明类型
     */
    @Override
    public String getType() {
        return "CUSTOM";
    }

    @Override
    public Properties getProperties() {
        return properties;
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

}
