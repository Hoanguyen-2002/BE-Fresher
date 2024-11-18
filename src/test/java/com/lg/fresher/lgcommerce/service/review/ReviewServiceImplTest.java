package com.lg.fresher.lgcommerce.service.review;

import com.lg.fresher.lgcommerce.entity.review.Review;
import com.lg.fresher.lgcommerce.exception.InvalidRequestException;
import com.lg.fresher.lgcommerce.mapping.review.ReviewMapper;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.review.ReviewResponse;
import com.lg.fresher.lgcommerce.repository.review.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceImplTest {
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private ReviewMapper reviewMapper;
    @InjectMocks
    private ReviewServiceImpl reviewServiceImpl;
    private Review review;
    private ReviewResponse reviewResponse;
    @BeforeEach
    void setUp() {
        // Prepare a sample review and response for testing
        review = Review.builder()
                .reviewId(UUID.randomUUID().toString())
                .comment("This is a test review.")
                .rating(5)
                .build();
        reviewResponse = ReviewResponse.builder()
                .reviewId(review.getReviewId())
                .comment(review.getComment())
                .rating(review.getRating())
                .build();
    }
    @Test
    void getReviewsByBookId_shouldReturnReviewsSuccessfully() {
        // Arrange
        String bookId = UUID.randomUUID().toString();
        List<Review> reviews = new ArrayList<>();
        reviews.add(review);
        Page<Review> reviewPage = new PageImpl<>(reviews);
        Pageable pageable = PageRequest.of(0, 10);
        // Mock the behavior of reviewRepository and reviewMapper
        when(reviewRepository.findAllReviewsByBookId(bookId, pageable)).thenReturn(reviewPage);
        when(reviewRepository.countReviewsByBookId(bookId)).thenReturn(1L);
        when(reviewMapper.toReviewResponse(any(Review.class))).thenReturn(reviewResponse);
        // Act
        CommonResponse<Map<String, Object>> response = reviewServiceImpl.getReviewsByBookId(bookId, 1, 10);
        // Assert
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals("Thao tác thành công", response.getMsg());
        assertNotNull(response.getData());
        assertTrue(response.getData().containsKey("content"));
        assertTrue(response.getData().containsKey("metadata"));
        List<ReviewResponse> content = (List<ReviewResponse>) response.getData().get("content");
        assertEquals(1, content.size());
        assertEquals(review.getReviewId(), content.get(0).getReviewId());
    }
    @Test
    void getReviewsByBookId_shouldThrowExceptionForInvalidBookId() {
        // Arrange
        String invalidBookId = "invalid-uuid";
        // Act & Assert
        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> {
            reviewServiceImpl.getReviewsByBookId(invalidBookId, 1, 10);
        });
        assertEquals("Mã sách không hợp lệ!", exception.getMessage());
    }
    @Test
    void getReviewsByBookId_shouldReturnEmptyForNoReviews() {
        // Arrange
        String bookId = UUID.randomUUID().toString();
        Page<Review> emptyReviewPage = new PageImpl<>(new ArrayList<>());
        Pageable pageable = PageRequest.of(0, 10);
        // Mock the behavior of reviewRepository and reviewMapper
        when(reviewRepository.findAllReviewsByBookId(bookId, pageable)).thenReturn(emptyReviewPage);
        when(reviewRepository.countReviewsByBookId(bookId)).thenReturn(0L);
        // Act
        CommonResponse<Map<String, Object>> response = reviewServiceImpl.getReviewsByBookId(bookId, 1, 10);
        // Assert
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals("Thao tác thành công", response.getMsg());
        assertNotNull(response.getData());
        assertTrue(response.getData().containsKey("content"));
        assertTrue(response.getData().containsKey("metadata"));
        List<ReviewResponse> content = (List<ReviewResponse>) response.getData().get("content");
        assertTrue(content.isEmpty());
    }
}
