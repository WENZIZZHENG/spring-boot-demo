package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author MrWen
 **/
@Data
@TableName("t_user")
public class User {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("name")
    private String name;

    @TableField("age")
    private Integer age;

    @TableField("gender")
    private Integer gender;

    @TableField("email")
    private String email;

    @TableField("manager_id")
    private Long managerId;
}
