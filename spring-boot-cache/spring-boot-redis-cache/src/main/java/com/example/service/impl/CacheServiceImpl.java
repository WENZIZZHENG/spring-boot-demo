package com.example.service.impl;

import com.example.entity.Admin;
import com.example.service.ICacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 缓存测试
 * </p>
 *
 * @author MrWen
 **/
@Slf4j
@Service
public class CacheServiceImpl implements ICacheService {


    @Override
    @Cacheable(value = "admin", key = "#id")//如果在缓存中找不到计算出的键值，则会调用目标方法，并将返回的值存储在关联的缓存中。
    public Admin getById(Long id) {
//        Admin admin = Admin.builder()
//                .id(id)
//                .userName("随机用户" + new Random().nextInt(100))
//                .lastLoginTime(LocalDateTime.now())
//                .build();

//        log.info("detail-->id={},admin={}", id, admin);
//        return admin;
        return null;
    }

    @Override
    @CachePut(value = "admin", key = "#result.id")//新增或修改一条记录
    public Admin save(Admin admin) {
        log.info("save-->id={},admin={}", admin.getId(), admin);
        return admin;
    }

    @Override
    @CachePut(value = "admin", key = "#admin.id")//新增或修改一条记录
    public Admin update(Admin admin) {
        log.info("update-->id={},admin={}", admin.getId(), admin);
        return admin;
    }


    @Override
    @CacheEvict(value = "admin", key = "#id") //清除一条缓存，key为要清空的数据
//    @CacheEvict(value = "admin", allEntries = true) //方法调用后清空所有缓存
//    @CacheEvict(value = "admin", beforeInvocation = true, allEntries = true) //方法调用前清空所有缓存
    public void deleteById(Long id) {
        log.info("deleteById-->id={}", id);
    }
}
