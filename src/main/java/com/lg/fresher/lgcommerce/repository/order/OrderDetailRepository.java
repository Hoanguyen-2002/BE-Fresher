package com.lg.fresher.lgcommerce.repository.order;

import com.lg.fresher.lgcommerce.entity.order.OrderDetail;
import com.lg.fresher.lgcommerce.model.response.checkout.CheckoutItemResponse;
import com.lg.fresher.lgcommerce.model.response.order.OrderDetailChecking;
import com.lg.fresher.lgcommerce.model.response.order.OrderDetailItem;
import com.lg.fresher.lgcommerce.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    /**
     * @ Description : lg_ecommerce_be OrderDetailRepository Member Field getOrderDetailItemByOrderId
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/22/2024           63200502    first creation
     * <pre>
     * @param orderId
     * @return List<OrderDetailItem>
     */
    @Query("""
            SELECT new com.lg.fresher.lgcommerce.model.response.order.OrderDetailItem(
                o.orderId,
                od.orderDetailId,
                o.email,
                b.thumbnail,
                b.title,
                od.quantity,
                od.finalPrice,
                od.total,
                o.totalAmount,
                b.bookId,
                b.quantity,
                o.orderStatus)
            FROM OrderDetail od
            JOIN od.book b
            JOIN od.order o
            WHERE o.orderId = :orderId
            """)
    List<OrderDetailItem> getOrderDetailItemByOrderId(@Param("orderId") String orderId);

    /**
     * @ Description : lg_ecommerce_be OrderDetailRepository Member Field appendMessage
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/23/2024           63200502    first creation
     * <pre>
     * @param orderDetailIds
     * @param message
     */
    @Modifying
    @Query(value = """
            UPDATE OrderDetail od
            SET od.note = :message
            WHERE od.orderDetailId IN (:orderDetailIds)
            """)
    void appendMessage(@Param("orderDetailIds") List<String> orderDetailIds, @Param("message") String message);

    @Modifying
    @Query(value = """
            UPDATE OrderDetail od
            SET od.note = ""
            WHERE od.order.orderId = :orderId
            """)
    void removeErrorMessage(@Param("orderId") String orderId);
}
