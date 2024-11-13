package com.lg.fresher.lgcommerce.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : JwtResponse
 * @ Description : lg_ecommerce_be JwtResponse
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/5/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/5/2024       63200502      first creation */
@Data
@NoArgsConstructor
public class JwtResponse extends ResponseData {
    private String accessToken;
    private String refreshToken;
    private String type = "Bearer";
    private String userId;
    private String username;
    private String email;
    private String role;

    /**
     *
     * @ Description : lg_ecommerce_be JwtResponse constructor
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/5/2024         63200502    first creation
     *<pre>

     field:

     * @ Description : lg_ecommerce_be JwtResponse Member Field
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/5/2024          63200502    first creation
     *<pre>
     */
    public JwtResponse(String accessToken, String refreshToken, String userId, String username, String email,
                       String role) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.role = role;
    }

}
