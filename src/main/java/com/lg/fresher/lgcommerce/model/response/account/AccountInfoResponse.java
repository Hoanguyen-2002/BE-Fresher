package com.lg.fresher.lgcommerce.model.response.account;

import com.lg.fresher.lgcommerce.model.response.address.AddressResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class AccountInfoResponse {
    private String accountId;
    private String fullname;
    private String phone;
    private String avatar;
    private String email;
    private List<AddressResponse> listAddress = new ArrayList<>();
}
