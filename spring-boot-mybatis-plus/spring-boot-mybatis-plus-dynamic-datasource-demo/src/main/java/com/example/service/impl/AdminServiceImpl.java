package com.example.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Admin;
import com.example.mapper.AdminMapper;
import com.example.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author MrWen
 * @since 2021-12-07
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;

    /**
     * 其实这里，用无注解或自定义注解形式，更加方便
     * 详情参考：https://www.kancloud.cn/tracy5546/dynamic-datasource/2327437
     */
    @DS("slave")
    @Override
    public List<Admin> selectSlaveAdmin() {
        return this.adminMapper.selectList(null);
    }

}
