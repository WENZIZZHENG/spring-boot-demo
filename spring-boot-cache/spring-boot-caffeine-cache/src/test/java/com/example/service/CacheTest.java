package com.example.service;

import com.example.BaseTest;
import com.example.entity.Admin;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

/**
 * <p>
 * 缓存测试
 * </p>
 *
 * @author MrWen
 **/
public class CacheTest extends BaseTest {

    @Autowired
    private ICacheService cacheService;


    @Test
    public void test1() throws InterruptedException {
        Admin admin = Admin.builder()
                .id(2L)
                .userName("柳岩")
                .lastLoginTime(LocalDateTime.now())
                .build();
        this.cacheService.save(admin);
        System.out.println("this.cacheService.getById(2L) = " + this.cacheService.getById(2L));


        admin.setUserName("柳岩233");
        this.cacheService.update(admin);
        System.out.println("this.cacheService.getById(2L) = " + this.cacheService.getById(2L));

        //删除再查询，缓存会有null的序列号值。
        this.cacheService.deleteById(2L);
        System.out.println("this.cacheService.getById(2L) = " + this.cacheService.getById(2L));

        Thread.sleep(30000);
    }
}
