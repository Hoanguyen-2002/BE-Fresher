package com.lg.fresher.lgcommerce.model.response.address;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : AddressResponse
 * @ Description : lg_ecommerce_be AddressResponse
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
public class AddressResponse {
    private String addressId;
    private String city;
    private String province;
    private String district;
    private String detailAddress;
}
