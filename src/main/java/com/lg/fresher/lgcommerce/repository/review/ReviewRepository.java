package com.lg.fresher.lgcommerce.repository.review;


import com.lg.fresher.lgcommerce.entity.review.Review;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
    @Query(value = "SELECT r FROM Review r JOIN r.book b WHERE b.bookId = :bookId")
    Page<Review> findAllReviewsByBookId(@Param("bookId") String bookId, Pageable pageable);

    @Query(value = "SELECT COUNT(r.review_id) FROM review r WHERE r.book_id = :bookId", nativeQuery = true)
    long countReviewsByBookId(@Param("bookId") String bookId);
}