package com.lg.fresher.lgcommerce.constant;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Enum Name : AccountStatus
 * @ Description : lg_ecommerce_be AccountStatus
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/6/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/6/2024       63200502      first creation */
public enum AccountStatus {
    PENDING(0),
    ACTIVE(1),
    BANNED(2),
    ;
    private int value;

    /**
     *
     * @ Description : lg_ecommerce_be AccountStatus constructor
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/6/2024         63200502    first creation
     *<pre>

     field:

     * @ Description : lg_ecommerce_be AccountStatus Member Field
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/6/2024          63200502    first creation
     *<pre>
     */
    AccountStatus(int value) {
        this.value = value;
    }
}
