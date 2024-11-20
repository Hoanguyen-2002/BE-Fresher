package com.lg.fresher.lgcommerce.mapping.checkout;

import com.lg.fresher.lgcommerce.entity.order.ShippingMethod;
import com.lg.fresher.lgcommerce.model.response.checkout.ShippingMethodResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * -------------------------------------------------------------------------
 * LG CNS Ecommerce
 * ------------------------------------------------------------------------
 *
 * @ Interface Name : ShippingMapper
 * @ Description : lg_ecommerce_be ShippingMapper
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/18/2024
 * ------------------------------------------------------------------------
 * Modification Information
 * ------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/18/2024       63200502      first creation
 */
@Mapper(componentModel = "spring")
public interface ShippingMethodMapper {
    /**
     * @ Description : lg_ecommerce_be ShippingMethodMapper Member Field toShippingMethodResponse
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/18/2024           63200502    first creation
     * <pre>
     * @param shippingMethod
     * @return ShippingMethodResponse
     */
    @Mapping(target = "shippingMethodName", source = "shippingName")
    @Mapping(target = "shippingMethodFee", source = "shippingFee")
    ShippingMethodResponse toShippingMethodResponse(ShippingMethod shippingMethod);
}
