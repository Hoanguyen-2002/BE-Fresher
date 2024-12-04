package com.lg.fresher.lgcommerce.service.account;

import com.lg.fresher.lgcommerce.config.security.UserDetailsImpl;
import com.lg.fresher.lgcommerce.constant.AccountStatus;
import com.lg.fresher.lgcommerce.constant.OrderStatus;
import com.lg.fresher.lgcommerce.constant.Status;
import com.lg.fresher.lgcommerce.entity.account.Account;
import com.lg.fresher.lgcommerce.entity.account.Profile;
import com.lg.fresher.lgcommerce.entity.order.Order;
import com.lg.fresher.lgcommerce.exception.InvalidRequestException;
import com.lg.fresher.lgcommerce.exception.data.DataNotFoundException;
import com.lg.fresher.lgcommerce.mapping.account.AccountMapper;
import com.lg.fresher.lgcommerce.mapping.order.OrderMapper;
import com.lg.fresher.lgcommerce.model.request.account.CancelOrderRequest;
import com.lg.fresher.lgcommerce.model.request.account.UpdateAccountRequest;
import com.lg.fresher.lgcommerce.model.request.order.SearchOrderRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.StringResponse;
import com.lg.fresher.lgcommerce.model.response.account.AccountInfoResponse;
import com.lg.fresher.lgcommerce.model.response.order.GetListOrderResponse;
import com.lg.fresher.lgcommerce.repository.account.AccountRepository;
import com.lg.fresher.lgcommerce.repository.order.OrderRepository;
import com.lg.fresher.lgcommerce.service.account.order.AccountOrderService;
import com.lg.fresher.lgcommerce.service.account.order.AccountOrderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
    @Mock
    private SearchOrderRequest searchOrderRequest;
    private List<Order> orders;
    private Order order;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderMapper orderMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        order = new Order();
        orders = Collections.singletonList(order);
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
        Mockito.when(accountRepository.findById(userDetails.getUserId())).thenThrow(new DataNotFoundException(Status.FAIL_USER_NOT_FOUND.label()));

        Assertions.assertThrows(DataNotFoundException.class, () -> accountService.updateAccountInfo(updateAccountRequest));
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
        Mockito.when(accountRepository.findById(userDetails.getUserId())).thenThrow(new DataNotFoundException(Status.FAIL_USER_NOT_FOUND.label()));

        Assertions.assertThrows(DataNotFoundException.class, () -> accountService.getMyInfo());

    }
}
