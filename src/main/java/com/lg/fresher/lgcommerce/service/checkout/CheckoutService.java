package com.lg.fresher.lgcommerce.service.checkout;

import com.lg.fresher.lgcommerce.model.request.checkout.CheckoutRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;

import java.util.Map;

public interface CheckoutService {
    CommonResponse<Map<String, Object>> captureOrder(CheckoutRequest checkoutRequest);
}
