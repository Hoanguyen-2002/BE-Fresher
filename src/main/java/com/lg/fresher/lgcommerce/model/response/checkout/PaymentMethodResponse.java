package com.lg.fresher.lgcommerce.model.response.checkout;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : PaymentMethodResponse
 * @ Description : lg_ecommerce_be PaymentMethodResponse
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/19/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/19/2024       63200502      first creation */
@Data
@NoArgsConstructor
public class PaymentMethodResponse {
    private String paymentMethodId;
    private String paymentName;
}
