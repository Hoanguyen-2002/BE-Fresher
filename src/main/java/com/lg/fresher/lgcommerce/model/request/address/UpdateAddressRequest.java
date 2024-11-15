package com.lg.fresher.lgcommerce.model.request.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : UpdateAddressRequest
 * @ Description : lg_ecommerce_be UpdateAddressRequest
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/14/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/14/2024       63200502      first creation */
@Data
@NoArgsConstructor
public class UpdateAddressRequest {
    private String addresId;
    @NotBlank(message = "Tỉnh/Thành phố không được để trống")
    @Size(min = 3, max = 30, message = "Tỉnh/Thành phố không hợp lệ")
    private String city;
    @NotBlank(message = "Xã/Phường không được để trống")
    @Size(min = 3, max = 30, message = "Xã/Phường không hợp lệ")
    private String district;
    @NotBlank(message = "Quận/Huyện không được để trống")
    @Size(min = 3, max = 30, message = "Quận/Huyện không hợp lệ")
    private String province;
    @NotBlank(message = "Địa chỉ chi tiết không được để trống")
    @Size(min = 1, max = 30, message = "Địa chỉ chi tiết phải bao gồm số nhà, thôn, xóm")
    private String detailAddress;
}
