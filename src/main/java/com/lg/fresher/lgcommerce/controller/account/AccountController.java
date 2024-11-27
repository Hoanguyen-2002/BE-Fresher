package com.lg.fresher.lgcommerce.controller.account;

import com.lg.fresher.lgcommerce.model.request.account.CancelOrderRequest;
import com.lg.fresher.lgcommerce.model.request.account.UpdateAccountRequest;
import com.lg.fresher.lgcommerce.model.request.order.SearchOrderRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.StringResponse;
import com.lg.fresher.lgcommerce.service.account.AccountService;
import com.lg.fresher.lgcommerce.service.account.order.AccountOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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
 * @ Class Name : AccountController
 * @ Description : lg_ecommerce_be AccountController
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/6/2024
 * ------------------------------------------------------------------------
 * Modification Information
 * ------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/6/2024       63200502      first creation
 * 11/12/2024      63200502      move /accounts/{id} to admin serice and add /myInfo
 * 11/22/2024      63200502      add method to get my order
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final AccountOrderService accountOrderService;

    @GetMapping("/myInfo")
    @Operation(summary = "Lấy thông tin cá nhân của mình", description = "Trả về thông tin account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy thông tin thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy người dùng", content = @Content)
    })
    public CommonResponse<Map<String, Object>> getMyInfo() {
        return accountService.getMyInfo();
    }

    @PutMapping("")
    @Operation(summary = "Cập nhật thông tin về account", description = "Cập nhật thông tin account và trả về thông tin" +
            "sau khi cập nhật")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cập nhật thành công"),
            @ApiResponse(responseCode = "400", description = "Cập nhật không thành công, người dùng không timf thấy hoặc thông tin không hợp lệ", content = @Content)
    })
    public CommonResponse<StringResponse> updateAccountInfo(@Valid @RequestBody UpdateAccountRequest updateAccountRequest) {
        return accountService.updateAccountInfo(updateAccountRequest);
    }

    @PostMapping("/myOrders")
    @Operation(summary = "Lấy thông tin về các đơn hàng của mình", description = "Trả về thông tin các đơn hàng đã đặt")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy thông tin thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy thông tin", content = @Content)
    })
    public CommonResponse<Map<String, Object>> getMyOrders(@Valid @RequestBody SearchOrderRequest searchOrderRequest) {
        return accountService.getMyOrders(searchOrderRequest);
    }

    @PutMapping("/myOrders/{orderId}")
    @Operation(summary = "Người dùng hủy đơn", description = "Người dùng hủy đơn mà họ đã đặt nếu đơn chưa được PROCESSING")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "0", description = "Hủy đơn hàng thành công"),
            @ApiResponse(responseCode = "19100", description = "Hủy đơn hàng thất bại, không tìm thấy đơn hàng"),
            @ApiResponse(responseCode = "19101", description = "Đơn hàng đang được xử lý, không thể hủy đơn. " +
                    "Vui lòng liên hệ Admin để biết thêm thông tin"),
            @ApiResponse(responseCode = "19102", description = "Bạn không sở hữu đơn hàng này"),
    })
    public CommonResponse<StringResponse> cancelOrder(@PathVariable String orderId,
                                                           @Valid @RequestBody CancelOrderRequest cancelOrderRequest) {
        return accountOrderService.cancelOrder(orderId, cancelOrderRequest);
    }
}
