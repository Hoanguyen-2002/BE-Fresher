package com.lg.fresher.lgcommerce.model.request.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewImageRequest {
    private String imageUrl;
}
