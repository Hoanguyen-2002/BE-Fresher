package com.lg.fresher.lgcommerce.model.request.auth;

import com.lg.fresher.lgcommerce.annotation.auth.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * -------------------------------------------------------------------------
 * LG CNS Ecommerce
 * ------------------------------------------------------------------------
 *
 * @ Class Name : ChangePasswordRequest
 * @ Description : lg_ecommerce_be ChangePasswordRequest
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/6/2024
 * ------------------------------------------------------------------------
 * Modification Information
 * ------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/6/2024       63200502      first creation
 */
@Data
@NoArgsConstructor
public class ChangePasswordRequest {
    @NotBlank(message = "Vui lòng nhập mật khẩu cũ")
    private String oldPassword;
    @ValidPassword(min = 7, message = "Mật khẩu không hợp lệ, phải chứa số, chữ cái và ký tự đặc biệt")
    private String newPassword;
}
