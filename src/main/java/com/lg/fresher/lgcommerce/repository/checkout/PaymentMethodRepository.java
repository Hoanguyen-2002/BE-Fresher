package com.lg.fresher.lgcommerce.repository.checkout;

import com.lg.fresher.lgcommerce.entity.order.PaymentMethod;
import com.lg.fresher.lgcommerce.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Interface Name : PaymentMethodRepository
 * @ Description : lg_ecommerce_be PaymentMethodRepository
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/19/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/19/2024       63200502      first creation */
@Repository
public interface PaymentMethodRepository extends BaseRepository<PaymentMethod, String> {
}
