package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Goods;
import com.example.mapper.GoodsMapper;
import com.example.service.IGoodsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品基本信息表 服务实现类
 * </p>
 *
 * @author MrWen
 * @since 2021-11-28
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

}
