package com.lg.fresher.lgcommerce.repository.review;


import com.lg.fresher.lgcommerce.entity.review.Review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Interface Name : ReviewRepository
 * @ Description : lg_ecommerce_be ReviewRepository
 * @ author lg_ecommerce_be Dev Team 63200485
 * @ since 11/19/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/19/2024       63200485      first creation */
@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
    @Query(value = "SELECT r FROM Review r JOIN r.book b WHERE b.bookId = :bookId ORDER BY r.createdAt DESC")
    Page<Review> findAllReviewsByBookId(@Param("bookId") String bookId, Pageable pageable);

    @Query(value = "SELECT COUNT(r.review_id) FROM review r WHERE r.book_id = :bookId", nativeQuery = true)
    long countReviewsByBookId(@Param("bookId") String bookId);
}