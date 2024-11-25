package com.lg.fresher.lgcommerce.utils;

import com.lg.fresher.lgcommerce.constant.OrderStatus;
import com.lg.fresher.lgcommerce.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
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
 */
@UtilityClass
public class OrderUtil {
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
            case PROCESSING -> newStatus == OrderStatus.SHIPPING;
            case SHIPPING -> newStatus == OrderStatus.COMPLETE;
            default -> false;
        };
    }
}
