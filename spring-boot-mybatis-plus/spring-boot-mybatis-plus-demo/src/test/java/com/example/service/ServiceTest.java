package com.example.service;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.BaseTest;
import com.example.entity.Admin;
import com.example.entity.User;
import com.example.enums.GenderEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * service测试类
 * </p>
 *
 * @author MrWen
 **/
public class ServiceTest extends BaseTest {

    @Autowired
    private IUserService userService;
    @Autowired
    private IAdminService adminService;


    /**
     * 分页查询,其它具体查询，参考 spring-boot-mybatis-plus-simple下的 com.example.test.MybatisTest
     * 或github这个的地址
     * https://github.com/WENZIZZHENG/spring-boot-demo/blob/master/spring-boot-mybatis-plus/spring-boot-mybatis-plus-simple/src/test/java/com/example/test/MybatisTest.java
     */
    @Test
    public void findPage() {
        //逻辑删除查询
        //SELECT id, name, age, gender, email, manager_id, create_time, create_name, update_time, update_name, version, tenant_id FROM t_user WHERE deleted = 0 LIMIT ?
        Page<User> userPage = userService.page(new Page<>(1, 10));

        //带逻辑删除和租户id查询
        //SELECT id, user_name, avatar, create_time, create_name, update_time, update_name, version, tenant_id FROM t_admin WHERE deleted = 0 AND tenant_id = 1 LIMIT ?
        Page<Admin> adminPage = adminService.page(new Page<>(1, 10));

        System.out.println("userPage = " + JSONUtil.toJsonStr(userPage));
        System.out.println("adminPage = " + JSONUtil.toJsonStr(adminPage));
    }

    /**
     * 新增
     */
    @Test
    public void insert() {
        //这里的id是使用了 com.example.config.CustomIdGenerator 自定义自增id，可以根据实际业务自定义
        User user = User.builder()
                .name("黑白无常")
                .age(19)
                .gender(GenderEnum.UNKNOWN)
                .email("xxxxx.xx.xx")
                .build();
        userService.save(user);
        System.out.println(userService.getById(user.getId()));


        //这里的id是数据库自增
        Admin admin = Admin.builder()
                .userName("新增管理员")
                .avatar("222")
                .build();
        adminService.save(admin);
        System.out.println(adminService.getById(admin.getId()));
    }

    /**
     * 修改
     */
    @Test
    public void update() {
        //第一次修改
        User user = this.userService.getById(1);
        user.setAge(18);
        //修改成功
        System.out.println(userService.updateById(user));

        //第二次修改
        //模拟并发情况，修改乐观锁id
        user.setVersion(0);
        user.setAge(17);
        //修改失败
        System.out.println(userService.updateById(user));
    }
}
