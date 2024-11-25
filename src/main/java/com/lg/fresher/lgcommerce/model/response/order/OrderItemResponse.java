package com.lg.fresher.lgcommerce.model.response.order;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : OrderItemResponse
 * @ Description : lg_ecommerce_be OrderItemResponse
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/21/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/21/2024       63200502      first creation */
@Data
@NoArgsConstructor
public class OrderItemResponse {
    private String orderDetailId;
    private String bookId;
    private String name;
    private String thumbnail;
    private double basePrice;
    private double discountPrice;
    private double finalPrice;
    private double totalPrice;
    private int quantity;
    private Boolean isReviewed;
    private String note;
}
