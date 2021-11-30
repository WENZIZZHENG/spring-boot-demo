package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author MrWen
 * @since 2021-11-30
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("t_user")
//@ApiModel(value = "User对象", description = "用户")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键默认名称：id 非默认名称需要指定id
     * type: 局部策略  IdType.AUTO 数据库ID自增   NONE:该类型为未设置主键类型(将跟随全局)
     * ASSIGN_ID: 雪花id  ASSIGN_UUID：UUID
     */
    @TableId(type = IdType.ASSIGN_ID)
    //@ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 起别名，对应数据库字段name
     */
    @TableField(value = "name")
    //@ApiModelProperty(value = "姓名")
    private String realName;

    //@ApiModelProperty(value = "年龄")
    private Integer age;

    //@ApiModelProperty(value = "邮箱")
    private String email;

    //@ApiModelProperty(value = "直属上级id")
    private Long managerId;

    //@ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 是否为数据库表字段
     * 默认 true 存在，false 不存在
     */
    @TableField(exist = false)
    private String remark;

}
