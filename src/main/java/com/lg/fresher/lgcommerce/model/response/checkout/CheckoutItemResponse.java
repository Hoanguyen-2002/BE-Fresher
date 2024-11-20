package com.lg.fresher.lgcommerce.model.response.checkout;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : CheckoutItemRequest
 * @ Description : lg_ecommerce_be CheckoutItemRequest
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/18/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/18/2024       63200502      first creation */
@Data
@NoArgsConstructor
public class CheckoutItemResponse {
    private String id;
    private String title;
    private String imageURL;
    private int quantity;
    private double originalPrice;
    private double salePrice;
    private double totalPrice;
    private String note;

    /**
     * 
     * @ Description : lg_ecommerce_be CheckoutItemResponse constructor
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/19/2024         63200502    first creation
     *<pre>

     field:

     * @ Description : lg_ecommerce_be CheckoutItemResponse Member Field
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/19/2024          63200502    first creation
     *<pre>
     */
    public CheckoutItemResponse(String id, String title, String imageURL, double originalPrice, double salePrice) {
        this.id = id;
        this.title = title;
        this.imageURL = imageURL;
        this.originalPrice = originalPrice;
        this.salePrice = salePrice;
    }
}
