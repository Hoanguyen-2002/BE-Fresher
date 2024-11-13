package com.lg.fresher.lgcommerce.model.request.address;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateAddressRequest {
    private String addresId;
    @Size(min = 3, max = 30, message = "Tỉnh/Thành phố không hợp lệ")
    private String city;
    @Size(min = 3, max = 30, message = "Xã/Phường không hợp lệ")
    private String district;
    @Size(min = 3, max = 30, message = "Quận/Huyện không hợp lệ")
    private String province;
}
