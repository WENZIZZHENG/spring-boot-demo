package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.User;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author MrWen
 * @since 2021-12-02
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

}
