package com.lg.fresher.lgcommerce.controller.auth;

import com.lg.fresher.lgcommerce.model.request.auth.*;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.StringResponse;
import com.lg.fresher.lgcommerce.service.auth.AuthenService;
import com.lg.fresher.lgcommerce.service.auth.RegisterService;
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
 * @ Class Name : AuthController
 * @ Description : lg_ecommerce_be AuthController
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/5/2024
 * ------------------------------------------------------------------------
 * Modification Information
 * ------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/5/2024       63200502      first creation
 * 11/6/2024       63200502      add reset password API
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenService authenService;
    private final RegisterService registerService;

    /**
     * @ Description : lg_ecommerce_be AuthController Member Field authenticateUser
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/12/2024           63200502    first creation
     * <pre>
     * @param loginRequest
     * @return CommonResponse<Map < String, Object>>
     */
    @PostMapping(value = "/login", produces = "application/json")
    @Operation(summary = "Đăng nhập", description = "Người dùng đăng nhập thông qua tài khoản và mật khẩu")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Đăng nhập thành công"),
            @ApiResponse(responseCode = "400", description = "Tài khoản hoặc mật khẩu không chính xác")
    })
    public CommonResponse<Map<String, Object>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authenService.executeWithJsonResult(loginRequest);
    }

    /**
     * @ Description : lg_ecommerce_be AuthController Member Field registerUser
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/12/2024           63200502    first creation
     * <pre>
     * @param signUpRequest
     * @return CommonResponse<StringResponse>
     */
    @PostMapping("/register")
    @Operation(summary = "Đăng ký", description = "Người dùng đăng ký tài khoản mới")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Đăng ký thành công"),
            @ApiResponse(responseCode = "400", description = "Thông tin cung cấp không chính xác")
    })
    public CommonResponse<StringResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        return registerService.executeWithJsonResult(signUpRequest);
    }

    /**
     * @ Description : lg_ecommerce_be AuthController Member Field verifyUser
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/12/2024           63200502    first creation
     * <pre>
     * @param verifyUserRequest
     * @return CommonResponse<StringResponse>
     */
    @PostMapping("/verify")
    @Operation(summary = "Xác thực tài khoản", description = "Người dùng xác thực tài khoản đã đăng ký")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Xác thực thành công"),
            @ApiResponse(responseCode = "400", description = "Xác thực không thành công, " +
                    "tài khoản đã được xác thực hoặc mã xác thực không hợp lệ")
    })
    public CommonResponse<StringResponse> verifyUser(@Valid @RequestBody VerifyUserRequest verifyUserRequest) {
        return registerService.verifyUser(verifyUserRequest);
    }

    /**
     * @ Description : lg_ecommerce_be AuthController Member Field resendVerifyCode
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/12/2024           63200502    first creation
     * <pre>
     * @param email
     * @return CommonResponse<StringResponse>
     */
    @PostMapping("/resendOtp")
    @Operation(summary = "Gửi lại mã kích hoạt", description = "Người dùng yêu cầu gửi lại mã kích hoạt tài khoản")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gửi mã kích hoạt thành công")
    })
    public CommonResponse<StringResponse> resendVerifyCode(@RequestParam String email) {
        return registerService.resendVerifyCode(email);
    }

    /**
     * @ Description : lg_ecommerce_be AuthController Member Field refreshToken
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/12/2024           63200502    first creation
     * <pre>
     * @param refreshTokenRequest
     * @return CommonResponse<Map < String, Object>>
     */
    @PostMapping("/refreshToken")
    @Operation(summary = "Làm mới token", description = "Người dùng yêu cầu làm mới token, gửi lên refresh token và" +
            "nhận lại access token và refresh token mới")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Làm mới token thành công"),
            @ApiResponse(responseCode = "401", description = "Refresh token không hợp lệ")
    })
    public CommonResponse<Map<String, Object>> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authenService.refreshJwtToken(refreshTokenRequest);
    }

    /**
     * @ Description : lg_ecommerce_be AuthController Member Field revokeToken
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/12/2024           63200502    first creation
     * <pre>
     * @param logoutRequest
     * @return CommonResponse<StringResponse>
     */
    @PostMapping("/logout")
    @Operation(summary = "Đăng xuất", description = "Người dùng đăng xuất, gửi lên refresh token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Đăng xuất thành công"),
    })
    public CommonResponse<StringResponse> revokeToken(@RequestBody LogoutRequest logoutRequest) {
        return authenService.signOut(logoutRequest);
    }

    /**
     * @ Description : lg_ecommerce_be AuthController Member Field resetPassword
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/12/2024           63200502    first creation
     * <pre>
     * @param resetPassRequest
     * @return CommonResponse<StringResponse>
     */
    @PostMapping("/resetPassword")
    @Operation(summary = "Đặt lại mật khẩu", description = "Người dùng đặt lại mật khẩu, gửi lên email và username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Người dùng đặt lại mật khẩu thành công"),
            @ApiResponse(responseCode = "400", description = "Thông tin cung cấp không trùng khớp")
    })
    public CommonResponse<StringResponse> resetPassword(@Valid @RequestBody ResetPassRequest resetPassRequest) {
        return authenService.resetPassword(resetPassRequest);
    }

    /**
     * @ Description : lg_ecommerce_be AuthController Member Field changePassword
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/12/2024           63200502    first creation
     * <pre>
     * @param changePasswordRequest
     * @return CommonResponse<Map < String, Object>>
     */
    @PostMapping("/changePassword")
    @Operation(summary = "Đổi mật khẩu", description = "Người dùng đổi mật khẩu, gửi lên mật khẩu cũ và mật khẩu mới")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Đổi mật khẩu thành công"),
            @ApiResponse(responseCode = "401", description = "Mật khẩu không chính xác")
    })
    public CommonResponse<Map<String, Object>> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        return authenService.changePassword(changePasswordRequest);
    }
}
