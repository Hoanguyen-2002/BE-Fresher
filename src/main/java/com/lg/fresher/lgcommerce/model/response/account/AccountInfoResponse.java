package com.lg.fresher.lgcommerce.model.response.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lg.fresher.lgcommerce.constant.AccountStatus;
import com.lg.fresher.lgcommerce.model.response.address.AddressResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : AccountInfoResponse
 * @ Description : lg_ecommerce_be AccountInfoResponse
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
public class AccountInfoResponse {
    private String accountId;
    private String fullname;
    private String username;
    private String phone;
    private String avatar;
    private String email;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime joinDate;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updatedDate;
    private AccountStatus status;
    private List<AddressResponse> listAddress = new ArrayList<>();
}
