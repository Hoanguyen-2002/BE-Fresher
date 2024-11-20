package com.lg.fresher.lgcommerce.model.response.checkout;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : CheckOutResponse
 * @ Description : lg_ecommerce_be CheckOutResponse
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
public class CheckoutResponse {
    private List<CheckoutItemResponse> itemResponseList;
    private String orderId;
}
