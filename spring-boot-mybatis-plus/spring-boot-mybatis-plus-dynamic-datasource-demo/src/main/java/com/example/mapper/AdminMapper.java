package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Admin;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 管理员表 Mapper 接口
 * </p>
 *
 * @author MrWen
 * @since 2021-12-07
 */
@Repository
public interface AdminMapper extends BaseMapper<Admin> {

}
