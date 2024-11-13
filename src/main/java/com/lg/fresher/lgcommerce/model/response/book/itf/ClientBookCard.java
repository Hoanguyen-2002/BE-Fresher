package com.lg.fresher.lgcommerce.model.response.book.itf;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Interface Name : ClientBookCard
 * @ Description : lg_ecommerce_be ClientBookCard
 * @ author lg_ecommerce_be Dev Team 63200504
 * @ since 11/6/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/6/2024       63200504      first creation */
public interface ClientBookCard {
    String getBookId();
    String getTitle();
    String getThumbnail();
    Double getAverageRating();
    Double getBasePrice();
    Double getDiscountPrice();
    String getAuthorName();
    String getCategoryName();
    String getPublisherName();
    Integer getTotalSalesCount();
}
