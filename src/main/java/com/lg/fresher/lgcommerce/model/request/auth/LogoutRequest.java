package com.lg.fresher.lgcommerce.model.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LogoutRequest {
    @NotBlank(message = "Yêu cầu refresh token")
    private String refreshToken;
}
