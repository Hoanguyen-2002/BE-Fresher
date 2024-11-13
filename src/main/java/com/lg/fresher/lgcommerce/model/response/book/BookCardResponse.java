package com.lg.fresher.lgcommerce.model.response.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : BookCardResponse
 * @ Description : lg_ecommerce_be BookCardResponse
 * @ author lg_ecommerce_be Dev Team 63200504
 * @ since 11/6/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/6/2024       63200504      first creation */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookCardResponse {
    private String bookId;
    private String title;
    private String thumbnail;
    private Double averageRating;
    private Double basePrice;
    private Double discountPrice;
    private String authorName;
    private String categoryName;
    private String publisherName;
    private Integer totalSalesCount;
}
