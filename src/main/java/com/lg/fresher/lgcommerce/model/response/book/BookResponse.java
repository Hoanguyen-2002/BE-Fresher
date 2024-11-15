package com.lg.fresher.lgcommerce.model.response.book;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lg.fresher.lgcommerce.model.response.author.AuthorResponse;
import com.lg.fresher.lgcommerce.model.response.base.BaseResponse;
import com.lg.fresher.lgcommerce.model.response.book_image.BookImageResponse;
import com.lg.fresher.lgcommerce.model.response.admin.category.CategoryDTO;
import com.lg.fresher.lgcommerce.model.response.price.PriceResponse;
import com.lg.fresher.lgcommerce.model.response.property.PropertyResponse;
import com.lg.fresher.lgcommerce.model.response.publisher.PublisherResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : BookResponse
 * @ Description : lg_ecommerce_be BookResponse
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
public class BookResponse extends BaseResponse {
    private String bookId;
    private String title;
    private String description;
    private String thumbnail;
    private Integer quantity;
    private Boolean status;
    private PublisherResponse publisher;
    private List<BookImageResponse> images;
    private List<AuthorResponse> authors;
    private List<CategoryDTO> categories;
    private PriceResponse price;
    private List<PropertyResponse> properties;
    private Double averageRating;
    private Integer totalSalesCount;
    private Integer totalReviewsCount;
}
