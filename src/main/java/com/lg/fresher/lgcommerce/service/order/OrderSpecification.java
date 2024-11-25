package com.lg.fresher.lgcommerce.service.order;

import com.lg.fresher.lgcommerce.constant.OrderStatus;
import com.lg.fresher.lgcommerce.entity.order.Order;
import org.springframework.data.jpa.domain.Specification;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : OrderSpecification
 * @ Description : lg_ecommerce_be OrderSpecification
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/21/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/21/2024       63200502      first creation */
public class OrderSpecification {
    public static Specification<Order> searchByKeyword(String searchKeyword, String searchBy) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null || searchKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            String field = (searchBy == null || !searchBy.equals("orderId"))
                    ? "recipient"
                    : "orderId";
            return criteriaBuilder.like(root.get(field), "%" + searchKeyword.trim() + "%");
        };
    }

    /**
     *
     * @ Description : lg_ecommerce_be OrderSpecification Member Field filterByOrderStatus
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/21/2024           63200502    first creation
     *<pre>
     * @param status
     * @return  Specification<Order>
     */
    public static Specification<Order> filterByOrderStatus(String status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null || status.isEmpty()) {
                return criteriaBuilder.notEqual(root.get("orderStatus"), OrderStatus.DAFT);
            }
            return criteriaBuilder.equal(root.get("orderStatus"), status);
        };
    }

    /**
     * @ Description : lg_ecommerce_be OrderSpecification Member Field searchByAccountId
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/22/2024           63200502    first creation
     * <pre>
     * @param accountId
     * @return Specification<Order>
     */
    public static Specification<Order> searchByAccountId(String accountId) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("account").get("accountId"), accountId);
        };
    }
}
