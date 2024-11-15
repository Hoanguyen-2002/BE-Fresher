package com.lg.fresher.lgcommerce.controller.admin.account;

import com.lg.fresher.lgcommerce.model.request.account.SearchAccountRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.service.account.AccountService;
import com.lg.fresher.lgcommerce.service.admin.account.AdminAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : AdminController
 * @ Description : lg_ecommerce_be AdminController
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/11/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/11/2024       63200502      first creation
 * 12/11/2024       63200502      add method for get account info
 * */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/admin/accounts")
@RequiredArgsConstructor
public class AdminAccountController {
    private final AdminAccountService adminAccountService;
    private final AccountService accountService;

    @PostMapping("/search")
    @Operation(summary = "Tìm kiếm thông tin về account", description = "Tìm kiếm account dựa theo tên người dùng (Full name)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tìm kiếm thành công"),
            @ApiResponse(responseCode = "400", description = "Tìm kiếm không thành công, tham số tìm kiếm không hợp lệ", content = @Content)
    })
    public CommonResponse<Map<String, Object>> searchAccount(@Valid @RequestBody SearchAccountRequest searchAccountRequest){
        return adminAccountService.searchAccount(searchAccountRequest);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy thông tin về account", description = "Trả về thông tin account thông qua id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tìm kiếm thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy người dùng", content = @Content)
    })
    public CommonResponse<Map<String, Object>> getAccountInfo(@PathVariable String id){
        return accountService.getAccountInfo(id);
    }
}
