package com.lg.fresher.lgcommerce.model.request.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : CancelOrderRequest
 * @ Description : lg_ecommerce_be CancelOrderRequest
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/26/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/26/2024       63200502      first creation */
@Data
@NoArgsConstructor
public class CancelOrderRequest {
    @NotBlank(message = "Vui lòng nhập lý do hủy đơn")
    @Size(min = 0, max = 150, message = "Nội dung ghi chú không được vượt quá 150 ký tự")
    private String message;
}
