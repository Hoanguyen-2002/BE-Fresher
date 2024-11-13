package com.lg.fresher.lgcommerce.service.auth;

import com.lg.fresher.lgcommerce.constant.AccountStatus;
import com.lg.fresher.lgcommerce.constant.Status;
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
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * -------------------------------------------------------------------------
 * LG CNS Ecommerce
 * ------------------------------------------------------------------------
 *
 * @ Class Name : RegisterServiceImpl
 * @ Description : lg_ecommerce_be RegisterServiceImpl
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/6/2024
 * ------------------------------------------------------------------------
 * Modification Information
 * ------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/6/2024       63200502      first creation
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterServiceImpl implements RegisterService {
    private final EmailService emailService;
    private final RedisService redisService;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final AccountMapper accountMapper;
    private final ProfileMapper profileMapper;
    @Value("${lg-commerce.app.otpLiveTime:60}")
    private long verificationCodeTime; //second

    /**
     * @param signupRequest
     * @return
     */
    @Override
    public CommonResponse<StringResponse> executeWithJsonResult(SignupRequest signupRequest) {
        if (Boolean.TRUE.equals(accountRepository.existsByUsername(signupRequest.getUsername()))) {
            throw new InvalidRequestException(Status.REGISTER_USER_EXISTED);
        }

        if (Boolean.TRUE.equals(accountRepository.existsByEmail(signupRequest.getEmail()))) {
            throw new InvalidRequestException(Status.REGISTER_EMAIL_EXISTED);
        }
        Account account = accountMapper.toEntity(signupRequest);
        Profile profile = profileMapper.toEntity(signupRequest);
        String hashedPass = encoder.encode(account.getPassword());
        Role role = roleRepository.findRoleByName("USER");

        account.setProfile(profile);
        account.setStatus(AccountStatus.PENDING);
        account.setPassword(hashedPass);
        account.setRole(role);

        accountRepository.save(account);

        String verificationCode = generateVerificationCode();
        redisService.setValue(account.getEmail(), verificationCode, verificationCodeTime);
        sendVerificationEmail(account.getEmail(), verificationCode);

        return CommonResponse.success(Status.REGISTER_SUCCESS.label());
    }

    /**
     * @param verifyUserRequest
     * @return
     */
    @Override
    public CommonResponse<StringResponse> verifyUser(VerifyUserRequest verifyUserRequest) {
        Account account = accountRepository.findAccountByEmail(verifyUserRequest.getEmail())
                .orElseThrow(()->new InvalidRequestException(Status.FAIL_USER_NOT_FOUND));
        Object verifyCode = redisService.getValue(account.getEmail());
        if (verifyCode == null) {
            throw new InvalidRequestException(Status.VERIFY_FAIL_CODE_EXPIRATION);
        }
        if (!verifyCode.toString().equals(verifyUserRequest.getOtp())) {
            throw new InvalidRequestException(Status.VERIFY_FAIL_CODE_INVALID);
        }
        account.setStatus(AccountStatus.ACTIVE);
        redisService.deleteValue(account.getEmail());
        accountRepository.save(account);
        return CommonResponse.success(Status.VERIFY_SUCCESS.label());
    }

    /**
     * @param email
     * @return
     */
    @Override
    public CommonResponse<StringResponse> resendVerifyCode(String email) {
        Account account = accountRepository.findAccountByEmail(email)
                .orElseThrow(() -> new InvalidRequestException(Status.FAIL_USER_NOT_FOUND));
        if (account.getStatus() == AccountStatus.ACTIVE) {
            throw new InvalidRequestException(Status.VERIFY_FAIL_USER_ALREADY_VERIFIED);
        }
        String verificationCode = generateVerificationCode();
        redisService.setValue(account.getEmail(), verificationCode, verificationCodeTime);
        sendVerificationEmail(account.getEmail(), verificationCode);

        return CommonResponse.success(Status.VERIFY_RESEND_SUCCESS.label());

    }

    /**
     * @ Description : lg_ecommerce_be RegisterServiceImpl Member Field sendVerificationEmail
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/6/2024           63200502    first creation
     * <pre>
     * @param email
     * @param otp
     */
    private void sendVerificationEmail(String email, String otp) {
        try {
            emailService.sendVerificationEmail(email, otp);
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * @ Description : lg_ecommerce_be RegisterServiceImpl Member Field generateVerificationCode
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/6/2024           63200502    first creation
     * <pre>
     * @return String
     */
    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }
}
