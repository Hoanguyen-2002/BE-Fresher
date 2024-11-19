package com.lg.fresher.lgcommerce.service.checkout;

import com.lg.fresher.lgcommerce.model.response.CommonResponse;

import java.util.Map;

public interface ShippingMethodService {
    CommonResponse<Map<String, Object>> getAllShippingMethod();
}
