package com.lg.fresher.lgcommerce.model.response.address;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressResponse {
    private String addressId;
    private String city;
    private String province;
    private String district;
}
