package com.lg.fresher.lgcommerce.service.review;

import com.lg.fresher.lgcommerce.entity.review.Review;
import com.lg.fresher.lgcommerce.exception.InvalidRequestException;
import com.lg.fresher.lgcommerce.mapping.review.ReviewMapper;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.common.MetaData;
import com.lg.fresher.lgcommerce.model.response.review.ReviewResponse;
import com.lg.fresher.lgcommerce.repository.review.ReviewRepository;
import com.lg.fresher.lgcommerce.utils.UUIDUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;
    private ReviewMapper reviewMapper;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
    }


    @Override
    public CommonResponse<Map<String, Object>> getReviewsByBookId(String bookId, int pageNo, int pageSize) {
        if (!UUIDUtil.isValidUUID(bookId)) {
            throw new InvalidRequestException("Mã sách không hợp lệ!");
        }

        // Đảm bảo pageNo không âm và điều chỉnh để bắt đầu từ 0
        pageNo = Math.max(0, pageNo - 1);
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<Review> reviews = reviewRepository.findAllReviewsByBookId(bookId, pageable);
        long totalElements = reviewRepository.countReviewsByBookId(bookId);

        List<ReviewResponse> reviewResponses = reviews.stream()
                .map(reviewMapper::toReviewResponse)
                .collect(Collectors.toList());

        Map<String, Object> data = new HashMap<>();
        data.put("content", reviewResponses);
        data.put("Metadata", new MetaData(totalElements, pageNo, pageSize));

        return new CommonResponse<>(data);
    }
}
