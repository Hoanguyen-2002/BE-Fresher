package com.lg.fresher.lgcommerce.service.auth;

import com.lg.fresher.lgcommerce.model.request.auth.*;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.StringResponse;

import java.util.Map;

public interface AuthenService {

    CommonResponse<Map<String, Object>> executeWithJsonResult(LoginRequest loginRequest);

    CommonResponse<Map<String, Object>> refreshJwtToken(RefreshTokenRequest refreshTokenRequest);

    CommonResponse<StringResponse> signOut(LogoutRequest logoutRequest);

    CommonResponse<StringResponse> resetPassword(ResetPassRequest resetPassRequest);

    CommonResponse<Map<String, Object>> changePassword(ChangePasswordRequest changePasswordRequest);

}
