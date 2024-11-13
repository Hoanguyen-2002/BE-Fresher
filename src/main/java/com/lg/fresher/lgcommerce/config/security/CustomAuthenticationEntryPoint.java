package com.lg.fresher.lgcommerce.config.security;

import com.lg.fresher.lgcommerce.constant.Status;
import com.lg.fresher.lgcommerce.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        int status = response.getStatus();
        if (status == HttpServletResponse.SC_NOT_FOUND) {
            ResponseUtil.writeErrMsg(response, Status.FAIL_NOT_FOUND);
        } else {
            if (authException instanceof BadCredentialsException) {
                ResponseUtil.writeErrMsg(response, Status.FAIL_INVALID_ACCOUNT);
            } else {
                ResponseUtil.writeErrMsg(response, Status.FAIL_INVALID_TOKEN);
            }
        }
    }
}
