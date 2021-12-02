package com.example.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.Goods;
import com.example.service.IGoodsService;
import com.example.util.CommonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;

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

    private final static Date DATE_2022 = DateUtil.parseDate("2022-01-01");

    private final static Date DATE_2024 = DateUtil.parseDate("2024-01-01");

    @PostMapping("/init")
    @ApiOperation("初始化测试数据")
    public void init() {
        Date startDate = DateUtil.parseDate("2020-01-01");

        for (int i = 0; i < 24; i++) {
            Goods goods = Goods.builder()
                    .name("商品" + i)
                    .unit("件")
                    .retailPrice(BigDecimal.valueOf(i + 10))
                    .build();
            goods.setCreateTime(startDate);
            this.goodsService.save(goods);

            //分库规格：2020-2021 ds0   2022-2023 ds1  分表规则：t_goods_2020_01_03
            goods.setBrief("第" + (startDate.compareTo(DATE_2022) >= 0 ? "ds1" : "ds0")
                    + "个数据库中，在" + (CommonUtil.date2QuarterStr(startDate)) + "表_" + i);
            //更新时不能带有create_time,用mybatis-plus 自动填充-updateFill 设为空会报错。。。
            goods.setCreateTime(null);
            this.goodsService.updateById(goods);

            //+1个月
            startDate = startDate.compareTo(DATE_2024) >= 0 ?
                    //大于2024年的，初始化回2020
                    DateUtil.parseDate("2020-01-01") :
                    //+1个月
                    DateUtil.offsetMonth(startDate, 1);
        }
    }


    @GetMapping("/list")
    @ApiOperation("查询列表")
    public Page<Goods> list(@ApiParam("当前页") @RequestParam(defaultValue = "1") Integer page,
                            @ApiParam("每页大小") @RequestParam(defaultValue = "10") Integer size,
                            @ApiParam("开始时间 yyyy-MM-dd") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                            @ApiParam("结束时间 yyyy-MM-dd") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return this.goodsService.page(new Page<>(page, size),
                Wrappers.lambdaQuery(Goods.class)
                        .ge(startDate != null, Goods::getCreateTime, startDate)
                        .le(endDate != null, Goods::getCreateTime, endDate)
                        .orderByAsc(Goods::getCreateTime));
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

    @PostMapping("/update")
    @ApiOperation("修改含分片字段属性")
    public Boolean update(@ApiParam("主键") @RequestParam("id") Long id, @ApiParam("修改商品名词") @RequestParam("name") String name) {
        Goods goods = this.goodsService.getById(id);
        //这里全量修改，模拟修改分片字段 create_time 的情况。
        //这里会经历MybatisPluginCustomInterceptor拦截器清空 分片字段 create_time
        //后续mybatis-plus更新时会跳过null值
        goods.setName(name);
        return this.goodsService.updateById(goods);
    }


    @PostMapping("/delete/{id}")
    @ApiOperation("删除")
    public boolean delete(@PathVariable("id") Long id) {
        return this.goodsService.removeById(id);
    }

}

