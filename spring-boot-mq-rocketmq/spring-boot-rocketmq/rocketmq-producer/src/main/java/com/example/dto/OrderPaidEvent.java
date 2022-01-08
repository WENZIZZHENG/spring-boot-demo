package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 订单支付事件
 * </p>
 *
 * @author MrWen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPaidEvent implements Serializable {

    private static final long serialVersionUID = 1L;


    private String orderId;

    private BigDecimal paidMoney;

    private String msg;
}
