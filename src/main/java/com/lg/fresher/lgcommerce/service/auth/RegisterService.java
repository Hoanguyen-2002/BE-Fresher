package com.lg.fresher.lgcommerce.service.auth;

import com.lg.fresher.lgcommerce.model.request.auth.SignupRequest;
import com.lg.fresher.lgcommerce.model.request.auth.VerifyUserRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.StringResponse;
import jakarta.mail.MessagingException;

public interface RegisterService {

    CommonResponse<StringResponse> executeWithJsonResult(SignupRequest signupRequest);
    CommonResponse<StringResponse> verifyUser(VerifyUserRequest verifyUserRequest);
    CommonResponse<StringResponse> resendVerifyCode(String email);
}
