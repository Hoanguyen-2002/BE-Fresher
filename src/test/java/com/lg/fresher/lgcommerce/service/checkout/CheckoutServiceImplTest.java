package com.lg.fresher.lgcommerce.service.checkout;

import com.lg.fresher.lgcommerce.model.request.checkout.CheckoutItemRequest;
import com.lg.fresher.lgcommerce.model.request.checkout.CheckoutRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.checkout.CheckoutItemResponse;
import com.lg.fresher.lgcommerce.model.response.checkout.CheckoutResponse;
import com.lg.fresher.lgcommerce.repository.book.PriceRepository;
import com.lg.fresher.lgcommerce.repository.order.OrderDetailRepository;
import com.lg.fresher.lgcommerce.repository.order.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class CheckoutServiceImplTest {
    @Mock
    private PriceRepository priceRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderDetailRepository orderDetailRepository;
    @InjectMocks
    private CheckoutServiceImpl checkoutService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_capture_order_success() {
        CheckoutRequest checkoutRequest = new CheckoutRequest();
        List<CheckoutItemRequest> itemList = List.of(
                new CheckoutItemRequest("book1", 2, 100, 10),
                new CheckoutItemRequest("book2", 3, 200, 20)
        );
        checkoutRequest.setItemList(itemList);

        List<CheckoutItemResponse> mockedItems = List.of(
                new CheckoutItemResponse("book1", "Title1", "book1Image", 100.0, 10.0),
                new CheckoutItemResponse("book2", "Title2", "book2Image", 200.0, 20.0)
        );

        Mockito.when(priceRepository.captureListBookPrice(any(List.class))).thenReturn(mockedItems);

        CommonResponse<Map<String, Object>> response = checkoutService.captureOrder(checkoutRequest);

        CheckoutResponse checkoutItemResponse = (CheckoutResponse) response.getData().get("content");
        List<CheckoutItemResponse> items = checkoutItemResponse.getItemResponseList();
        String orderId = checkoutItemResponse.getOrderId();
        assertNotNull(items);
        assertNotNull(orderId);
        assertEquals(2, items.size());
        assertEquals(180.0, items.get(0).getTotalPrice());
        assertEquals(540.0, items.get(1).getTotalPrice());
    }

    @Test
    public void test_capture_order_fail_product_price_have_changed(){
        CheckoutRequest checkoutRequest = new CheckoutRequest();
        List<CheckoutItemRequest> itemList = List.of(
                new CheckoutItemRequest("book1", 2, 100, 0),
                new CheckoutItemRequest("book2", 3, 200, 0)
        );
        checkoutRequest.setItemList(itemList);

        List<CheckoutItemResponse> mockedItems = List.of(
                new CheckoutItemResponse("book1", "Title1", "book1Image", 100.0, 10.0),
                new CheckoutItemResponse("book2", "Title2", "book2Image", 200.0, 20.0)
        );

        Mockito.when(priceRepository.captureListBookPrice(any(List.class))).thenReturn(mockedItems);

        CommonResponse<Map<String, Object>> response = checkoutService.captureOrder(checkoutRequest);

        CheckoutResponse checkoutItemResponse = (CheckoutResponse) response.getData().get("content");
        List<CheckoutItemResponse> items = checkoutItemResponse.getItemResponseList();
        String orderId = checkoutItemResponse.getOrderId();
        assertNotNull(items);
        assertNull(orderId);
        assertEquals(2, items.size());
        assertEquals(180.0, items.get(0).getTotalPrice());
        assertEquals(540.0, items.get(1).getTotalPrice());
    }
}
