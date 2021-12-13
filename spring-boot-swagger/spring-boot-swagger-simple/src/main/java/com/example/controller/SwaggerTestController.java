package com.example.controller;

import com.example.dto.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * swagger测试
 * </p>
 *
 * @author MrWen
 **/
@RestController
@RequestMapping("/test")
@Api(tags = "swagger测试")
public class SwaggerTestController {


    @ApiOperation(value = "向客人问好")
    @GetMapping("/sayHi")
    public ResponseEntity<String> sayHi(@ApiParam("姓名") @RequestParam(value = "name") String name) {
        return ResponseEntity.ok("Hi:" + name);
    }


    @ApiOperation("新增")
    @PostMapping("/save")
    public User list(@RequestBody User user) {
        return user;
    }
}
