package com.lg.fresher.lgcommerce.service.review;

import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.review.ReviewResponse;

public interface ReviewService {
    CommonResponse<ReviewResponse> getReviewsByBookId(String bookId, int page, int size);
}
