package com.lg.fresher.lgcommerce.service.admin.order;

import com.lg.fresher.lgcommerce.entity.order.Order;
import com.lg.fresher.lgcommerce.exception.InvalidRequestException;
import com.lg.fresher.lgcommerce.mapping.order.OrderMapper;
import com.lg.fresher.lgcommerce.model.request.order.SearchOrderRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.order.GetListOrderResponse;
import com.lg.fresher.lgcommerce.repository.order.OrderRepository;
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
    private AdminOrderServiceImpl orderService;
    @Mock
    private OrderRepository orderRepository;
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

        CommonResponse<Map<String, Object>> actualResponse = orderService.getListOrder(searchOrderRequest);

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
            orderService.getListOrder(searchOrderRequest);
        });
    }
}
