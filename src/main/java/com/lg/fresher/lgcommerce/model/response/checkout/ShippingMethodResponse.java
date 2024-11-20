package com.lg.fresher.lgcommerce.model.response.checkout;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * -------------------------------------------------------------------------
 * LG CNS Ecommerce
 * ------------------------------------------------------------------------
 *
 * @ Class Name : ShippingMethodResponse
 * @ Description : lg_ecommerce_be ShippingMethodResponse
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/18/2024
 * ------------------------------------------------------------------------
 * Modification Information
 * ------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/18/2024       63200502      first creation
 */
@Data
@NoArgsConstructor
public class ShippingMethodResponse {
    private String shippingMethodId;
    private String shippingMethodName;
    private Double shippingMethodFee;
}
