package com.lg.fresher.lgcommerce.controller.checkout;

import com.lg.fresher.lgcommerce.model.request.checkout.CheckoutRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.service.checkout.CheckoutService;
import com.lg.fresher.lgcommerce.service.checkout.PaymentMethodService;
import com.lg.fresher.lgcommerce.service.checkout.ShippingMethodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
 * 11/18/2024       63200502      add capture order
 * 11/19/2024       63200502      add get payment method
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/checkout")
@RequiredArgsConstructor
public class CheckoutController {
    private final ShippingMethodService shippingMethodService;
    private final PaymentMethodService paymentMethodService;
    private final CheckoutService checkoutService;

    @GetMapping("/shipping")
    @Operation(summary = "Lấy thông tin các phương thức giao hàng", description = "Trả về thông tin các phương thức giao hàng")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy thông tin thành công"),
    })
    public CommonResponse<Map<String, Object>> getAllShippingMethod() {
        return shippingMethodService.getAllShippingMethod();
    }

    @Operation(summary = "Lấy thông tin về giá của sản phẩm đã đặt", description = "Trả về thông tin về giá, tổng tiền của 1 loại sản phẩm đặt")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "0", description = "Lấy thông tin thành công"),
            @ApiResponse(responseCode = "4005", description = "Số lượng sản phẩm đặt mua không hợp lệ"),
    })
    @PostMapping("")
    public CommonResponse<Map<String, Object>> checkout(@Valid @RequestBody CheckoutRequest checkoutRequest) {
        return checkoutService.captureOrder(checkoutRequest);
    }

    @GetMapping("/payment")
    @Operation(summary = "Lấy thông tin các phương thức thanh toán", description = "Trả về thông tin các phương thức thanh toán")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy thông tin thành công"),
    })
    public CommonResponse<Map<String, Object>> getAllPaymentMethod() {
        return paymentMethodService.getAllPaymentMethod();
    }
}
