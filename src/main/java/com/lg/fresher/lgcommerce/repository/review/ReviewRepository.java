package com.lg.fresher.lgcommerce.repository.review;

import com.lg.fresher.lgcommerce.entity.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, String> {
    @Query(value = "SELECT r.review_id AS reviewId, r.book_id AS bookId, r.account_id AS accountId, " +
            "a.username AS username, r.rating AS rating, r.comment AS comment " +
            "FROM review r " +
            "JOIN account a ON r.account_id = a.account_id " +
            "WHERE r.book_id = :bookId " +
            "ORDER BY r.created_date DESC " +
            "LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<ReviewProjection> findReviewsByBookId(@Param("bookId") String bookId,
                                                     @Param("offset") int offset,
                                                     @Param("limit") int limit);
    @Query(value = "SELECT COUNT(*) FROM review WHERE book_id = :bookId", nativeQuery = true)
    long countByBookId(@Param("bookId") String bookId);
}
