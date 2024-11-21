package com.lg.fresher.lgcommerce.controller.order;

import com.lg.fresher.lgcommerce.model.request.order.ConfirmOrderRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.service.order.OrderService;
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
 * @ Class Name : OrderController
 * @ Description : lg_ecommerce_be OrderController
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/20/2024
 * ------------------------------------------------------------------------
 * Modification Information
 * ------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/20/2024       63200502      api confirm order
 * 11/21/2024       63200502      api get order detail
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/confirm")
    @Operation(summary = "Xác nhận đặt hàng", description = "Người dùng xác nhận đặt hàng")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "0", description = "Đặt hàng thành công"),
            @ApiResponse(responseCode = "15100", description = "Đặt hàng không thành công, không tìm thấy đơn hàng"),
            @ApiResponse(responseCode = "15101", description = "Đặt hàng không thành công, không tìm thấy phương thức giao hàng"),
            @ApiResponse(responseCode = "15102", description = "Đặt hàng không thành công, không tìm thấy phương thức thanh toán"),
    })
    public CommonResponse<Map<String, Object>> confirmOrder(@RequestBody @Valid ConfirmOrderRequest confirmOrderRequest) {
        return orderService.confirmOrder(confirmOrderRequest);
    }

    @GetMapping("/{id}/itemDetail")
    @Operation(summary = "Lấy thông tin chi tiết các mặt hàng của order", description = "Người dùng lấy ra thông tin chi tiết " +
            "bao gồm giá gốc, giá giảm và tổng giá của từng sản phẩm đang đặt")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "0", description = "Lấy thông tin thành công"),
            @ApiResponse(responseCode = "404", description = "Lấy thông tin không thành công, đơn hàng không tồn tại"),
    })
    public CommonResponse<Map<String, Object>> getOrderDetail(@PathVariable String id) {
        return orderService.getOrderDetail(id);
    }

}
