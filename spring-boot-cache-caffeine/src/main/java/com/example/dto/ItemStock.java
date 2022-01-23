package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 测试dto
 * </p>
 *
 * @author MrWen
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemStock {

    private Long id;

    private Integer stock;

    private Integer sold;

}
