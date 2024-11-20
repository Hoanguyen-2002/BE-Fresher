package com.lg.fresher.lgcommerce.repository.order;

import com.lg.fresher.lgcommerce.entity.order.Order;
import com.lg.fresher.lgcommerce.repository.BaseRepository;
import org.springframework.stereotype.Repository;

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
public interface OrderRepository extends BaseRepository<Order, String> {
}
