package com.lg.fresher.lgcommerce.model.request.checkout;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : CheckOutItemRequest
 * @ Description : lg_ecommerce_be CheckOutItemRequest
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/18/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/18/2024       63200502      first creation */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutItemRequest {
    private String bookId;
    @Min(value = 1, message = "Số lượng sản phẩm đặt mua không hợp lệ")
    private int quantity;
    private double originalPrice;
    private double salePrice;

}
