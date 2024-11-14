package com.lg.fresher.lgcommerce.model.request.review;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ReviewRequest {
    private String bookId;
    private String accountId;
    private int rating;
    private String comment;
    private List<ReviewImageRequest> images;
}
