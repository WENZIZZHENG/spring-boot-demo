package com.example.entity.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 基础属性,这个根据实际项目，放到公共包中，这里只做演示
 * </p>
 *
 * @author MrWen
 **/
@Data
public class BaseEntity {

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建者")
    private String createName;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改者")
    private String updateName;

    @Version
    @ApiModelProperty(value = "乐观锁版本号")
    private Integer version;

    @TableLogic
    @TableField(select = false)
    @ApiModelProperty(value = "逻辑删除")
    private Boolean deleted;

    @ApiModelProperty(value = "租户id")
    private Integer tenantId;
}
