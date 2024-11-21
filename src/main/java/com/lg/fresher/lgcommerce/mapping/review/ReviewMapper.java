package com.lg.fresher.lgcommerce.mapping.review;

import com.lg.fresher.lgcommerce.entity.book.Book;
import com.lg.fresher.lgcommerce.entity.order.OrderDetail;
import com.lg.fresher.lgcommerce.entity.review.Review;
import com.lg.fresher.lgcommerce.model.response.review.ReviewResponse;
import com.lg.fresher.lgcommerce.model.response.review.ReviewResponseDTO;
import com.lg.fresher.lgcommerce.model.response.review.ReviewsAdminResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Interface Name : ReviewMapper
 * @ Description : lg_ecommerce_be ReviewMapper
 * @ author lg_ecommerce_be Dev Team 63200485
 * @ since 11/19/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/19/2024       63200485      first creation */
@Component
@Mapper(componentModel = "spring",uses = {ReviewImageMapper.class})
public interface ReviewMapper {

    @Mapping(source = "review.reviewId", target = "reviewId")
    @Mapping(source = "review.account.accountId", target = "accountId")
    @Mapping(source = "review.account.username", target = "username")
    @Mapping(source = "review.reviewImages", target = "images")
    @Mapping(source = "review.createdAt", target = "createdAt")
    ReviewResponse toReviewResponse(Review review);

    @Mapping(target = "orderId", source = "orderId")
    @Mapping(target = "orderDetailId", source = "orderDetailId")
    @Mapping(target = "bookId", source = "review.book.bookId")
    @Mapping(target = "createdBy", source = "review.createdBy")
    @Mapping(target = "createdAt", source = "review.createdAt")
    @Mapping(target = "comment", source = "review.comment")
    @Mapping(target = "rating", source = "review.rating")
    @Mapping(target = "isReviewed", source = "orderDetail.isReviewed")
    @Mapping(target = "images", source = "review.reviewImages")
    ReviewResponseDTO toReviewResponseDTO(String orderId, String orderDetailId, Review review, OrderDetail orderDetail);

    @Mapping(source = "review.reviewId", target = "reviewId")
    @Mapping(source = "review.comment", target = "comment")
    @Mapping(source = "review.rating", target = "rating")
    @Mapping(source = "review.account.accountId", target = "createdBy")
    @Mapping(source = "review.account.username", target = "username")
    @Mapping(source = "review.createdAt", target = "createdAt")
    @Mapping(source = "review.book.bookId", target = "bookId")
    @Mapping(expression = "java(getOrderIdFromBook(review.getBook()))", target = "orderId")
    @Mapping(source = "review.reviewImages", target = "images")
    ReviewsAdminResponse toReviewsAdminResponse(Review review);
    default String getOrderIdFromBook(Book book) {
        if (book.getOrderDetails() != null && !book.getOrderDetails().isEmpty()) {
            return book.getOrderDetails().get(0).getOrder().getOrderId();
        }
        return null;
    }
}
