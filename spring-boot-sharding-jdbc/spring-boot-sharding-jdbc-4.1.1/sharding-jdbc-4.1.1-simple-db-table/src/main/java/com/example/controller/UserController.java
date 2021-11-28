package com.example.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.User;
import com.example.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author MrWen
 * @since 2021-11-28
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户")
public class UserController {

    @Autowired
    private IUserService userService;


    @PostMapping("/init")
    @ApiOperation("初始化测试数据")
    public void init() {
        for (int i = 0; i < 12; i++) {
            User user = User
                    .builder()
                    .userName("")
                    .build();
            this.userService.save(user);
            //默认分库规则 ds$->{id % 2}，无分表
            user.setUserName("第" + (user.getId() % 2) + "个数据库_" + i);
            this.userService.updateById(user);
        }
    }

    @GetMapping("/list")
    @ApiOperation("查询列表")
    public Page<User> list(@ApiParam("当前页") @RequestParam(defaultValue = "1") Integer page,
                           @ApiParam("每页大小") @RequestParam(defaultValue = "10") Integer size) {
        return this.userService.page(new Page<>(page, size),
                Wrappers.lambdaQuery(User.class).orderByAsc(User::getId));
    }

    @GetMapping("/detail")
    @ApiOperation("查询详情")
    public User detail(@ApiParam("主键") @RequestParam("id") Long id) {
        return this.userService.getById(id);
    }


    @PostMapping("/edit")
    @ApiOperation("保存或修改")
    public User edit(@RequestBody User user) {
        this.userService.saveOrUpdate(user);
        return user;
    }


    @PostMapping("/delete/{id}")
    @ApiOperation("删除")
    public boolean delete(@PathVariable("id") Long id) {
        return this.userService.removeById(id);
    }
}

