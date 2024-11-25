package com.lg.fresher.lgcommerce.model.response.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : GetListOrderResponse
 * @ Description : lg_ecommerce_be GetListOrderResponse
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
public class GetListOrderResponse {
    private String orderId;
    private List<OrderItemResponse> orderDetails;
    private String status;
    private String recipient;
    private String phone;
    private String address;
    private String shippingMethod;
    private double shippingFee;
    private String paymentMethod;
    private double total;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime lastUpdated;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    private String email;
    private String note;
}
