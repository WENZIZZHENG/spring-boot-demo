package com.example.service;


import com.example.entity.Admin;

/**
 * <p>
 * 缓存测试
 * </p>
 *
 * @author MrWen
 **/
public interface ICacheService {

    /**
     * 详情
     *
     * @param id 主键
     * @return Admin对象
     */
    Admin getById(Long id);


    /**
     * 保存
     *
     * @param admin admin
     * @return admin
     */
    Admin save(Admin admin);

    /**
     * 保存
     *
     * @param admin admin
     * @return admin
     */
    Admin update(Admin admin);

    /**
     * 删除
     *
     * @param id 主键
     */
    void deleteById(Long id);
}
