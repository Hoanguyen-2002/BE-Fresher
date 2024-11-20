package com.lg.fresher.lgcommerce.service.checkout;

import com.lg.fresher.lgcommerce.model.response.CommonResponse;

import java.util.Map;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Interface Name : PaymentMethodService
 * @ Description : lg_ecommerce_be PaymentMethodService
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/19/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/19/2024       63200502      first creation */
public interface PaymentMethodService {
    CommonResponse<Map<String, Object>> getAllPaymentMethod();
}
