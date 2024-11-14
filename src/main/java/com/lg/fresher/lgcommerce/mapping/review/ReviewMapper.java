package com.lg.fresher.lgcommerce.mapping.review;

import com.lg.fresher.lgcommerce.entity.review.Review;
import com.lg.fresher.lgcommerce.model.response.review.ReviewResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",uses = {ReviewImageMapper.class})
public interface ReviewMapper {

    @Mapping(source = "review.reviewId", target = "reviewId")
    @Mapping(source = "review.account.accountId", target = "accountId")
    @Mapping(source = "review.account.username", target = "username")
    @Mapping(source = "review.reviewImages", target = "images")
    ReviewResponse toReviewResponse(Review review);
}
