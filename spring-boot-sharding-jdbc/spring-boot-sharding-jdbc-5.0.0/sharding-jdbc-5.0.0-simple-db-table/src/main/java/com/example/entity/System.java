package com.example.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统配置表格
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
@TableName("t_system")
@ApiModel(value = "System对象", description = "系统配置表格")
public class System implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "系统配置名")
    private String keyName;

    @ApiModelProperty(value = "系统配置值")
    private String keyValue;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建者")
    private String createName;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "修改者")
    private String updateName;

    @TableLogic
    @TableField(select = false)
    @ApiModelProperty(value = "逻辑删除")
    private Boolean deleted;


}
