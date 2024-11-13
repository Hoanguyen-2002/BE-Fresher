package com.lg.fresher.lgcommerce.service.auth;

import com.lg.fresher.lgcommerce.constant.AccountStatus;
import com.lg.fresher.lgcommerce.entity.account.Account;
import com.lg.fresher.lgcommerce.entity.account.Profile;
import com.lg.fresher.lgcommerce.entity.role.Role;
import com.lg.fresher.lgcommerce.exception.InvalidRequestException;
import com.lg.fresher.lgcommerce.mapping.account.AccountMapper;
import com.lg.fresher.lgcommerce.mapping.account.ProfileMapper;
import com.lg.fresher.lgcommerce.model.request.auth.SignupRequest;
import com.lg.fresher.lgcommerce.model.request.auth.VerifyUserRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.StringResponse;
import com.lg.fresher.lgcommerce.repository.account.AccountRepository;
import com.lg.fresher.lgcommerce.repository.account.RoleRepository;
import com.lg.fresher.lgcommerce.service.email.EmailService;
import com.lg.fresher.lgcommerce.service.redis.RedisService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

class RegisterServiceImplTest {
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private AccountMapper accountMapper;
    @Mock
    private ProfileMapper profileMapper;
    @Mock
    private EmailService emailService;
    @Mock
    private RedisService redisService;
    @InjectMocks
    private RegisterServiceImpl registerService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }
    // Successfully registers a new user when username and email are unique
    @Test
    void test_registers_new_user_successfully() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("uniqueUser");
        signupRequest.setEmail("unique@example.com");
        signupRequest.setPassword("password123");
        signupRequest.setFullname("Nguyen Van A");
        signupRequest.setPhone("0123456789");

        Profile profile = new Profile();
        profile.setProfileId("uniqueProfileId");
        profile.setFullname("Nguyen Van A");
        profile.setAvatar("url_placeholder");
        profile.setPhone("0123456789");

        Account account = new Account();
        account.setAccountId("uniqueAccountId");
        account.setUsername("uniqueUser");
        account.setPassword("encodedPassword");
        account.setEmail("unique@example.com");

        Role role = new Role();
        role.setRoleId("1");
        role.setName("USER");

        Mockito.when(accountRepository.existsByUsername(signupRequest.getUsername())).thenReturn(false);
        Mockito.when(accountRepository.existsByEmail(signupRequest.getEmail())).thenReturn(false);
        Mockito.when(accountMapper.toEntity(signupRequest)).thenReturn(account);
        Mockito.when(profileMapper.toEntity(signupRequest)).thenReturn(profile);
        Mockito.when(encoder.encode(signupRequest.getPassword())).thenReturn("encodedPassword");
        Mockito.when(roleRepository.findRoleByName("USER")).thenReturn(role);

        CommonResponse<StringResponse> response = registerService.executeWithJsonResult(signupRequest);

        Assertions.assertEquals("Đăng ký thành công", response.getMsg());
    }

    // Throws an exception if the username is already taken
    @Test
    void test_username_already_taken_exception() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("existingUser");
        signupRequest.setEmail("new@example.com");
        signupRequest.setPassword("password");

        Mockito.when(accountRepository.existsByUsername("existingUser")).thenReturn(true);

        Assertions.assertThrows(InvalidRequestException.class, () -> {
            registerService.executeWithJsonResult(signupRequest);
        });
    }

    @Test
    void test_verify_code_sucess(){
        VerifyUserRequest verifyUserRequest = new VerifyUserRequest();
        verifyUserRequest.setEmail("testing@gmail.com");
        verifyUserRequest.setOtp("123456");

        Account account = new Account();
        account.setEmail("testing@gmail.com");
        account.setStatus(AccountStatus.PENDING);

        Mockito.when(accountRepository.findAccountByEmail("testing@gmail.com")).thenReturn(Optional.of(account));
        Mockito.when(redisService.getValue(account.getEmail())).thenReturn("123456");

        CommonResponse<StringResponse> response = registerService.verifyUser(verifyUserRequest);
        Assertions.assertEquals("Xác thực thành công", response.getMsg());
    }

    @Test
    void test_verify_code_has_expired(){
        VerifyUserRequest verifyUserRequest = new VerifyUserRequest();
        verifyUserRequest.setEmail("testing@gmail.com");
        verifyUserRequest.setOtp("123456");

        Account account = new Account();
        account.setEmail("testing@gmail.com");
        account.setStatus(AccountStatus.PENDING);

        Mockito.when(accountRepository.findAccountByEmail(verifyUserRequest.getEmail())).thenReturn(Optional.of(account));
        Mockito.when(redisService.getValue(account.getEmail())).thenReturn(null);

        Assertions.assertThrows(InvalidRequestException.class, () -> {
            registerService.verifyUser(verifyUserRequest);
        });
    }

    @Test
    void test_resend_verify_code_success(){
        String email = "testing@gmail.com";
        Account account = new Account();
        account.setEmail("testing@gmail.com");
        account.setStatus(AccountStatus.PENDING);

        Mockito.when(accountRepository.findAccountByEmail(email)).thenReturn(Optional.of(account));

        CommonResponse<StringResponse> response = registerService.resendVerifyCode(email);
        Assertions.assertEquals("Gửi lại mã xác thực thành công", response.getMsg());
    }

    @Test
    void test_verify_code_account_already_verified(){
        String email = "testing@gmail.com";
        Account account = new Account();
        account.setEmail("testing@gmail.com");
        account.setStatus(AccountStatus.ACTIVE);

        Mockito.when(accountRepository.findAccountByEmail(email)).thenReturn(Optional.of(account));

        Assertions.assertThrows(InvalidRequestException.class, () -> {
            registerService.resendVerifyCode(email);
        });
    }


}
