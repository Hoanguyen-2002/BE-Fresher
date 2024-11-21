package com.lg.fresher.lgcommerce.model.response.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * -------------------------------------------------------------------------
 * LG CNS Ecommerce
 * ------------------------------------------------------------------------
 *
 * @ Class Name : ConfirmOrderResponse
 * @ Description : lg_ecommerce_be ConfirmOrderResponse
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/20/2024
 * ------------------------------------------------------------------------
 * Modification Information
 * ------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/20/2024       63200502      first creation
 */
@Data
@NoArgsConstructor
public class ConfirmOrderResponse {
    private String orderId;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timeOrdered;
}
