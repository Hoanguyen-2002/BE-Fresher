package com.lg.fresher.lgcommerce.repository.order;

import com.lg.fresher.lgcommerce.constant.OrderStatus;
import com.lg.fresher.lgcommerce.entity.order.Order;
import com.lg.fresher.lgcommerce.repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * -------------------------------------------------------------------------
 * LG CNS Ecommerce
 * ------------------------------------------------------------------------
 *
 * @ Interface Name : OrderRepository
 * @ Description : lg_ecommerce_be OrderRepository
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/19/2024
 * ------------------------------------------------------------------------
 * Modification Information
 * ------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/19/2024       63200502      first creation
 */
@Repository
public interface OrderRepository extends BaseRepository<Order, String>, JpaSpecificationExecutor<Order> {
    Optional<Order> findByOrderId(String orderId);
    /**
     * @ Description : lg_ecommerce_be OrderRepository Member Field appendMessage
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/23/2024           63200502    first creation
     * <pre>
     * @param orderId
     * @param message
     */
    @Modifying
    @Query(value = """
            UPDATE Order o
            SET o.note = :message, o.updatedAt = CURRENT_TIMESTAMP
            WHERE o.orderId = :orderId
            """)
    void appendMessage(@Param("orderId") String orderId, @Param("message") String message);

    /**
     * @ Description : lg_ecommerce_be OrderRepository Member Field updateOrderStatus
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/25/2024           63200502    first creation
     * <pre>
     * @param orderId
     * @param orderStatus
     */
    @Modifying
    @Query("""
            UPDATE Order o
            SET o.orderStatus = :orderStatus, o.updatedAt = CURRENT_TIMESTAMP
            WHERE o.orderId = :orderId
            """)
    void updateOrderStatus(@Param("orderId") String orderId,
                           @Param("orderStatus") OrderStatus orderStatus);
}
