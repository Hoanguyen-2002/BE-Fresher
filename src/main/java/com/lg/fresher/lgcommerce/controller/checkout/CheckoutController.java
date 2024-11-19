package com.lg.fresher.lgcommerce.controller.checkout;

import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.service.checkout.ShippingMethodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * -------------------------------------------------------------------------
 * LG CNS Ecommerce
 * ------------------------------------------------------------------------
 *
 * @ Class Name : ShippingController
 * @ Description : lg_ecommerce_be ShippingController
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/18/2024
 * ------------------------------------------------------------------------
 * Modification Information
 * ------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/18/2024       63200502      first creation
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/checkout")
@RequiredArgsConstructor
public class CheckoutController {
    private final ShippingMethodService shippingMethodService;

    @GetMapping("/shipping")
    @Operation(summary = "Lấy thông tin các phương thức giao hàng", description = "Trả về thông tin các phương thức giao hàng")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy thông tin thành công"),
    })
    public CommonResponse<Map<String, Object>> getAllShippingMethod() {
        return shippingMethodService.getAllShippingMethod();
    }
}
