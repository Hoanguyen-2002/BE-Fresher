package com.lg.fresher.lgcommerce.model.request.review;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ReviewRequest {

    @NotBlank(message = "Bạn phải lựa chọn số sao")
    private int rating;

    @NotBlank(message = "Không được để trống đánh giá")
    private String comment;

    private List<ReviewImageRequest> images;
}