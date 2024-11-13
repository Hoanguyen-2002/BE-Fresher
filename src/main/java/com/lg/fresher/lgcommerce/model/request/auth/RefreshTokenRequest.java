package com.lg.fresher.lgcommerce.model.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * -------------------------------------------------------------------------
 * LG CNS Ecommerce
 * ------------------------------------------------------------------------
 *
 * @ Class Name : RefreshTokenRequest
 * @ Description : lg_ecommerce_be RefreshTokenRequest
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/5/2024
 * ------------------------------------------------------------------------
 * Modification Information
 * ------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/5/2024       63200502      first creation
 */
@Data
@NoArgsConstructor
public class RefreshTokenRequest {
    @NotBlank(message = "Yêu cầu refresh token")
    private String refreshToken;
    @NotBlank(message = "Yêu cầu thông tin tài khoản")
    private String userId;
}
