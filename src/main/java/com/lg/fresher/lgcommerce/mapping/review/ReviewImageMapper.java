package com.lg.fresher.lgcommerce.mapping.review;

import com.lg.fresher.lgcommerce.entity.review.ReviewImage;
import com.lg.fresher.lgcommerce.model.response.review.ReviewImageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Interface Name : ReviewImageMapper
 * @ Description : lg_ecommerce_be ReviewImageMapper
 * @ author lg_ecommerce_be Dev Team 63200485
 * @ since 11/19/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/19/2024       63200485      first creation */
@Component
@Mapper(componentModel = "spring")
public interface ReviewImageMapper {
    @Mapping(source = "reviewImage.reviewImageId", target = "reviewImageId")
    @Mapping(source = "reviewImage.imageUrl", target = "imageUrl")
    ReviewImageResponse toReviewImageResponse(ReviewImage reviewImage);
}
