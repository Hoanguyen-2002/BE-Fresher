package com.lg.fresher.lgcommerce.controller.admin.order;

import com.lg.fresher.lgcommerce.model.request.order.SearchOrderRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.service.admin.order.AdminOrderService;
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
 * @ Class Name : AdminOrderController
 * @ Description : lg_ecommerce_be AdminOrderController
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/21/2024
 * ------------------------------------------------------------------------
 * Modification Information
 * ------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/21/2024       63200502      first creation
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {
    private final AdminOrderService orderService;

    @PostMapping("")
    @Operation(summary = "Lấy thông tin về danh sách các order", description = "Trả về danh sách các order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "0", description = "Tìm kiếm thành công"),
            @ApiResponse(responseCode = "9001", description = "Tham số tìm kiếm không hợp lệ")
    })
    public CommonResponse<Map<String, Object>> searchOrder(@Valid @RequestBody SearchOrderRequest searchOrderRequest) {
        return orderService.getListOrder(searchOrderRequest);
    }
}
