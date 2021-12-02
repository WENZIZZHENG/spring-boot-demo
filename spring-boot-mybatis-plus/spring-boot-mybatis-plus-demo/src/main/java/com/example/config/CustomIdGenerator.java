package com.example.config;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicLong;


/**
 * <p>
 * 自定义ID生成器, 仅作为示范(实际项目中 ， 可以用雪花算法,或参考默认的DefaultIdentifierGenerator,继承重写它)
 * </p>
 *
 * @author MrWen
 **/
@Slf4j
public class CustomIdGenerator implements IdentifierGenerator {


    private final AtomicLong al = new AtomicLong(100);

    @Override
    public Long nextId(Object entity) {
        //可以将当前传入的class全类名来作为bizKey,或者提取参数来生成bizKey进行分布式Id调用生成.
        String bizKey = entity.getClass().getName();
        log.info("bizKey:{}", bizKey);
        final long id = al.getAndAdd(1);
        log.info("为{}生成主键值->:{}", bizKey, id);
        return id;
    }
}
