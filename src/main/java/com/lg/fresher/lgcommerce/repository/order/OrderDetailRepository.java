package com.lg.fresher.lgcommerce.repository.order;

import com.lg.fresher.lgcommerce.entity.order.OrderDetail;
import com.lg.fresher.lgcommerce.model.response.checkout.CheckoutItemResponse;
import com.lg.fresher.lgcommerce.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Interface Name : OrderDetailRepository
 * @ Description : lg_ecommerce_be OrderDetailRepository
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/19/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/19/2024       63200502      first creation
 * 11/21/2024       63200502      add method to get order detail item
 * */
@Repository
public interface OrderDetailRepository extends BaseRepository<OrderDetail, String> {
    @Query("""
       SELECT od FROM OrderDetail od
       JOIN od.order o
       WHERE od.orderDetailId = :orderDetailId
         AND o.orderStatus = "COMPLETE"
         AND od.isReviewed = false
   """)
    Optional<OrderDetail> findCompletedAndNotReviewedOrderDetail(@Param("orderDetailId") String orderDetailId);

    @Query("""
            SELECT new com.lg.fresher.lgcommerce.model.response.checkout.CheckoutItemResponse(
                od.orderDetailId, b.title, b.thumbnail, od.quantity, od.basePrice, od.discountPrice, od.finalPrice)
            FROM OrderDetail od
            JOIN od.book b
            WHERE od.order.orderId = :orderId
            """)
    List<CheckoutItemResponse> getAllOrderDetailByOrderId(@Param("orderId") String orderId);
}
