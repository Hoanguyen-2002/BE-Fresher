package com.lg.fresher.lgcommerce.repository.checkout;

import com.lg.fresher.lgcommerce.entity.order.ShippingMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * -------------------------------------------------------------------------
 * LG CNS Ecommerce
 * ------------------------------------------------------------------------
 *
 * @ Interface Name : ShippingMethodRepository
 * @ Description : lg_ecommerce_be ShippingMethodRepository
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/18/2024
 * ------------------------------------------------------------------------
 * Modification Information
 * ------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/18/2024       63200502      first creation
 */
@Repository
public interface ShippingMethodRepository extends JpaRepository<ShippingMethod, String> {
}
