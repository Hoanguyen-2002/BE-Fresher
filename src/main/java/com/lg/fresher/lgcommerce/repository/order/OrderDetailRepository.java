package com.lg.fresher.lgcommerce.repository.order;

import com.lg.fresher.lgcommerce.entity.order.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {
    @Query("""
       SELECT od FROM OrderDetail od
       JOIN od.order o
       JOIN o.status s
       WHERE od.orderDetailId = :orderDetailId
         AND s.statusName = 'completed'
         AND od.isReviewed = false
   """)
    Optional<OrderDetail> findCompletedAndNotReviewedOrderDetail(@Param("orderDetailId") String orderDetailId);
}
