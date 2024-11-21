package com.lg.fresher.lgcommerce.model.request.order;

import com.lg.fresher.lgcommerce.annotation.account.ValidPhone;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * -------------------------------------------------------------------------
 * LG CNS Ecommerce
 * ------------------------------------------------------------------------
 *
 * @ Class Name : ConfirmOrderRequest
 * @ Description : lg_ecommerce_be ConfirmOrderRequest
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
public class ConfirmOrderRequest {
    private String orderId;

    private String shippingMethodId;

    private String paymentMethodId;

    @ValidPhone(message = "Số điện thoại không hợp lệ")
    private String phone;

    @NotBlank(message = "Tên người nhận không được trống")
    private String recipient;

    @NotBlank(message = "Email không được để trống")
    @Size(max = 320, message = "Email không hợp lệ")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Địa chỉ người nhận không được trống")
    private String detailAddress;
}
