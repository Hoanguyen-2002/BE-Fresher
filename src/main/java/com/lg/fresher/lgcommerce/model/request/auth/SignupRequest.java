package com.lg.fresher.lgcommerce.model.request.auth;

import com.lg.fresher.lgcommerce.annotation.account.ValidPhone;
import com.lg.fresher.lgcommerce.annotation.auth.ValidPassword;
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
 * @ Class Name : SignupRequest
 * @ Description : lg_ecommerce_be SignupRequest
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
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20, message = "")
    private String username;

    @ValidPassword(min = 7, message = "Mật khẩu không hợp lệ, phải chứa số, chữ và chữ cái đặc biệt")
    private String password;

    @NotBlank
    @Size(max = 320, message = "Email không hợp lệ")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank
    @Size(min = 6, max = 50, message = "Vui lòng nhập đầy đủ họ tên")
    private String fullname;

    @NotBlank
    @ValidPhone(message = "Số điện thoại không hợp lệ")
    private String phone;


}
