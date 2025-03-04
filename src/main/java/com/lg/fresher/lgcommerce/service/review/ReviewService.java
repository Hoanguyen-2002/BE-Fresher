package com.lg.fresher.lgcommerce.service.review;

import com.lg.fresher.lgcommerce.model.request.review.ReviewRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;

import java.util.Map;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Interface Name : ReviewService
 * @ Description : lg_ecommerce_be ReviewService
 * @ author lg_ecommerce_be Dev Team 63200485
 * @ since 11/19/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/19/2024       63200485      first creation */
public interface ReviewService {
    CommonResponse<Map<String, Object>> getReviewsByBookId(String bookId, int pageNo, int pageSize);
    CommonResponse<Map<String, Object>> createReviewByOrderDetailId(String orderDetailId , ReviewRequest reviewRequest);
}