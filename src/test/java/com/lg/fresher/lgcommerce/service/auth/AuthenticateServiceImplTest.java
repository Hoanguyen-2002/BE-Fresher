package com.lg.fresher.lgcommerce.service.auth;

import com.lg.fresher.lgcommerce.config.security.UserDetailsImpl;
import com.lg.fresher.lgcommerce.constant.AccountStatus;
import com.lg.fresher.lgcommerce.entity.account.Account;
import com.lg.fresher.lgcommerce.exception.InvalidRequestException;
import com.lg.fresher.lgcommerce.model.request.auth.ChangePasswordRequest;
import com.lg.fresher.lgcommerce.model.request.auth.LoginRequest;
import com.lg.fresher.lgcommerce.model.request.auth.LogoutRequest;
import com.lg.fresher.lgcommerce.model.request.auth.ResetPassRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.JwtResponse;
import com.lg.fresher.lgcommerce.model.response.StringResponse;
import com.lg.fresher.lgcommerce.repository.account.AccountRepository;
import com.lg.fresher.lgcommerce.service.email.EmailService;
import com.lg.fresher.lgcommerce.utils.JwtUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

class AuthenticateServiceImplTest {
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtUtils jwtUtils;
    @Mock
    private Authentication authentication;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private EmailService emailService;
    @Mock
    private SecurityContext securityContext;
    @InjectMocks
    private AuthenticateServiceImpl authenticateService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        // Set up the security context and authentication
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    //login success
    @Test
    void test_login_successfully() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("account");
        loginRequest.setPassword("password");

        UserDetailsImpl userDetails = new UserDetailsImpl("user_id", "Nguyen Van A", "email@gmail.com", AccountStatus.ACTIVE.toString(), Collections.emptyList());

        Mockito.when(authentication.isAuthenticated()).thenReturn(true);
        Mockito.when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        Mockito.when(jwtUtils.generateJwtToken(authentication)).thenReturn("accesstoken");
        Mockito.when(jwtUtils.generateRefreshToken()).thenReturn("refreshtoken");
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);

        JwtResponse response = new JwtResponse();
        response.setAccessToken("acessToken");
        response.setAccessToken("accesstoken");
        response.setUserId(userDetails.getUserId());
        response.setUsername(userDetails.getUsername());
        response.setEmail(userDetails.getEmail());
        response.setRole(userDetails.getAuthorities().toString());

        CommonResponse<Map<String, Object>> jwtResponse = authenticateService.executeWithJsonResult(loginRequest);
        Assertions.assertNotNull(jwtResponse);
    }

    @Test
    void test_login_failed() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("account");
        loginRequest.setPassword("password");

        Mockito.when(authentication.isAuthenticated()).thenReturn(false);
        Mockito.when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);

        Assertions.assertThrows(Exception.class, () -> {
            authenticateService.executeWithJsonResult(loginRequest);
        });
    }

    @Test
    void test_log_out_success() {
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setRefreshToken("refreshToken");
        UserDetailsImpl userDetails = new UserDetailsImpl("unique_userId", null, null, null, null);

        Mockito.when(jwtUtils.validateRefreshToken(logoutRequest.getRefreshToken(), userDetails.getUserId())).thenReturn(true);
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(userDetails);

        CommonResponse<StringResponse> response = authenticateService.signOut(logoutRequest);
        Assertions.assertEquals("Logout successfull", response.getMsg());
    }

    @Test
    void test_reset_password_success() {
        ResetPassRequest resetPassRequest = new ResetPassRequest();
        resetPassRequest.setEmail("email@gmail.com");
        resetPassRequest.setUsername("user01");

        Account account = new Account();
        account.setUsername("user01");
        account.setEmail("email@gmail.com");
        account.setPassword("hehe");

        String hashedPass = "hashed";

        Mockito.when(accountRepository.findAccountByEmail(resetPassRequest.getEmail())).thenReturn(Optional.of(account));

        CommonResponse<StringResponse> response = authenticateService.resetPassword(resetPassRequest);
        String expectResult = "Reset password successfully!";
        Assertions.assertEquals(expectResult, response.getMsg());
    }

    @Test
    void test_reset_password_fail_authentication() {
        ResetPassRequest resetPassRequest = new ResetPassRequest();
        resetPassRequest.setEmail("email@gmail.com");
        resetPassRequest.setUsername("user01");

        Account account = new Account();
        account.setUsername("user03");
        account.setEmail("email@gmail.com");
        account.setPassword("hehe");

        Mockito.when(accountRepository.findAccountByEmail(resetPassRequest.getEmail())).thenReturn(Optional.of(account));

        Assertions.assertThrows(InvalidRequestException.class, () -> {
            authenticateService.resetPassword(resetPassRequest);
        });
    }

    @Test
    void test_change_password_successfully() {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setOldPassword("oldPass");
        changePasswordRequest.setNewPassword("newPass");

        UserDetailsImpl userDetails = new UserDetailsImpl(
                "unique_userId",
                null, null,
                null,
                Collections.emptyList());

        Account account = new Account();
        account.setPassword("oldPass");

        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(userDetails);
        Mockito.when(accountRepository.findUserByAccountId(userDetails.getUserId())).thenReturn(Optional.of(account));
        Mockito.when(encoder.matches(changePasswordRequest.getOldPassword(), account.getPassword())).thenReturn(true);

        CommonResponse<Map<String, Object>> response = authenticateService.changePassword(changePasswordRequest);

        Assertions.assertNotNull(response);

    }

    @Test
    void test_change_password_failed() {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setOldPassword("oldPass");
        changePasswordRequest.setNewPassword("newPass");

        UserDetailsImpl userDetails = new UserDetailsImpl(
                "unique_userId",
                null, null,
                null,
                Collections.emptyList());

        Account account = new Account();
        account.setPassword("oldPass");

        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(userDetails);
        Mockito.when(accountRepository.findUserByAccountId(userDetails.getUserId())).thenReturn(Optional.of(account));
        Mockito.when(encoder.matches(changePasswordRequest.getOldPassword(), account.getPassword())).thenReturn(false);

        Assertions.assertThrows(InvalidRequestException.class, () -> authenticateService.changePassword(changePasswordRequest));
    }
}
