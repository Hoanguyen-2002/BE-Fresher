package com.lg.fresher.lgcommerce.utils;

import com.lg.fresher.lgcommerce.constant.OrderStatus;
import lombok.experimental.UtilityClass;

/**
 * -------------------------------------------------------------------------
 * LG CNS Ecommerce
 * ------------------------------------------------------------------------
 *
 * @ Class Name : OrderUtil
 * @ Description : lg_ecommerce_be OrderUtil
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/23/2024
 * ------------------------------------------------------------------------
 * Modification Information
 * ------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/23/2024       63200502      first creation
 * 11/26/2024       62200502      add defaule note for order detail item, cancel order
 * 11/26/2024       63200502      add adjustment param
 */
@UtilityClass
public class OrderUtil {
    public final int PLACE_ORDER = 1;
    public final int CANCEL_ORDER = -1;

    public final String DEFAULT_DETAIL_ITEM_NOTE = "";
    public final String DEFAULT_ADMIN_REASON_DENIED_ORDER = "Người bán hàng không thể cung cấp đủ số lượng";
    public final String DEFAULT_USER_REASON_CANCEL_ORDER = "Người mua không còn nhu cầu";
    /**
     * @ Description : lg_ecommerce_be OrderUtil Member Field isOrderStatusValid
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/23/2024           63200502    first creation
     * <pre>
     * @param currentStatus
     * @param newStatus
     * @return boolean
     */
    public boolean isOrderStatusValid(OrderStatus currentStatus, OrderStatus newStatus) {
        return switch (currentStatus) {
            case PENDING -> switch (newStatus) {
                case CANCEL, PROCESSING, DENIED -> true;
                default -> false;
            };
            case PROCESSING -> (newStatus == OrderStatus.SHIPPING || newStatus == OrderStatus.DENIED);
            case SHIPPING -> newStatus == OrderStatus.COMPLETE;
            default -> false;
        };
    }
}
