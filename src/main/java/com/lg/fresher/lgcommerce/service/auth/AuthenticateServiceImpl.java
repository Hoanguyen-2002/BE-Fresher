package com.lg.fresher.lgcommerce.service.auth;


import com.lg.fresher.lgcommerce.config.security.UserDetailsImpl;
import com.lg.fresher.lgcommerce.constant.AccountStatus;
import com.lg.fresher.lgcommerce.constant.Status;
import com.lg.fresher.lgcommerce.entity.account.Account;
import com.lg.fresher.lgcommerce.exception.InvalidRequestException;
import com.lg.fresher.lgcommerce.exception.auth.RefreshTokenException;
import com.lg.fresher.lgcommerce.model.request.auth.*;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.JwtResponse;
import com.lg.fresher.lgcommerce.model.response.StringResponse;
import com.lg.fresher.lgcommerce.repository.account.AccountRepository;
import com.lg.fresher.lgcommerce.service.email.EmailService;
import com.lg.fresher.lgcommerce.utils.JwtUtils;
import io.jsonwebtoken.lang.Strings;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * -------------------------------------------------------------------------
 * LG CNS Ecommerce
 * ------------------------------------------------------------------------
 *
 * @ Class Name : AuthenticateServiceImpl
 * @ Description : lg_ecommerce_be AuthenticateServiceImpl
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/5/2024
 * ------------------------------------------------------------------------
 * Modification Information
 * ------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/5/2024       63200502      first creation
 * 11/6/2024       63200502      add reset password function
 * 11/7/2024       63200502      add userId for revoke refresh token
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticateServiceImpl implements AuthenService {
    private static final Logger authLogger = LoggerFactory.getLogger(AuthenticateServiceImpl.class);
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final AccountRepository accountRepository;
    private final PasswordEncoder encoder;
    private final EmailService emailService;
    private final HttpServletRequest request;


    /**
     * @param loginRequest
     * @return
     */
    @Override
    public CommonResponse<Map<String, Object>> executeWithJsonResult(LoginRequest loginRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String role = getAccountRole(userDetails);

            String jwt = jwtUtils.generateJwtToken(authentication);
            String refreshToken = jwtUtils.generateRefreshToken();

            jwtUtils.saveToken(userDetails.getUserId(),
                    refreshToken, jwt);

            JwtResponse response = new JwtResponse(
                    jwt,
                    refreshToken,
                    userDetails.getUserId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    role);
            Map<String, Object> res = new HashMap<>();
            res.put("content", response);
            return CommonResponse.success(res);
        } catch (BadCredentialsException e) {
            throw new InvalidRequestException(Status.FAIL_INVALID_ACCOUNT);
        }
    }

    /**
     * @param refreshTokenRequest
     * @return
     */
    @Override
    public CommonResponse<Map<String, Object>> refreshJwtToken(RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        String userId = refreshTokenRequest.getUserId();
        if (!jwtUtils.validateRefreshToken(refreshToken, userId)) {
            throw new RefreshTokenException();
        }
        Account account = accountRepository.findUserByAccountId(userId).orElseThrow(
                () -> new InvalidRequestException(Status.FAIL_USER_NOT_FOUND));
        if (account.getStatus() == AccountStatus.BANNED) {
            throw new InvalidRequestException(Status.FAIL_USER_IS_BANNED);
        }

        UserDetailsImpl userDetails = UserDetailsImpl.build(account);
        String role = getAccountRole(userDetails);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        String newAccessToken = jwtUtils.generateJwtToken(authentication);
        String newRefreshToken = jwtUtils.generateRefreshToken();

        jwtUtils.revokeRefreshToken(refreshToken, userId);
        jwtUtils.saveToken(userDetails.getUserId(),
                newRefreshToken, newAccessToken);

        JwtResponse response = new JwtResponse(
                newAccessToken,
                newRefreshToken,
                userDetails.getUserId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                role);
        try {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            authLogger.error("Cannot set user authentication: {}", e.getMessage());
            SecurityContextHolder.clearContext();
            throw e;
        }
        Map<String, Object> res = new HashMap<>();
        res.put("content", response);
        return CommonResponse.success(res);
    }

    @Override
    public CommonResponse<StringResponse> signOut(LogoutRequest logoutRequest) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        String token = logoutRequest.getRefreshToken();
        if (token != null && jwtUtils.validateRefreshToken(token, userDetails.getUserId())) {
            jwtUtils.revokeRefreshToken(token, userDetails.getUserId());
            jwtUtils.revokeAccessToken(request);
        }
        return CommonResponse.success(Status.LOGOUT_SUCCESS.label());
    }

    @Override
    public CommonResponse<StringResponse> resetPassword(ResetPassRequest resetPassRequest) {
        Account account = accountRepository.findAccountByEmail(resetPassRequest.getEmail())
                .orElseThrow(() -> new InvalidRequestException(Status.FAIL_USER_NOT_FOUND));
        if (!account.getUsername().equals(resetPassRequest.getUsername())) {
            throw new InvalidRequestException(Status.FAIL_AUTHENTICATION);
        }
        if (account.getStatus() == AccountStatus.BANNED) {
            throw new InvalidRequestException(Status.FAIL_USER_IS_BANNED);
        }
        String newPassword = generateNewPassword();
        String hashedPass = encoder.encode(newPassword);
        account.setPassword(hashedPass);

        accountRepository.save(account);
        jwtUtils.revokeAllToken(account.getAccountId());
        sendNewPassword(account.getEmail(), newPassword);

        return CommonResponse.success("Reset password successfully!");
    }

    @Override
    public CommonResponse<Map<String, Object>> changePassword(ChangePasswordRequest changePasswordRequest) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Account account = accountRepository.findUserByAccountId(userDetails.getUserId()).orElseThrow(
                () -> new InvalidRequestException(Status.FAIL_USER_NOT_FOUND)
        );

        if (!encoder.matches(changePasswordRequest.getOldPassword(), account.getPassword())) {
            throw new InvalidRequestException(Status.UPDATE_ACCOUNT_FAIL_CHANGE_PASSWORD_NOT_MATCH);
        }

        String hashedNewPassword = encoder.encode(changePasswordRequest.getNewPassword());
        account.setPassword(hashedNewPassword);
        accountRepository.save(account);

        jwtUtils.revokeAllToken(userDetails.getUserId());

        JwtResponse response = generateNewToken();

        Map<String, Object> res = new HashMap<>();
        res.put("content", response);
        return CommonResponse.success(res);
    }

    /**
     * @ Description : lg_ecommerce_be AuthenticateServiceImpl Member Field getAccountRole
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/6/2024           63200502    first creation
     * <pre>
     * @param userDetails
     * @return String
     */
    private String getAccountRole(UserDetailsImpl userDetails) {
        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst().orElse(Strings.EMPTY);
        return role;
    }

    /**
     * @ Description : lg_ecommerce_be AuthenticateServiceImpl Member Field generateNewPassword
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/6/2024           63200502    first creation
     * <pre>
     * @return String
     */
    private String generateNewPassword() {
        return UUID.randomUUID().toString();
    }

    private void sendNewPassword(String email, String newPassword) {
        try {
            emailService.sendNewPassword(email, newPassword);
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * @ Description : lg_ecommerce_be AccountServiceImpl Member Field generateNewToken
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/7/2024           63200502    first creation
     * <pre>
     * @return JwtResponse
     */
    private JwtResponse generateNewToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String role = getAccountRole(userDetails);

        String jwt = jwtUtils.generateJwtToken(authentication);
        String refreshToken = jwtUtils.generateRefreshToken();

        jwtUtils.saveToken(userDetails.getUserId(),
                refreshToken, jwt);

        return new JwtResponse(
                jwt,
                refreshToken,
                userDetails.getUserId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                role);
    }


}