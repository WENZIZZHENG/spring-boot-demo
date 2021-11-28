package com.example.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.System;
import com.example.service.ISystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 系统配置表格 前端控制器
 * </p>
 *
 * @author MrWen
 * @since 2021-11-28
 */
@RestController
@RequestMapping("/system")
@Api(tags = "系统配置")
public class SystemController {

    @Autowired
    private ISystemService systemService;


    @GetMapping("/list")
    @ApiOperation("查询列表")
    public Page<System> list(@ApiParam("当前页") @RequestParam(defaultValue = "1") Integer page,
                             @ApiParam("每页大小") @RequestParam(defaultValue = "10") Integer size) {
        return this.systemService.page(new Page<>(page, size));
    }

    @GetMapping("/detail")
    @ApiOperation("查询详情")
    public System detail(@ApiParam("主键") @RequestParam("id") Long id) {
        return this.systemService.getById(id);
    }


    @PostMapping("/edit")
    @ApiOperation("保存或修改")
    public System edit(@RequestBody System system) {
        this.systemService.saveOrUpdate(system);
        return system;
    }


    @PostMapping("/delete/{id}")
    @ApiOperation("删除")
    public boolean delete(@PathVariable("id") Long id) {
        return this.systemService.removeById(id);
    }
}

