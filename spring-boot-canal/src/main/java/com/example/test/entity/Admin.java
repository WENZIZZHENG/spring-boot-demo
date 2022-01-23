package com.example.test.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

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
//@CanalTable("t_admin")//优先这个
@TableName("t_admin")//CanalTable不存在，才这个
public class Admin {

    private Long id;

    private String userName;

    private String avatar;

    private LocalDateTime createTime;

    private String createName;

    private Date updateTime;

    private String updateName;

    private Integer version;

    private Boolean deleted;

    private Integer tenantId;
}
