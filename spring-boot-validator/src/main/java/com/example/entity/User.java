package com.example.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Data
@ApiModel("用户")
public class User {
    @ApiModelProperty("用户id")
    @NotNull(message = "用户id不能为空")
    private Integer id;

    @ApiModelProperty("用户名")
    @NotEmpty(message = "用户名不能为空")
    @Length(min = 4, max = 30, message = "用户名只能在4~30位之间")
    private String username;

    @ApiModelProperty("年龄")
    @Min(message = "年龄最小为18岁", value = 18)
    @Max(message = "年龄最大为80岁", value = 80)
    @NotNull(message = "年龄不能为空")
    private int age;


    @ApiModelProperty("手机号")
    @Pattern(regexp = "^1[35678]\\d{9}$", message = "手机号格式不正确")
    @NotBlank(message = "不能为空")
    private String phone;


    @ApiModelProperty("邮箱")
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "请输入正确的邮箱")
    private String email;
}
