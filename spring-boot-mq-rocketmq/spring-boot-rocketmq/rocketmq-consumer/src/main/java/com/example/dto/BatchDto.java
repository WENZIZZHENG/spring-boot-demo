package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 批量消息实体
 * </p>
 *
 * @author MrWen
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BatchDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String message;
}
