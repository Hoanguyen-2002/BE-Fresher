package com.lg.fresher.lgcommerce.mapping.review;

import com.lg.fresher.lgcommerce.entity.review.ReviewImage;
import com.lg.fresher.lgcommerce.model.response.review.ReviewImageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ReviewImageMapper {
    @Mapping(source = "reviewImage.reviewImageId", target = "reviewImageId")
    @Mapping(source = "reviewImage.imageUrl", target = "imageUrl")
    ReviewImageResponse toReviewImageResponse(ReviewImage reviewImage);
}
