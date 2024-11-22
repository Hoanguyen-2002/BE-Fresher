package com.lg.fresher.lgcommerce.mapping.order;

import com.lg.fresher.lgcommerce.entity.order.OrderDetail;
import com.lg.fresher.lgcommerce.model.response.checkout.CheckoutItemResponse;
import com.lg.fresher.lgcommerce.model.response.order.OrderItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Interface Name : OrderDetailMapper
 * @ Description : lg_ecommerce_be OrderDetailMapper
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/21/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/21/2024       63200502      add mapper for order detail
 * 11/21/2024       63200502      add mapper for order detail (manage order)
 * */
@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    /**
     *
     * @ Description : lg_ecommerce_be OrderDetailMapper Member Field toCheckoutItemResponse
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/21/2024           63200502    first creation
     *<pre>
     * @param orderDetail
     * @return  CheckoutItemResponse
     */
    @Mapping(target = "id", source = "orderDetailId")
    @Mapping(target = "title", source = "book.title")
    @Mapping(target = "imageURL", source = "book.thumbnail")
    @Mapping(target = "originalPrice", source = "basePrice")
    @Mapping(target = "salePrice", source = "discountPrice")
    @Mapping(target = "totalPrice", source = "total")
    CheckoutItemResponse toCheckoutItemResponse(OrderDetail orderDetail);

    /**
     *
     * @ Description : lg_ecommerce_be OrderDetailMapper Member Field toOrderItemResponse
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/21/2024           63200502    first creation
     *<pre>
     * @param orderDetail
     * @return  OrderItemResponse
     */
    @Mapping(target = "bookId", source = "book.bookId")
    @Mapping(target = "name", source = "book.title")
    @Mapping(target = "thumbnail", source = "book.thumbnail")
    OrderItemResponse toOrderItemResponse(OrderDetail orderDetail);
}
