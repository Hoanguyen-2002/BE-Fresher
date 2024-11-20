package com.lg.fresher.lgcommerce.repository.book.custom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : BookCard
 * @ Description : lg_ecommerce_be BookCard
 * @ author lg_ecommerce_be Dev Team 63200504
 * @ since 11/20/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/20/2024       63200504      first creation */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookCard {
    private String bookId;
    private String title;
    private Double basePrice;
    private String thumbnail;
    private String publisherName;
    private Double averageRating;
    private Double sellingPrice;
    private Integer totalSalesCount;
    private Double discountPrice;
    private String authorName;
    private String categoryName;
}