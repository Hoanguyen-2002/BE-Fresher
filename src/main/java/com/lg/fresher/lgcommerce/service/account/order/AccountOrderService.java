package com.lg.fresher.lgcommerce.service.account.order;

import com.lg.fresher.lgcommerce.model.request.account.CancelOrderRequest;
import com.lg.fresher.lgcommerce.model.request.order.SearchOrderRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.StringResponse;
import java.util.Map;

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
 * 12/02/2024       63200502      move method get my order into account order service
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

    /**
     * @ Description : lg_ecommerce_be AccountOrderService Member Field getMyOrders
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 12/2/2024           63200502    first creation
     * <pre>
     * @param searchOrderRequest
     * @return CommonResponse<Map < String, Object>>
     */
    CommonResponse<Map<String, Object>> getMyOrders(SearchOrderRequest searchOrderRequest);
}
