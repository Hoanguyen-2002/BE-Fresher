package com.lg.fresher.lgcommerce.model.response.order;

import com.lg.fresher.lgcommerce.constant.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * -------------------------------------------------------------------------
 * LG CNS Ecommerce
 * ------------------------------------------------------------------------
 *
 * @ Class Name : OrderDetailItem
 * @ Description : lg_ecommerce_be OrderDetailItem
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/22/2024
 * ------------------------------------------------------------------------
 * Modification Information
 * ------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/22/2024       63200502      first creation
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailItem {
    private String orderId;
    private String orderDetailId;
    private String orderEmail;
    private String imageURL;
    private String title;
    private int quantity;
    private double finalPrice;
    private Double total;
    private Double totalAmount;
    private String bookId;
    private int stockQuantity;
    private OrderStatus orderStatus;
}
