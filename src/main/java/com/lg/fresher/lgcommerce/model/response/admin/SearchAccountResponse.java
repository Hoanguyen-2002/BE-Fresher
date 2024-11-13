package com.lg.fresher.lgcommerce.model.response.admin;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : SearchAccountResponse
 * @ Description : lg_ecommerce_be SearchAccountResponse
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/11/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/11/2024       63200502      first creation */
@Data
@NoArgsConstructor
public class SearchAccountResponse {
    private String accountId;
    private String fullname;
    private String email;
    private String avatar;
    private LocalDateTime jointDate;
}
