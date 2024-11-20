package com.lg.fresher.lgcommerce.model.response.book;

import com.lg.fresher.lgcommerce.repository.book.custom.BookCard;
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

    public static BookCardResponse from(BookCard param) {
        return BookCardResponse.builder()
                .bookId(param.getBookId())
                .title(param.getTitle())
                .thumbnail(param.getThumbnail())
                .authorName(param.getAuthorName())
                .categoryName(param.getCategoryName())
                .publisherName(param.getPublisherName())
                .averageRating(param.getAverageRating())
                .basePrice(param.getBasePrice())
                .discountPrice(param.getDiscountPrice())
                .totalSalesCount(param.getTotalSalesCount())
                .build();
    }
}
