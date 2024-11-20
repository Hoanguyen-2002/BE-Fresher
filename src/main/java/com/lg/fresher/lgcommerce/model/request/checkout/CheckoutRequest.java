package com.lg.fresher.lgcommerce.model.request.checkout;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : CheckoutRequest
 * @ Description : lg_ecommerce_be CheckoutRequest
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/18/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/18/2024       63200502      first creation */
@Data
@NoArgsConstructor
public class CheckoutRequest {
    private List<@Valid CheckoutItemRequest> itemList;
}
