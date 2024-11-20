package com.lg.fresher.lgcommerce.constant;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Enum Name : OrderStatus
 * @ Description : lg_ecommerce_be OrderStatus
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/19/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/19/2024       63200502      first creation */
public enum OrderStatus {
    DAFT(0),
    PENDING(1),
    CANCEL(2),
    PROCESSING(3),
    SHIPPING(4),
    DENIED(5),
    COMPLETE(6)
    ;

    private int value;

    /**
     *
     * @ Description : lg_ecommerce_be OrderStatus constructor
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/19/2024         63200502    first creation
     *<pre>

     field:

     * @ Description : lg_ecommerce_be OrderStatus Member Field
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/19/2024          63200502    first creation
     *<pre>
     */
    OrderStatus(int value) {
        this.value = value;
    }
}
