package com.lg.fresher.lgcommerce.service.checkout;

import com.lg.fresher.lgcommerce.entity.order.PaymentMethod;
import com.lg.fresher.lgcommerce.entity.order.ShippingMethod;
import com.lg.fresher.lgcommerce.mapping.checkout.PaymentMethodMapper;
import com.lg.fresher.lgcommerce.mapping.checkout.ShippingMethodMapper;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.repository.checkout.PaymentMethodRepository;
import com.lg.fresher.lgcommerce.repository.checkout.ShippingMethodRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class PaymentMethodServiceImplTest {
    @Mock
    private PaymentMethodRepository paymentMethodRepository;
    @Mock
    private PaymentMethodMapper paymentMethodMapper;
    @InjectMocks
    private PaymentMethodServiceImpl shippingMethodService;

    private List<PaymentMethod> paymentMethodList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        paymentMethodList = new ArrayList<>();
    }

    @Test
    public void test_get_all_shipping_method_success(){
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setPaymentMethodId("uniqueId");
        paymentMethod.setPaymentName("uniqueName");

        paymentMethodList.add(paymentMethod);

        when(paymentMethodRepository.findAll()).thenReturn(paymentMethodList);

        CommonResponse<Map<String, Object>> actualResponse = shippingMethodService.getAllPaymentMethod();

        assertNotNull(actualResponse.getData());
        assertTrue(actualResponse.getData().containsKey("content"));
    }
}
