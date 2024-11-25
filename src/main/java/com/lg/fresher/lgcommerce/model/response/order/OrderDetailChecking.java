package com.lg.fresher.lgcommerce.model.response.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailChecking {
    private String orderDetailId;
    private String orderId;
    private int buyQuantity;
    private int stockQuantity;
}
