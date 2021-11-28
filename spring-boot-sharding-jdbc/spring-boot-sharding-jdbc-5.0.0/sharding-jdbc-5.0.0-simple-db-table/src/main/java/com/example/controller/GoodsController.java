package com.example.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.Goods;
import com.example.service.IGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * <p>
 * 商品基本信息表 前端控制器
 * </p>
 *
 * @author MrWen
 * @since 2021-11-28
 */
@RestController
@RequestMapping("/goods")
@Api(tags = "商品基本信息")
public class GoodsController {

    @Autowired
    private IGoodsService goodsService;


    @PostMapping("/init")
    @ApiOperation("初始化测试数据")
    public void init() {
        for (int i = 0; i < 12; i++) {
            Goods goods = Goods.builder()
                    .name("商品" + i)
                    .unit("件")
                    .retailPrice(BigDecimal.valueOf(i + 10))
                    .build();
            this.goodsService.save(goods);

            //分库规格：ds$->{id % 2}   分表规则：t_goods_$->{id % 3}
            goods.setBrief("第" + (goods.getId() % 2) + "个数据库中，第" + (goods.getId() % 3) + "个表_" + i);
            this.goodsService.updateById(goods);
        }
    }


    @GetMapping("/list")
    @ApiOperation("查询列表")
    public Page<Goods> list(@ApiParam("当前页") @RequestParam(defaultValue = "1") Integer page,
                            @ApiParam("每页大小") @RequestParam(defaultValue = "10") Integer size) {
        return this.goodsService.page(new Page<>(page, size),
                Wrappers.lambdaQuery(Goods.class).orderByAsc(Goods::getId)
        );
    }

    @GetMapping("/detail")
    @ApiOperation("查询详情")
    public Goods detail(@ApiParam("主键") @RequestParam("id") Long id) {
        return this.goodsService.getById(id);
    }


    @PostMapping("/edit")
    @ApiOperation("保存或修改")
    public Goods edit(@RequestBody Goods goods) {
        this.goodsService.saveOrUpdate(goods);
        return this.goodsService.getById(goods.getId());
    }


    @PostMapping("/delete/{id}")
    @ApiOperation("删除")
    public boolean delete(@PathVariable("id") Long id) {
        return this.goodsService.removeById(id);
    }

}

