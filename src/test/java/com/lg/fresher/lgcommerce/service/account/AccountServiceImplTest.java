package com.lg.fresher.lgcommerce.service.account;

import com.lg.fresher.lgcommerce.config.security.UserDetailsImpl;
import com.lg.fresher.lgcommerce.constant.Status;
import com.lg.fresher.lgcommerce.entity.account.Account;
import com.lg.fresher.lgcommerce.entity.account.Profile;
import com.lg.fresher.lgcommerce.exception.InvalidRequestException;
import com.lg.fresher.lgcommerce.mapping.account.AccountMapper;
import com.lg.fresher.lgcommerce.model.request.account.UpdateAccountRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.StringResponse;
import com.lg.fresher.lgcommerce.model.response.account.AccountInfoResponse;
import com.lg.fresher.lgcommerce.repository.account.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;
import java.util.Optional;

public class AccountServiceImplTest {
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountMapper accountMapper;
    @InjectMocks
    private AccountServiceImpl accountService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private Authentication authentication;
    @Mock
    private SecurityContext securityContext;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void test_get_user_successfully() {
        String id = "uniqueId";
        Account account = new Account();
        account.setAccountId("uniqueId");

        AccountInfoResponse accountInfoResponse = new AccountInfoResponse();
        accountInfoResponse.setAccountId("uniqueId");

        Mockito.when(accountRepository.findById(id)).thenReturn(Optional.of(account));
        Mockito.when(accountMapper.toAccountInfoResponse(account)).thenReturn(accountInfoResponse);

        CommonResponse<Map<String, Object>> actualResponse = accountService.getAccountInfo(id);

        Assertions.assertEquals(actualResponse.getData().get("content"), accountInfoResponse);
    }

    @Test
    void test_get_user_fail() {
        String id = "uniqueId";
        Mockito.when(accountRepository.findById(id)).thenThrow(InvalidRequestException.class);

        Assertions.assertThrows(InvalidRequestException.class, () -> {
            accountService.getAccountInfo(id);
        });
    }

    @Test
    void update_account_info_successfully() {
        UpdateAccountRequest updateAccountRequest = new UpdateAccountRequest();
        updateAccountRequest.setFullname("Nguyen Van A");
        updateAccountRequest.setAvatar("newAvatar");

        UserDetailsImpl userDetails = new UserDetailsImpl("uniqueId", null, null, null, null);

        Account account = new Account();
        account.setAccountId("uniqueId");

        Profile profile = new Profile();
        profile.setFullname("Nguyen Van B");
        profile.setAvatar("oldAvatar");

        account.setProfile(profile);

        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(userDetails);
        Mockito.when(accountRepository.findById(userDetails.getUserId())).thenReturn(Optional.of(account));

        CommonResponse<StringResponse> actualResponse = accountService.updateAccountInfo(updateAccountRequest);

        Assertions.assertEquals("Cập nhật thông tin thành công", actualResponse.getMsg());
        Assertions.assertEquals("Nguyen Van A", account.getProfile().getFullname());
        Assertions.assertEquals("newAvatar", account.getProfile().getAvatar());
    }

    @Test
    void update_account_info_not_found_account() {
        UpdateAccountRequest updateAccountRequest = new UpdateAccountRequest();
        updateAccountRequest.setFullname("Nguyen Van A");
        updateAccountRequest.setAvatar("newAvatar");

        UserDetailsImpl userDetails = new UserDetailsImpl("uniqueId", null, null, null, null);
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(userDetails);
        Mockito.when(accountRepository.findById(userDetails.getUserId())).thenThrow(new InvalidRequestException(Status.FAIL_USER_NOT_FOUND));

        Assertions.assertThrows(InvalidRequestException.class, () -> accountService.updateAccountInfo(updateAccountRequest));
    }


    @Test
    public void test_get_myInfo(){
        UserDetailsImpl userDetails = new UserDetailsImpl("uniqueId", null, null, null, null);

        Account account = new Account();
        account.setAccountId("uniqueId");


        AccountInfoResponse accountInfoResponse = new AccountInfoResponse();
        accountInfoResponse.setAccountId("uniqueId");

        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(userDetails);
        Mockito.when(accountRepository.findById(userDetails.getUserId())).thenReturn(Optional.of(account));
        Mockito.when(accountMapper.toAccountInfoResponse(account)).thenReturn(accountInfoResponse);

        CommonResponse<Map<String, Object>> actualResponse = accountService.getMyInfo();

        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(actualResponse.getData().get("content"), accountInfoResponse);
    }

    @Test
    public void test_get_myInfo_failed_account_not_found() {
        UserDetailsImpl userDetails = new UserDetailsImpl("uniqueId", null, null, null, null);

        Account account = new Account();
        account.setAccountId("uniqueId");


        AccountInfoResponse accountInfoResponse = new AccountInfoResponse();
        accountInfoResponse.setAccountId("uniqueId");

        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(userDetails);
        Mockito.when(accountRepository.findById(userDetails.getUserId())).thenThrow(new InvalidRequestException(Status.FAIL_USER_NOT_FOUND));

        Assertions.assertThrows(InvalidRequestException.class, () -> accountService.getMyInfo());

    }
}
