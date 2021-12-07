package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Admin;

import java.util.List;

/**
 * <p>
 * 管理员表 服务类
 * </p>
 *
 * @author MrWen
 * @since 2021-12-07
 */
public interface IAdminService extends IService<Admin> {

    /**
     * 获取所有从库的Admin
     *
     * @return 从库的Admin
     */
    List<Admin> selectSlaveAdmin();
}
