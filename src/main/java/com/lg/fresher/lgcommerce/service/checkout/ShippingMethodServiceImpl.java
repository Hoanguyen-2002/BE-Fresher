package com.lg.fresher.lgcommerce.service.checkout;

import com.lg.fresher.lgcommerce.entity.order.ShippingMethod;
import com.lg.fresher.lgcommerce.mapping.checkout.ShippingMethodMapper;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.repository.checkout.ShippingMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * -------------------------------------------------------------------------
 * LG CNS Ecommerce
 * ------------------------------------------------------------------------
 *
 * @ Class Name : ShippingMethodServiceImpl
 * @ Description : lg_ecommerce_be ShippingMethodServiceImpl
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/18/2024
 * ------------------------------------------------------------------------
 * Modification Information
 * ------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/18/2024       63200502      first creation
 */
@Service
@RequiredArgsConstructor
public class ShippingMethodServiceImpl implements ShippingMethodService {
    private final ShippingMethodRepository shippingMethodRepository;
    private final ShippingMethodMapper shippingMethodMapper;

    /**
     * @return
     */
    @Override
    public CommonResponse<Map<String, Object>> getAllShippingMethod() {
        List<ShippingMethod> shippingMethodList = shippingMethodRepository.findAll();
        Map<String, Object> res = new HashMap<>();
        res.put("content", shippingMethodList.stream().map(shippingMethodMapper::toShippingMethodResponse));
        return CommonResponse.success(res);
    }
}
