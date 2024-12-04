package com.lg.fresher.lgcommerce.service.admin.order;

import com.lg.fresher.lgcommerce.constant.OrderStatus;
import com.lg.fresher.lgcommerce.constant.Status;
import com.lg.fresher.lgcommerce.entity.order.Order;
import com.lg.fresher.lgcommerce.exception.InvalidRequestException;
import com.lg.fresher.lgcommerce.mapping.order.OrderMapper;
import com.lg.fresher.lgcommerce.model.request.order.SearchOrderRequest;
import com.lg.fresher.lgcommerce.model.request.order.UpdateOrderStatusRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.StringResponse;
import com.lg.fresher.lgcommerce.model.response.order.GetListOrderResponse;
import com.lg.fresher.lgcommerce.model.response.order.OrderDetailItem;
import com.lg.fresher.lgcommerce.repository.book.BookRepository;
import com.lg.fresher.lgcommerce.repository.order.OrderDetailRepository;
import com.lg.fresher.lgcommerce.repository.order.OrderRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AdminOrderServiceImplTest {
    @InjectMocks
    private AdminOrderServiceImpl adminOrderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderDetailRepository orderDetailRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private SearchOrderRequest searchOrderRequest;
    private List<Order> orders;
    private Order order;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        order = new Order();
        orders = Collections.singletonList(order);
    }

    @Test
    public void test_get_list_order_success() {
        when(searchOrderRequest.getSearchKeyword()).thenReturn("");
        when(searchOrderRequest.getSearchBy()).thenReturn("");
        when(searchOrderRequest.getSortRequest()).thenReturn("+createdAt");
        when(searchOrderRequest.getStatus()).thenReturn("PENDING");
        when(searchOrderRequest.getPageNo()).thenReturn(0);
        when(searchOrderRequest.getPageSize()).thenReturn(10);


        Page<Order> orderPage = new PageImpl<>(orders);
        when(orderRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(orderPage);

        when(orderMapper.toGetListOrderResponse(order)).thenReturn(new GetListOrderResponse());

        CommonResponse<Map<String, Object>> actualResponse = adminOrderService.getListOrder(searchOrderRequest);

        assertNotNull(actualResponse);

        Map<String, Object> content = actualResponse.getData();
        assertNotNull(content);
        assertTrue(content.containsKey("content"));
        assertTrue(content.containsKey("metaData"));
    }

    @Test
    public void testGetListOrder_InvalidSortField() {
        when(searchOrderRequest.getSortRequest()).thenReturn("+inValidSortField");

        assertThrows(InvalidRequestException.class, () -> {
            adminOrderService.getListOrder(searchOrderRequest);
        });
    }

    @Test
    public void test_accept_order_request_success() {
        String orderId = "orderId";
        order.setOrderStatus(OrderStatus.PENDING);

        OrderDetailItem detailItem = new OrderDetailItem();
        detailItem.setOrderId("orderId");
        detailItem.setOrderDetailId("orderDetailId");
        detailItem.setOrderEmail("orderEmail");
        detailItem.setImageURL("orderDetailURL");
        detailItem.setTitle("bookTitle");
        detailItem.setQuantity(1);
        detailItem.setFinalPrice(100.0);
        detailItem.setTotal(100.0);
        detailItem.setTotalAmount(100.0);
        detailItem.setBookId("bookId");
        detailItem.setStockQuantity(10);
        detailItem.setOrderStatus(OrderStatus.PENDING);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderDetailRepository.getOrderDetailItemByOrderId(orderId)).thenReturn(
            List.of(detailItem));

        CommonResponse<StringResponse> actualResponse = adminOrderService.acceptOrder(orderId);

        assertEquals(actualResponse.getMsg(), Status.ACCEPT_ORDER_SUCCESS.label());
    }

    @Test
    public void test_accept_order_fail_order_status_has_changed() {
        String orderId = "orderId";
        order.setOrderStatus(OrderStatus.PROCESSING);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        assertThrows(InvalidRequestException.class,
            () -> adminOrderService.acceptOrder(orderId));
    }

    @Test
    public void test_change_order_status_success() {
        String orderId = "orderId";
        order.setOrderStatus(OrderStatus.PENDING);

        UpdateOrderStatusRequest updateOrderStatusRequest = new UpdateOrderStatusRequest();
        updateOrderStatusRequest.setNewOrderStatus("PROCESSING");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        CommonResponse<StringResponse> actualResponse = adminOrderService.updateOrderStatus(orderId,
            updateOrderStatusRequest);

        assertEquals(actualResponse.getMsg(), Status.UPDATE_ORDER_STATUS_SUCCESS.label());
    }

    @Test
    public void test_change_order_status_fail_order_status_has_changed() {
        String orderId = "orderId";
        order.setOrderStatus(OrderStatus.PROCESSING);

        UpdateOrderStatusRequest updateOrderStatusRequest = new UpdateOrderStatusRequest();
        updateOrderStatusRequest.setNewOrderStatus("PROCESSING");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        assertThrows(InvalidRequestException.class,
            () -> adminOrderService.updateOrderStatus(orderId, updateOrderStatusRequest));
    }

    @Test
    public void test_admin_cancel_order_sucess() {
        String orderId = "orderId";
        order.setOrderStatus(OrderStatus.PROCESSING);

        UpdateOrderStatusRequest updateOrderStatusRequest = new UpdateOrderStatusRequest();
        updateOrderStatusRequest.setNewOrderStatus("DENIED");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        CommonResponse<StringResponse> actualResponse = adminOrderService.updateOrderStatus(orderId,
            updateOrderStatusRequest);

        assertEquals(actualResponse.getMsg(), Status.UPDATE_ORDER_STATUS_SUCCESS.label());
    }

    @Test
    public void test_admin_cancel_order_fail_order_is_shipping() {
        String orderId = "orderId";
        order.setOrderStatus(OrderStatus.SHIPPING);

        UpdateOrderStatusRequest updateOrderStatusRequest = new UpdateOrderStatusRequest();
        updateOrderStatusRequest.setNewOrderStatus("DENIED");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        assertThrows(InvalidRequestException.class,
            () -> adminOrderService.updateOrderStatus(orderId, updateOrderStatusRequest));
    }
}
