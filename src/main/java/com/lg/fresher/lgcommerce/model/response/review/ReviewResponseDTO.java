package com.lg.fresher.lgcommerce.model.response.review;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : ReviewResponseDTO
 * @ Description : lg_ecommerce_be ReviewResponseDTO
 * @ author lg_ecommerce_be Dev Team 63200485
 * @ since 11/20/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/20/2024       63200485      first creation */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewResponseDTO {
    @JsonProperty("review_id")
    private String reviewId;
    @JsonProperty("order_id")
    private String orderId;
    @JsonProperty("order_detail_id")
    private String orderDetailId;
    @JsonProperty("book_id")
    private String bookId;
    @JsonProperty("created_by")
    private String createdBy;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    private String comment;
    private Integer rating;
    private List<ReviewImageResponse> images;
    @JsonProperty("is_reviewed")
    private Boolean isReviewed;
}
