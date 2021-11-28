package com.example.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.System;
import com.example.mapper.SystemMapper;
import com.example.service.ISystemService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统配置表格 服务实现类
 * </p>
 *
 * @author MrWen
 * @since 2021-11-28
 */
@Service
public class SystemServiceImpl extends ServiceImpl<SystemMapper, System> implements ISystemService {

}
