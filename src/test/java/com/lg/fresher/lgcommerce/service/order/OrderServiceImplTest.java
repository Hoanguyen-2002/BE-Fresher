package com.lg.fresher.lgcommerce.service.order;

import com.lg.fresher.lgcommerce.config.security.UserDetailsImpl;
import com.lg.fresher.lgcommerce.constant.OrderStatus;
import com.lg.fresher.lgcommerce.entity.book.Book;
import com.lg.fresher.lgcommerce.entity.order.Order;
import com.lg.fresher.lgcommerce.entity.order.OrderDetail;
import com.lg.fresher.lgcommerce.entity.order.PaymentMethod;
import com.lg.fresher.lgcommerce.entity.order.ShippingMethod;
import com.lg.fresher.lgcommerce.exception.data.DataNotFoundException;
import com.lg.fresher.lgcommerce.mapping.order.OrderDetailMapper;
import com.lg.fresher.lgcommerce.model.request.order.ConfirmOrderRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.checkout.CheckoutItemResponse;
import com.lg.fresher.lgcommerce.model.response.order.ConfirmOrderResponse;
import com.lg.fresher.lgcommerce.repository.checkout.PaymentMethodRepository;
import com.lg.fresher.lgcommerce.repository.checkout.ShippingMethodRepository;
import com.lg.fresher.lgcommerce.repository.order.OrderDetailRepository;
import com.lg.fresher.lgcommerce.repository.order.OrderRepository;
import com.lg.fresher.lgcommerce.service.email.EmailService;
import java.util.Arrays;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ShippingMethodRepository shippingMethodRepository;
    @Mock
    private PaymentMethodRepository paymentMethodRepository;
    @Mock
    private OrderDetailRepository orderDetailRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private Authentication authentication;
    @Mock
    private SecurityContext securityContext;
    @Mock
    private EmailService emailService;

    @Mock
    private OrderDetailMapper orderDetailMapper;
    @InjectMocks
    private OrderServiceImpl orderService;
    private ConfirmOrderRequest confirmOrderRequest;
    private Order order;
    private ShippingMethod shippingMethod;
    private PaymentMethod paymentMethod;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        confirmOrderRequest = new ConfirmOrderRequest();
        confirmOrderRequest.setOrderId("orderId");
        confirmOrderRequest.setShippingMethodId("shippingMethodId");
        confirmOrderRequest.setPaymentMethodId("paymentMethodId");
        confirmOrderRequest.setPhone("0123456789");
        confirmOrderRequest.setRecipient("Nguyen Van A");
        confirmOrderRequest.setEmail("email@notemail.com");
        confirmOrderRequest.setDetailAddress("detailAddress");

        order = new Order();
        order.setOrderId("orderId");
        order.setOrderStatus(OrderStatus.DRAFT);
        order.setCreatedAt(LocalDateTime.now());
        order.setTotalAmount(100.0);

        paymentMethod = new PaymentMethod();
        paymentMethod.setPaymentName("paymentName");
        paymentMethod.setPaymentMethodId("paymentMethodId");

        shippingMethod = new ShippingMethod();
        shippingMethod.setShippingFee(30000.0);
        shippingMethod.setShippingName("shippingName");
        shippingMethod.setShippingMethodId("shippingMethodId");

        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void test_confirm_order_success() {
        UserDetailsImpl userDetails = new UserDetailsImpl("uniqueId", null, null, null, null);

        when(orderRepository.findById(anyString())).thenReturn(Optional.of(order));
        when(shippingMethodRepository.findById(anyString())).thenReturn(Optional.of(shippingMethod));
        when(paymentMethodRepository.findById(anyString())).thenReturn(Optional.of(paymentMethod));
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authentication);

        CommonResponse<Map<String, Object>> actualResponse = orderService.confirmOrder(confirmOrderRequest);
        ConfirmOrderResponse confirmOrderResponse = (ConfirmOrderResponse) actualResponse.getData().get("content");

        assertNotNull(confirmOrderResponse);
        assertEquals(confirmOrderResponse.getOrderId(), order.getOrderId());
    }

    @Test
    void test_confirm_order_failed_order_not_found() {
        when(orderRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> {
            orderService.confirmOrder(confirmOrderRequest);
        });
    }

    @Test
    void test_get_order_detail_item_success() {
        String orderId = "orderId";
        CheckoutItemResponse checkoutItemResponse = new CheckoutItemResponse();
        checkoutItemResponse.setId("orderItemId");
        checkoutItemResponse.setTitle("orderItemTitle");
        checkoutItemResponse.setImageURL("orderItemURL");
        checkoutItemResponse.setQuantity(10);
        checkoutItemResponse.setOriginalPrice(100.0);
        checkoutItemResponse.setSalePrice(0.0);
        checkoutItemResponse.setTotalPrice(1000.0);


        Mockito.when(orderDetailRepository.getAllOrderDetailByOrderId(anyString())).thenReturn(
            List.of(checkoutItemResponse));

        CommonResponse<Map<String, Object>> actualResponse = orderService.getOrderDetail(orderId);

        List<CheckoutItemResponse> itemResponses = (List<CheckoutItemResponse>) actualResponse
            .getData().get("content");
        assertNotNull(itemResponses);
    }

    @Test
    void test_get_order_detail_item_fail_order_not_existed() {
        when(orderDetailRepository.getAllOrderDetailByOrderId(anyString()))
            .thenReturn(List.of());

        CommonResponse<Map<String, Object>> actualResponse = orderService.getOrderDetail(anyString());

        List<CheckoutItemResponse> itemResponses = (List<CheckoutItemResponse>) actualResponse
            .getData().get("content");
        assertEquals(itemResponses.size(), 0);
    }
}
