package com.lg.fresher.lgcommerce.service.review;

import com.lg.fresher.lgcommerce.constant.Status;
import com.lg.fresher.lgcommerce.entity.order.OrderDetail;
import com.lg.fresher.lgcommerce.entity.review.Review;
import com.lg.fresher.lgcommerce.entity.review.ReviewImage;
import com.lg.fresher.lgcommerce.exception.InvalidRequestException;
import com.lg.fresher.lgcommerce.mapping.review.ReviewMapper;
import com.lg.fresher.lgcommerce.model.request.review.ReviewRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.common.MetaData;
import com.lg.fresher.lgcommerce.model.response.review.ReviewResponse;
import com.lg.fresher.lgcommerce.model.response.review.ReviewResponseDTO;
import com.lg.fresher.lgcommerce.repository.order.OrderDetailRepository;
import com.lg.fresher.lgcommerce.repository.review.ReviewImageRepository;
import com.lg.fresher.lgcommerce.repository.review.ReviewRepository;
import com.lg.fresher.lgcommerce.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : ReviewServiceImpl
 * @ Description : lg_ecommerce_be ReviewServiceImpl
 * @ author lg_ecommerce_be Dev Team 63200485
 * @ since 11/19/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/19/2024       63200485      first creation */
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final ReviewMapper reviewMapper;

    /**
     *
     * @ Description : lg_ecommerce_be ReviewServiceImpl constructor
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/19/2024         63200485    first creation
     *<pre>

     field:

     * @ Description : lg_ecommerce_be ReviewServiceImpl Member Field
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/19/2024          63200485    first creation
     *<pre>     */
    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, OrderDetailRepository orderDetailRepository, ReviewImageRepository reviewImageRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.reviewImageRepository = reviewImageRepository;
        this.reviewMapper = reviewMapper;
    }


    /**
     *
     * @param bookId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public CommonResponse<Map<String, Object>> getReviewsByBookId(String bookId, int pageNo, int pageSize) {
        if (!UUIDUtil.isValidUUID(bookId)) {
            throw new InvalidRequestException("Mã sách không hợp lệ!");
        }


        pageNo = Math.max(0, pageNo - 1);
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<Review> reviews = reviewRepository.findAllReviewsByBookId(bookId, pageable);
        long totalElements = reviewRepository.countReviewsByBookId(bookId);

        List<ReviewResponse> reviewResponses = reviews.stream()
                .map(reviewMapper::toReviewResponse)
                .toList();

        MetaData metadata = new MetaData(
                totalElements,
                pageNo + 1 ,
                pageSize
        );

        Map<String, Object> data = new HashMap<>();
        data.put("content", reviewResponses);
        data.put("metaData", metadata);

        return new CommonResponse<>(data);
    }

    /**
     *
     * @param orderDetailId
     * @param reviewRequest
     * @return
     */
    @Override
    @Transactional
    public CommonResponse<Map<String, Object>> createReviewByOrderDetailId(String orderDetailId, ReviewRequest reviewRequest) {

        if (reviewRequest.getImages() != null && reviewRequest.getImages().size() > 3) {
            throw new InvalidRequestException(Status.REVIEW_IMAGES_FAIL_UPLOADED);
        }

        OrderDetail orderDetail = orderDetailRepository.findCompletedAndNotReviewedOrderDetail(orderDetailId)
                .orElseThrow(() -> new InvalidRequestException(Status.REVIEW_FAIL_ORDER_NOT_COMPLETE_OR_IS_REVIEWED));


        Review review = new Review();
        review.setReviewId(UUIDUtil.generateId());
        review.setBook(orderDetail.getBook());
        review.setAccount(orderDetail.getOrder().getAccount());
        review.setRating(reviewRequest.getRating());
        review.setComment(reviewRequest.getComment());
        review.setCreatedBy(orderDetail.getOrder().getAccount().getAccountId());
        review.setCreatedAt(LocalDateTime.now());


        Review savedReview = reviewRepository.save(review);

        if (reviewRequest.getImages() != null && !reviewRequest.getImages().isEmpty()) {
            List<ReviewImage> reviewImages = reviewRequest.getImages().stream()
                    .map(imageUrl -> {
                        ReviewImage reviewImage = new ReviewImage();
                        reviewImage.setReviewImageId(UUIDUtil.generateId());
                        reviewImage.setImageUrl(imageUrl.getImageUrl());
                        reviewImage.setReview(savedReview);
                        reviewImage.setCreatedBy(orderDetail.getOrder().getAccount().getAccountId());
                        reviewImage.setCreatedAt(LocalDateTime.now());
                        return reviewImage;
                    })
                    .toList();

            reviewImageRepository.saveAll(reviewImages);
            savedReview.setReviewImages(reviewImages);
        }

        orderDetail.setIsReviewed(true);
        orderDetailRepository.save(orderDetail);

        ReviewResponseDTO responseDTO = reviewMapper.toReviewResponseDTO(
                orderDetail.getOrder().getOrderId(),
                orderDetailId,
                savedReview,
                orderDetail
        );

        Map<String, Object> data = new HashMap<>();
        data.put("content", responseDTO);

        return new CommonResponse<>(data);
    }
}