package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 顺序的步骤
 * </p>
 *
 * @author MrWen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStep {
    private long orderId;
    private String desc;
}
