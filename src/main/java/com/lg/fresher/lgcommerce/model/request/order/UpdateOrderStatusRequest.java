package com.lg.fresher.lgcommerce.model.request.order;

import com.lg.fresher.lgcommerce.annotation.order.ValidOrderStatus;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * -------------------------------------------------------------------------
 * LG CNS Ecommerce
 * ------------------------------------------------------------------------
 *
 * @ Class Name : UpdateOrderStatusRequest
 * @ Description : lg_ecommerce_be UpdateOrderStatusRequest
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/25/2024
 * ------------------------------------------------------------------------
 * Modification Information
 * ------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/25/2024       63200502      first creation
 */
@Data
@NoArgsConstructor
public class UpdateOrderStatusRequest {
    @ValidOrderStatus()
    private String newOrderStatus;
    @Size(max = 150, message = "Độ dài ghi chú không thể vựot quá 150 ký tự")
    private String message;
}
