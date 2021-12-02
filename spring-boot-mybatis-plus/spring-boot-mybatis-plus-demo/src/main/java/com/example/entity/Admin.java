package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.config.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 管理员表
 * </p>
 *
 * @author MrWen
 * @since 2021-12-02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("t_admin")
@ApiModel(value = "Admin对象", description = "管理员表")
public class Admin extends BaseEntity {

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("管理员名称")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty("头像图片")
    @TableField("avatar")
    private String avatar;


}
