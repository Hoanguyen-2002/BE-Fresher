package com.lg.fresher.lgcommerce.controller.review;

import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.review.ReviewResponse;
import com.lg.fresher.lgcommerce.service.review.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<CommonResponse<ReviewResponse>> getReviewByBookId(
            @PathVariable("bookId") String bookId,
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {

            return new ResponseEntity<>(reviewService.getReviewsByBookId(bookId, pageNo, pageSize), HttpStatus.OK);
    }


}
