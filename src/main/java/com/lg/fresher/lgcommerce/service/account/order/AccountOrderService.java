package com.lg.fresher.lgcommerce.service.account.order;

import com.lg.fresher.lgcommerce.model.request.account.CancelOrderRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.StringResponse;

/**
 * -------------------------------------------------------------------------
 * LG CNS Ecommerce
 * ------------------------------------------------------------------------
 *
 * @ Interface Name : AccountOrderService
 * @ Description : lg_ecommerce_be AccountOrderService
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/26/2024
 * ------------------------------------------------------------------------
 * Modification Information
 * ------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/26/2024       63200502      first creation
 */
public interface AccountOrderService {
    /**
     * @ Description : lg_ecommerce_be AccountOrderService Member Field cancelOrder
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/26/2024           63200502    first creation
     * <pre>
     * @param orderId
     * @param cancelOrderRequest
     * @return CommonResponse<StringResponse>
     */
    CommonResponse<StringResponse> cancelOrder(String orderId, CancelOrderRequest cancelOrderRequest);

}
