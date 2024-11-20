package com.lg.fresher.lgcommerce.service.checkout;

import com.lg.fresher.lgcommerce.entity.order.PaymentMethod;
import com.lg.fresher.lgcommerce.entity.order.ShippingMethod;
import com.lg.fresher.lgcommerce.mapping.checkout.PaymentMethodMapper;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.repository.checkout.PaymentMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : PaymentMethodServiceImpl
 * @ Description : lg_ecommerce_be PaymentMethodServiceImpl
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/19/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/19/2024       63200502      first creation */
@Service
@RequiredArgsConstructor
public class PaymentMethodServiceImpl implements PaymentMethodService{
    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentMethodMapper paymentMethodMapper;

    /**
     *
     * @return
     */
    @Override
    public CommonResponse<Map<String, Object>> getAllPaymentMethod() {
        List<PaymentMethod> shippingMethodList = paymentMethodRepository.findAll();
        Map<String, Object> res = new HashMap<>();
        res.put("content", shippingMethodList.stream().map(paymentMethodMapper::toPaymentMethodResponse));
        return CommonResponse.success(res);
    }
}
