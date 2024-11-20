package com.lg.fresher.lgcommerce.controller.review;

import com.lg.fresher.lgcommerce.model.request.review.ReviewRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.review.ReviewResponseDTO;
import com.lg.fresher.lgcommerce.service.review.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : ReviewController
 * @ Description : lg_ecommerce_be ReviewController
 * @ author lg_ecommerce_be Dev Team 63200485
 * @ since 11/19/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/19/2024       63200485      first creation */
@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    /**
     *
     * @ Description : lg_ecommerce_be ReviewController Member Field getReviewsByBookId
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/19/2024           63200485    first creation
     *<pre>
     * @param bookId
     * @param pageNo
     * @param pageSize
     * @return  ResponseEntity<CommonResponse<Map<String, Object>>>
     */

    @GetMapping("/book/{bookId}")
    public ResponseEntity<CommonResponse<Map<String, Object>>> getReviewsByBookId(
            @PathVariable String bookId,
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(reviewService.getReviewsByBookId(bookId, pageNo, pageSize));
    }

    @PostMapping("/create")
    public ResponseEntity<CommonResponse<Map<String, Object>>> createReviewByOrderDetailId(
            @RequestParam("orderDetailId") String orderDetailId,
            @Valid @RequestBody ReviewRequest reviewRequest) {
        return ResponseEntity.ok( reviewService.createReviewByOrderDetailId(orderDetailId, reviewRequest));
    }


}
