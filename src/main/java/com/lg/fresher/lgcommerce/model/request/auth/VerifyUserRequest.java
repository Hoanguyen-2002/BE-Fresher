package com.lg.fresher.lgcommerce.model.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : VerifyUserRequest
 * @ Description : lg_ecommerce_be VerifyUserRequest
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/6/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/6/2024       63200502      first creation */
@Data
@NoArgsConstructor
public class VerifyUserRequest {
    @NotBlank(message = "Vui lòng nhập email đã đăng ký")
    private String email;
    @NotBlank(message = "Vui lòng nhập mã xác nhận")
    private String otp;
}
