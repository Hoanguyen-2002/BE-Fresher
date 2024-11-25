package com.lg.fresher.lgcommerce.mapping.order;

import com.lg.fresher.lgcommerce.entity.order.Order;
import com.lg.fresher.lgcommerce.model.response.order.GetListOrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Interface Name : OrderMapper
 * @ Description : lg_ecommerce_be OrderMapper
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/21/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/21/2024       63200502      first creation */
@Mapper(componentModel = "spring", uses = {OrderDetailMapper.class})
public interface OrderMapper {
    @Mapping(target = "status", source = "orderStatus")
    @Mapping(target = "shippingMethod", source = "shippingMethod.shippingName")
    @Mapping(target = "shippingFee", source = "shippingMethod.shippingFee")
    @Mapping(target = "paymentMethod", source = "paymentMethod.paymentName")
    @Mapping(target = "address", source = "detailAddress")
    @Mapping(target = "lastUpdated", source = "updatedAt")
    @Mapping(target = "total", source = "totalAmount")
    GetListOrderResponse toGetListOrderResponse(Order order);
}
