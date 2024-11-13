package com.lg.fresher.lgcommerce.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : SignUpResponse
 * @ Description : lg_ecommerce_be SignUpResponse
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/6/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/6/2024       63200502      first creation */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpResponse {
    private String id;
    private String username;
    private String email;
    private String fullname;
    private String phone;
}
