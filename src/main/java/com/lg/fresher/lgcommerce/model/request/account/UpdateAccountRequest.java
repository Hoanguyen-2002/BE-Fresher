package com.lg.fresher.lgcommerce.model.request.account;

import com.lg.fresher.lgcommerce.annotation.account.ValidPhone;
import com.lg.fresher.lgcommerce.model.request.address.UpdateAddressRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UpdateAccountRequest {
    @NotBlank
    @Size(min = 6, max = 50, message = "Vui lòng nhập đầy đủ họ tên")
    private String fullname;
    @ValidPhone(message = "Số điện thoại không hợp lệ")
    private String phone;
    @NotBlank(message = "Không thể gỡ avatar")
    private String avatar;
    private List<UpdateAddressRequest> listAddress = new ArrayList<>();
}
