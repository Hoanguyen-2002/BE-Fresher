package com.lg.fresher.lgcommerce.service.review;

import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.review.ReviewResponse;

import java.util.Map;

public interface ReviewService {
    CommonResponse<Map<String, Object>> getReviewsByBookId(String bookId, int pageNo, int pageSize);
}