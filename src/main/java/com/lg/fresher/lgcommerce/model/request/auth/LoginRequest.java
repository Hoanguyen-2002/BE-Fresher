package com.lg.fresher.lgcommerce.model.request.auth;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : LoginRequest
 * @ Description : lg_ecommerce_be LoginRequest
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/5/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/5/2024       63200502      first creation */
@Data
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "Vui lòng nhập tên tài khoản")
    private String username;

    @NotBlank(message = "Vui lòng nhập mật khẩu")
    private String password;
}
