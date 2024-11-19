package com.lg.fresher.lgcommerce.service.checkout;

import com.lg.fresher.lgcommerce.entity.order.ShippingMethod;
import com.lg.fresher.lgcommerce.mapping.checkout.ShippingMethodMapper;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.repository.checkout.ShippingMethodRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ShippingMethodServiceImplTest {
    @Mock
    private ShippingMethodRepository shippingMethodRepository;
    @Mock
    private ShippingMethodMapper shippingMethodMapper;
    @InjectMocks
    private ShippingMethodServiceImpl shippingMethodService;

    private List<ShippingMethod> shippingMethodList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        shippingMethodList = new ArrayList<>();
    }

    @Test
    public void test_get_all_shipping_method_success(){
        ShippingMethod shippingMethod = new ShippingMethod();
        shippingMethod.setShippingMethodId("uniqueId");
        shippingMethod.setShippingName("uniqueName");
        shippingMethod.setShippingFee(30000.0);

        shippingMethodList.add(shippingMethod);

        when(shippingMethodRepository.findAll()).thenReturn(shippingMethodList);

        CommonResponse<Map<String, Object>> actualResponse = shippingMethodService.getAllShippingMethod();

        assertNotNull(actualResponse.getData());
        assertTrue(actualResponse.getData().containsKey("content"));
    }

}
