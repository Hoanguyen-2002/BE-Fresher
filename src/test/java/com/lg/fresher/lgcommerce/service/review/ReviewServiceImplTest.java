package com.lg.fresher.lgcommerce.service.review;

import com.lg.fresher.lgcommerce.entity.account.Account;
import com.lg.fresher.lgcommerce.entity.book.Book;
import com.lg.fresher.lgcommerce.entity.order.Order;
import com.lg.fresher.lgcommerce.entity.order.OrderDetail;
import com.lg.fresher.lgcommerce.entity.review.Review;
import com.lg.fresher.lgcommerce.entity.review.ReviewImage;
import com.lg.fresher.lgcommerce.exception.InvalidRequestException;
import com.lg.fresher.lgcommerce.mapping.review.ReviewMapper;
import com.lg.fresher.lgcommerce.model.request.review.ReviewImageRequest;
import com.lg.fresher.lgcommerce.model.request.review.ReviewRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.review.ReviewResponse;
import com.lg.fresher.lgcommerce.model.response.review.ReviewResponseDTO;
import com.lg.fresher.lgcommerce.repository.order.OrderDetailRepository;
import com.lg.fresher.lgcommerce.repository.review.ReviewImageRepository;
import com.lg.fresher.lgcommerce.repository.review.ReviewRepository;
import com.lg.fresher.lgcommerce.utils.UUIDUtil;
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

import java.time.LocalDateTime;
import java.util.*;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : ReviewServiceImplTest
 * @ Description : lg_ecommerce_be ReviewServiceImplTest
 * @ author lg_ecommerce_be Dev Team 63200485
 * @ since 11/19/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/19/2024       63200485      first creation */
@ExtendWith(MockitoExtension.class)
public class ReviewServiceImplTest {
    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewImageRepository reviewImageRepository;

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @InjectMocks
    private ReviewServiceImpl reviewServiceImpl;

    private Review review;
    private ReviewResponse reviewResponse;
    private OrderDetail orderDetail;
    private ReviewRequest reviewRequest;
    private Review savedReview;

    @BeforeEach
    void setUp() {

        review = Review.builder()
                .reviewId(UUIDUtil.generateId())
                .rating(5)
                .comment("Sample review")
                .build();

        reviewResponse = ReviewResponse.builder()
                .reviewId(review.getReviewId())
                .rating(review.getRating())
                .comment(review.getComment())
                .build();

        Book book = Book.builder().bookId(UUIDUtil.generateId()).title("Sample Book").build();
        Account account = Account.builder().accountId(UUIDUtil.generateId()).username("TestUser").build();
        Order order = Order.builder().orderId(UUIDUtil.generateId()).account(account).build();
        orderDetail = OrderDetail.builder()
                .orderDetailId(UUIDUtil.generateId())
                .book(book)
                .order(order)
                .isReviewed(false)
                .build();


        reviewRequest = new ReviewRequest();
        reviewRequest.setRating(5);
        reviewRequest.setComment("Great book!");
        reviewRequest.setImages(Arrays.asList(
                new ReviewImageRequest("http://image1.jpg"),
                new ReviewImageRequest("http://image2.jpg")
        ));


        savedReview = Review.builder()
                .reviewId(UUIDUtil.generateId())
                .book(book)
                .account(account)
                .rating(reviewRequest.getRating())
                .comment(reviewRequest.getComment())
                .build();
    }


    @Test
    void getReviewsByBookId_shouldReturnReviewsSuccessfully() {
        String bookId = UUIDUtil.generateId();
        Pageable pageable = PageRequest.of(0, 10);
        List<Review> reviews = Collections.singletonList(review);
        Page<Review> reviewPage = new PageImpl<>(reviews);

        when(reviewRepository.findAllReviewsByBookId(bookId, pageable)).thenReturn(reviewPage);
        when(reviewRepository.countReviewsByBookId(bookId)).thenReturn(1L);
        when(reviewMapper.toReviewResponse(any(Review.class))).thenReturn(reviewResponse);

        CommonResponse<Map<String, Object>> response = reviewServiceImpl.getReviewsByBookId(bookId, 1, 10);

        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertNotNull(response.getData());
        List<ReviewResponse> content = (List<ReviewResponse>) response.getData().get("content");
        assertEquals(1, content.size());
        assertEquals(reviewResponse.getReviewId(), content.get(0).getReviewId());
    }

    @Test
    void getReviewsByBookId_shouldReturnEmptyListWhenNoReviews() {
        String bookId = UUIDUtil.generateId();
        Pageable pageable = PageRequest.of(0, 10);
        Page<Review> emptyPage = new PageImpl<>(Collections.emptyList());

        when(reviewRepository.findAllReviewsByBookId(bookId, pageable)).thenReturn(emptyPage);
        when(reviewRepository.countReviewsByBookId(bookId)).thenReturn(0L);

        CommonResponse<Map<String, Object>> response = reviewServiceImpl.getReviewsByBookId(bookId, 1, 10);

        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertTrue(((List<ReviewResponse>) response.getData().get("content")).isEmpty());
    }

    @Test
    void getReviewsByBookId_shouldThrowExceptionWhenBookIdInvalid() {
        String invalidBookId = "invalid-uuid";

        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> {
            reviewServiceImpl.getReviewsByBookId(invalidBookId, 1, 10);
        });

        assertEquals("Mã sách không hợp lệ!", exception.getMessage());
    }


    @Test
    void createReviewByOrderDetailId_shouldCreateReviewSuccessfully() {
        when(orderDetailRepository.findCompletedAndNotReviewedOrderDetail(anyString()))
                .thenReturn(Optional.of(orderDetail));

        when(reviewRepository.save(any(Review.class))).thenReturn(savedReview);

        when(reviewMapper.toReviewResponseDTO(anyString(), anyString(), any(Review.class), any(OrderDetail.class)))
                .thenAnswer(invocation -> {
                    String orderId = invocation.getArgument(0);
                    String orderDetailId = invocation.getArgument(1);
                    Review review = invocation.getArgument(2);
                    return ReviewResponseDTO.builder()
                            .reviewId(review.getReviewId())
                            .rating(review.getRating())
                            .comment(review.getComment())
                            .orderId(orderId)
                            .orderDetailId(orderDetailId)
                            .build();
                });

        CommonResponse<Map<String, Object>> response = reviewServiceImpl.createReviewByOrderDetailId(orderDetail.getOrderDetailId(), reviewRequest);

        assertNotNull(response);
        assertEquals(0, response.getCode());
        ReviewResponseDTO responseDTO = (ReviewResponseDTO) response.getData().get("content");
        assertEquals(savedReview.getReviewId(), responseDTO.getReviewId());
    }

    @Test
    void createReviewByOrderDetailId_shouldThrowExceptionWhenOrderDetailNotFound() {
        when(orderDetailRepository.findCompletedAndNotReviewedOrderDetail(anyString()))
                .thenReturn(Optional.empty());

        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> {
            reviewServiceImpl.createReviewByOrderDetailId(UUIDUtil.generateId(), reviewRequest);
        });

        assertEquals("Không thể đánh giá sản phẩm này! Đơn hàng chưa hoàn thành hoặc sản phẩm đã được đánh giá.", exception.getMessage());
    }

    @Test
    void createReviewByOrderDetailId_shouldSaveReviewImagesSuccessfully() {
        when(orderDetailRepository.findCompletedAndNotReviewedOrderDetail(anyString()))
                .thenReturn(Optional.of(orderDetail));

        when(reviewRepository.save(any(Review.class))).thenReturn(savedReview);

        when(reviewImageRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        CommonResponse<Map<String, Object>> response = reviewServiceImpl.createReviewByOrderDetailId(orderDetail.getOrderDetailId(), reviewRequest);

        assertNotNull(response);
        assertEquals(0, response.getCode());
        List<ReviewImage> savedImages = savedReview.getReviewImages();
        assertNotNull(savedImages);
        assertEquals(2, savedImages.size());
    }

    @Test
    void createReviewByOrderDetailId_shouldThrowExceptionWhenMoreThanThreeImages() {
        reviewRequest.setImages(Arrays.asList(
                new ReviewImageRequest("http://image1.jpg"),
                new ReviewImageRequest("http://image2.jpg"),
                new ReviewImageRequest("http://image3.jpg"),
                new ReviewImageRequest("http://image4.jpg")
        ));
        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> {
            reviewServiceImpl.createReviewByOrderDetailId(orderDetail.getOrderDetailId(), reviewRequest);
        });
        assertEquals("Không thể tải lên quá 3 ảnh!", exception.getMessage());
    }
    @Test
    void createReviewByOrderDetailId_shouldAllowThreeImages() {
        reviewRequest.setImages(Arrays.asList(
                new ReviewImageRequest("http://image1.jpg"),
                new ReviewImageRequest("http://image2.jpg"),
                new ReviewImageRequest("http://image3.jpg")
        ));
        when(orderDetailRepository.findCompletedAndNotReviewedOrderDetail(anyString()))
                .thenReturn(Optional.of(orderDetail));
        when(reviewRepository.save(any(Review.class))).thenReturn(savedReview);
        when(reviewImageRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));
        CommonResponse<Map<String, Object>> response = reviewServiceImpl.createReviewByOrderDetailId(orderDetail.getOrderDetailId(), reviewRequest);
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertNotNull(savedReview.getReviewImages());
        assertEquals(3, savedReview.getReviewImages().size());
    }

}