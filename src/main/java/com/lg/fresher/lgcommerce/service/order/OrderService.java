package com.lg.fresher.lgcommerce.service.order;

import com.lg.fresher.lgcommerce.model.request.order.ConfirmOrderRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;

import java.util.Map;

/**
 * -------------------------------------------------------------------------
 * LG CNS Ecommerce
 * ------------------------------------------------------------------------
 *
 * @ Interface Name : OrderService
 * @ Description : lg_ecommerce_be OrderService
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/20/2024
 * ------------------------------------------------------------------------
 * Modification Information
 * ------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/20/2024       63200502      add method confirm order
 */
public interface OrderService {
    /**
     * @ Description : lg_ecommerce_be OrderService Member Field confirmOrder
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/20/2024           63200502    first creation
     * <pre>
     * @param confirmOrderRequest
     * @return CommonResponse<Map < String, Object>>
     */
    CommonResponse<Map<String, Object>> confirmOrder(ConfirmOrderRequest confirmOrderRequest);
}
