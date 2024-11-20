package com.lg.fresher.lgcommerce.repository.review;

import com.lg.fresher.lgcommerce.entity.review.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewImageRepository  extends JpaRepository<ReviewImage,String> {
}
