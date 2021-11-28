package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 商品基本信息表
 * </p>
 *
 * @author MrWen
 * @since 2021-11-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("t_goods")
@ApiModel(value = "Goods对象", description = "商品基本信息表")
public class Goods extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 如果需要使用 sharding-jdbc的雪花id，IdType只能是AUTO，设置INPUT会报错(缺失时报错)
     * 当然也可以使用mybatis-plus的雪花算法 IdType.ASSIGN_ID,且强烈推荐，理由如下
     * 1:使用sharding-jdbc的雪花id,IdType.AUT时，只有当插入对象ID不为空，仍让会自动填充。
     * 2:mybatis-plus的雪花算法，IdType.ASSIGN_ID，只有当插入对象ID 为空，才自动填充。
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "商品单位，例如件、盒")
    private String unit;

    @ApiModelProperty(value = "商品简介")
    private String brief;

    @ApiModelProperty(value = "零售价格")
    private BigDecimal retailPrice;


}
