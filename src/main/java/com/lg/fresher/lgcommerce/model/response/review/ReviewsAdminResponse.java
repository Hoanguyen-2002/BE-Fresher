package com.lg.fresher.lgcommerce.model.response.review;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewsAdminResponse {
    private String reviewId;
    private String comment;
    private Integer rating;
    private String createdBy;
    private String username;
    private LocalDateTime createdAt;
    private String orderId;
    private String bookId;
    private List<ReviewImageResponse> images;
}
