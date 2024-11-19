package com.lg.fresher.lgcommerce.model.response.review;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lg.fresher.lgcommerce.model.response.base.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : ReviewResponse
 * @ Description : lg_ecommerce_be ReviewResponse
 * @ author lg_ecommerce_be Dev Team 63200485
 * @ since 11/15/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/15/2024       63200485      first creation */


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewResponse {


    @JsonProperty("review_id")
    private String reviewId;
    private String bookId;
    private String accountId;
    private String username;
    private List<ReviewImageResponse> images;
    private Integer rating;
    private String comment;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;


}