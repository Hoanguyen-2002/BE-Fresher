package com.lg.fresher.lgcommerce.service.review;

import com.lg.fresher.lgcommerce.mapping.review.ReviewMapper;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.review.ReviewResponse;
import com.lg.fresher.lgcommerce.repository.book.BookRepository;
import com.lg.fresher.lgcommerce.repository.review.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;
    private BookRepository bookRepository;
    private ReviewMapper reviewMapper;
    @Override
    public CommonResponse<ReviewResponse> getReviewsByBookId(String bookId, int page, int size) {
        return null;
    }
}
