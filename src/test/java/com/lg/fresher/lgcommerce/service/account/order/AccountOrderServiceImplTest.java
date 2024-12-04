package com.lg.fresher.lgcommerce.service.account.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.lg.fresher.lgcommerce.config.security.UserDetailsImpl;
import com.lg.fresher.lgcommerce.constant.AccountStatus;
import com.lg.fresher.lgcommerce.constant.OrderStatus;
import com.lg.fresher.lgcommerce.constant.Status;
import com.lg.fresher.lgcommerce.entity.account.Account;
import com.lg.fresher.lgcommerce.entity.order.Order;
import com.lg.fresher.lgcommerce.exception.InvalidRequestException;
import com.lg.fresher.lgcommerce.exception.data.DataNotFoundException;
import com.lg.fresher.lgcommerce.mapping.account.AccountMapper;
import com.lg.fresher.lgcommerce.mapping.order.OrderMapper;
import com.lg.fresher.lgcommerce.model.request.account.CancelOrderRequest;
import com.lg.fresher.lgcommerce.model.request.order.SearchOrderRequest;
import com.lg.fresher.lgcommerce.model.request.order.UpdateOrderStatusRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.StringResponse;
import com.lg.fresher.lgcommerce.model.response.account.AccountInfoResponse;
import com.lg.fresher.lgcommerce.model.response.order.GetListOrderResponse;
import com.lg.fresher.lgcommerce.repository.account.AccountRepository;
import com.lg.fresher.lgcommerce.repository.order.OrderRepository;
import com.lg.fresher.lgcommerce.service.account.AccountServiceImpl;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

public class AccountOrderServiceImplTest {

  @Mock
  private AccountRepository accountRepository;
  @Mock
  private AccountMapper accountMapper;
  @InjectMocks
  private AccountOrderServiceImpl accountOrderService;
  @Mock
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
  public void test_get_my_order_success() {
    Account account = new Account();
    account.setAccountId("uniqueId");

    when(accountService.getAccountFromContext()).thenReturn(account);
    when(searchOrderRequest.getSortRequest()).thenReturn("+createdAt");
    when(searchOrderRequest.getStatus()).thenReturn("PENDING");
    when(searchOrderRequest.getPageNo()).thenReturn(0);
    when(searchOrderRequest.getPageSize()).thenReturn(10);

    Page<Order> orderPage = new PageImpl<>(orders);
    when(orderRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(
        orderPage);

    when(orderMapper.toGetListOrderResponse(order)).thenReturn(new GetListOrderResponse());

    CommonResponse<Map<String, Object>> actualResponse = accountOrderService.getMyOrders(
        searchOrderRequest);

    assertNotNull(actualResponse);

    Map<String, Object> content = actualResponse.getData();
    assertNotNull(content);
    assertTrue(content.containsKey("content"));
    assertTrue(content.containsKey("metaData"));
  }
  @Test
  public void test_user_cancel_order_sucess() {
    String orderId = "orderId";
    CancelOrderRequest cancelOrderRequest = new CancelOrderRequest();
    cancelOrderRequest.setMessage("Tôi không còn nhu cầu");

    Account account = new Account();
    account.setAccountId("uniqueId");

    Order order = new Order();
    order.setOrderId("orderId");
    order.setOrderStatus(OrderStatus.PENDING);
    order.setAccount(account);

    when(accountService.getAccountFromContext()).thenReturn(account);
    when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

    CommonResponse<StringResponse> actualResponse = accountOrderService.cancelOrder(orderId,
        cancelOrderRequest);

    assertEquals(actualResponse.getMsg(), Status.CANCEL_ORDER_SUCCESS.label());
  }

  @Test
  public void test_user_cancel_order_fail_orde_is_shipping() {
    String orderId = "orderId";
    order.setOrderStatus(OrderStatus.SHIPPING);

    Account account = new Account();
    account.setAccountId("uniqueId");

    CancelOrderRequest cancelOrderRequest = new CancelOrderRequest();
    cancelOrderRequest.setMessage("Tôi không còn nhu cầu");

    when(accountService.getAccountFromContext()).thenReturn(account);
    when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

    assertThrows(InvalidRequestException.class,
        () -> accountOrderService.cancelOrder(orderId, cancelOrderRequest));
  }
}
