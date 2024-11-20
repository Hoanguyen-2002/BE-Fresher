package com.lg.fresher.lgcommerce.mapping.checkout;

import com.lg.fresher.lgcommerce.entity.order.PaymentMethod;
import com.lg.fresher.lgcommerce.model.response.checkout.PaymentMethodResponse;
import org.mapstruct.Mapper;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Interface Name : PaymentMethodMapper
 * @ Description : lg_ecommerce_be PaymentMethodMapper
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/19/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/19/2024       63200502      first creation */
@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {
    /**
     *
     * @ Description : lg_ecommerce_be PaymentMethodMapper Member Field toPaymentMethodResponse
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/19/2024           63200502    first creation
     *<pre>
     * @param paymentMethod
     * @return  PaymentMethodResponse
     */
    PaymentMethodResponse toPaymentMethodResponse(PaymentMethod paymentMethod);
}
