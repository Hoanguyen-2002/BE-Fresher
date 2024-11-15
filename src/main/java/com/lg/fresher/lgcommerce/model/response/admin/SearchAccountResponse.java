package com.lg.fresher.lgcommerce.model.response.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lg.fresher.lgcommerce.constant.AccountStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * -------------------------------------------------------------------------
 * LG CNS Ecommerce
 * ------------------------------------------------------------------------
 *
 * @ Class Name : SearchAccountResponse
 * @ Description : lg_ecommerce_be SearchAccountResponse
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/11/2024
 * ------------------------------------------------------------------------
 * Modification Information
 * ------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/11/2024       63200502      first creation
 */
@Data
@NoArgsConstructor
public class SearchAccountResponse {
    private String accountId;
    private String fullname;
    private String username;
    private String email;
    private String avatar;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime jointDate;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updatedDate;
    private AccountStatus status;
}
