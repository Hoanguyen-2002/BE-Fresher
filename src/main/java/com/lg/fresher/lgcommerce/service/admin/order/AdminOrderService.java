package com.lg.fresher.lgcommerce.service.admin.order;

import com.lg.fresher.lgcommerce.model.request.order.SearchOrderRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.StringResponse;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Map;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Interface Name : AdminOrderService
 * @ Description : lg_ecommerce_be AdminOrderService
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/21/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/21/2024       63200502      add method get list order */
public interface AdminOrderService {
    /**
     *
     * @ Description : lg_ecommerce_be AdminOrderService Member Field getListOrder
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/21/2024           63200502    first creation
     *<pre>
     * @param searchOrderRequest
     * @return  CommonResponse<Map<String, Object>>
     */
    CommonResponse<Map<String, Object>> getListOrder(@ModelAttribute SearchOrderRequest searchOrderRequest);

    /**
     * @ Description : lg_ecommerce_be AdminOrderService Member Field acceptOrder
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/22/2024           63200502    first creation
     * <pre>
     * @param orderId
     * @return CommonResponse<Map < String, Object>>
     */
    CommonResponse<StringResponse> acceptOrder(String orderId);
}
