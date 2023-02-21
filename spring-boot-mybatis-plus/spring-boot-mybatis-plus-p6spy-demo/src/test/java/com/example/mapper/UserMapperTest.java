package com.example.mapper;

import com.example.BaseTest;
import com.example.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 测试
 * </p>
 *
 * @author MrWen
 **/
public class UserMapperTest extends BaseTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void selectAll() {
        /*
         控制台会输出：
         Consume Time：7 ms 2023-02-22 00:06:47
         Execute SQL：SELECT id,name,age,gender,email,manager_id FROM t_user WHERE id=1
         */
        User user = userMapper.selectById(1L);
        System.out.println(user);
    }
}
