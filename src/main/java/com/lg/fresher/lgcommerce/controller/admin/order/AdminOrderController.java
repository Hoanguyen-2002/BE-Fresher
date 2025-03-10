package com.lg.fresher.lgcommerce.controller.admin.order;

import com.lg.fresher.lgcommerce.model.request.order.SearchOrderRequest;
import com.lg.fresher.lgcommerce.model.request.order.UpdateOrderStatusRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.StringResponse;
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

    @PostMapping("/{orderId}")
    @Operation(summary = "Chấp nhận đơn hàng", description = "Nhân viên quản lý xác nhận đơn hàng của người dùng")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "0", description = "Xác nhận đơn thành công"),
            @ApiResponse(responseCode = "17100", description = "Xử lý đơn hàng thất bại, số lượng trong kho không đủ"),
            @ApiResponse(responseCode = "17101", description = "Trạng thái đơn hàng đã bị thay đổi, vui lòng tải lại trang"),
            @ApiResponse(responseCode = "17102", description = "Không tìm thấy đơn hàng, vui lòng thử lại")
    })
    public CommonResponse<StringResponse> acceptOrder(@PathVariable String orderId) {
        return orderService.acceptOrder(orderId);
    }

    @PutMapping("/{orderId}")
    @Operation(summary = "Cập nhật trạng thái đơn hàng", description = "Nhân viên quản lý cập nhật trạng thái đơn hàng của người dùng")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "0", description = "Cập nhật trạng thái đơn thành công"),
            @ApiResponse(responseCode = "17101", description = "Trạng thái đơn hàng đã bị thay đổi hoặc hông hợp lệ, " +
                    "vui lòng tải lại trang"),
            @ApiResponse(responseCode = "17102", description = "Không tìm thấy đơn hàng, vui lòng thử lại")
    })
    public CommonResponse<StringResponse> updateOrderStatus(@PathVariable String orderId,
                                                            @RequestBody @Valid UpdateOrderStatusRequest updateOrderStatusRequest) {
        return orderService.updateOrderStatus(orderId, updateOrderStatusRequest);
    }
}
