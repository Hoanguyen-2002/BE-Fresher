package com.lg.fresher.lgcommerce.repository.review;

import com.lg.fresher.lgcommerce.entity.review.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Interface Name : ReviewImageRepository
 * @ Description : lg_ecommerce_be ReviewImageRepository
 * @ author lg_ecommerce_be Dev Team 63200485
 * @ since 11/20/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/20/2024       63200485      first creation */
@Repository
public interface ReviewImageRepository  extends JpaRepository<ReviewImage,String> {
    @Modifying
    @Query("DELETE FROM ReviewImage ri WHERE ri.review.reviewId = :reviewId")
    void deleteByReviewId(@Param("reviewId") String reviewId);
}
