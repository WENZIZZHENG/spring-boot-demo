package com.example.controller;

import com.example.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * 测试
 * </p>
 *
 * @author MrWen
 **/
@Slf4j
@RestController
@RequestMapping("/user")
@Api(tags = "用户测试")
public class UserController {


    @PostMapping("/save")
    @ApiOperation("对象属性校验")
    public String save(@RequestBody @Valid User user) {
        log.info("save...user={}", user);
        return "save success";
    }
}
