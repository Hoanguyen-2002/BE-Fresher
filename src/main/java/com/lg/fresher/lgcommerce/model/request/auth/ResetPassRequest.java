package com.lg.fresher.lgcommerce.model.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : ResetPassRequest
 * @ Description : lg_ecommerce_be ResetPassRequest
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
public class ResetPassRequest {
    @NotBlank(message = "Vui lòng nhập tên tài khoản")
    private String username;
    @NotBlank(message = "Vui lòng nhập email đăng ký tài khoản")
    private String email;
}
