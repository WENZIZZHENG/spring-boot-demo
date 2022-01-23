package com.example.test.service;

import com.example.canal.handler.CanalEntryHandler;
import com.example.test.entity.Admin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 模式canal同步mysql测试
 * </p>
 *
 * @author MrWen
 * @since 2021-12-02
 */
@Slf4j
@Service
public class AdminService implements CanalEntryHandler<Admin> {

    @Override
    public void insert(Admin admin) {
        log.info("AdminService.insert---->admin={}", admin);
    }

    @Override
    public void update(Admin before, Admin after) {
        log.info("AdminService.update---->before={}  ,after={}", before, after);
    }

    @Override
    public void delete(Admin admin) {
        log.info("AdminService.delete---->admin={}", admin);
    }
}
