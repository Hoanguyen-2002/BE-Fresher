package com.lg.fresher.lgcommerce.repository.book;

import com.lg.fresher.lgcommerce.entity.book.Book;
import com.lg.fresher.lgcommerce.model.response.book.itf.ClientBookCard;
import com.lg.fresher.lgcommerce.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Interface Name : BookRepository
 * @ Description : lg_ecommerce_be BookRepository
 * @ author lg_ecommerce_be Dev Team 63200504
 * @ since 11/6/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/6/2024       63200504      first creation */
@Repository
public interface BookRepository extends BaseRepository<Book, String> {

    @Query(value = """
    WITH BOOK_MASTER AS (
    SELECT b.book_id, b.title, b.thumbnail, COALESCE(AVG(r.rating), 0) AS averageRating,
          pr.base_price, pr.discount_price, p.name AS publisherName
    FROM book b
            LEFT JOIN price pr ON pr.book_id = b.book_id
            LEFT JOIN publisher p ON b.publisher_id = p.publisher_id
            LEFT JOIN review r ON r.book_id = b.book_id
    WHERE (:title IS NULL OR LOWER(b.title COLLATE utf8mb4_bin) LIKE LOWER(CONCAT('%', :title, '%') COLLATE utf8mb4_bin))
    AND (:publisher IS NULL OR p.publisher_id = :publisher)
    AND (:minPrice IS NULL OR pr.base_price >= :minPrice)
    AND (:maxPrice IS NULL OR pr.base_price <= :maxPrice)
    AND b.status = 1
    GROUP BY b.book_id, b.title, b.thumbnail,
            pr.base_price, pr.discount_price, p.name
    HAVING (:rating IS NULL OR FLOOR(COALESCE(AVG(r.rating), 0)) = :rating)
    ORDER BY b.created_at DESC
    ), SALES_COUNT_MASTER AS (
        SELECT od.book_id, SUM(quantity) AS totalSalesCount
        FROM order_detail od
        GROUP BY od.book_id
    ), BOOK_CATEGORY_MASTER AS (
        SELECT bc.book_id,
            GROUP_CONCAT(ca.name ORDER BY ca.name SEPARATOR ', ') as categoryName
        FROM book_category bc
        INNER JOIN category ca ON ca.category_id = bc.category_id
        WHERE (:categories IS NULL OR bc.category_id IN (:categories))
        GROUP BY bc.book_id
    ), BOOK_AUTHOR_MASTER AS (
        SELECT ba.book_id,
               GROUP_CONCAT(a.name ORDER BY a.name SEPARATOR ', ') as authorName
        FROM book_author ba
        INNER JOIN author a ON a.author_id = ba.author_id
        WHERE (:authors IS NULL OR ba.author_id IN (:authors))
        GROUP BY ba.book_id
    )
    SELECT bm.book_id, bm.title, bm.thumbnail, bm.base_price, bm.discount_price, bm.publisherName,
           bm.averageRating, IFNULL(scm.totalSalesCount, 0) AS totalSalesCount, bam.authorName, bcm.categoryName
    FROM BOOK_MASTER bm
    LEFT JOIN SALES_COUNT_MASTER scm ON scm.book_id = bm.book_id
    INNER JOIN BOOK_AUTHOR_MASTER bam ON bam.book_id = bm.book_id
    INNER JOIN BOOK_CATEGORY_MASTER bcm ON bcm.book_id = bm.book_id
    """,
            countQuery = """
        WITH BOOK_MASTER AS (
        SELECT b.book_id, b.title, b.thumbnail, COALESCE(AVG(r.rating), 0) AS averageRating,
              pr.base_price, pr.discount_price, p.name AS publisherName
        FROM book b
                LEFT JOIN price pr ON pr.book_id = b.book_id
                LEFT JOIN publisher p ON b.publisher_id = p.publisher_id
                LEFT JOIN review r ON r.book_id = b.book_id
        WHERE (:title IS NULL OR LOWER(b.title COLLATE utf8mb4_bin) LIKE LOWER(CONCAT('%', :title, '%') COLLATE utf8mb4_bin))
        AND (:publisher IS NULL OR p.publisher_id = :publisher)
        AND (:minPrice IS NULL OR pr.base_price >= :minPrice)
        AND (:maxPrice IS NULL OR pr.base_price <= :maxPrice)
        GROUP BY b.book_id, b.title, b.thumbnail,
                pr.base_price, pr.discount_price, p.name
        HAVING (:rating IS NULL OR FLOOR(COALESCE(AVG(r.rating), 0)) = :rating)
        ORDER BY b.created_at DESC
        ), BOOK_CATEGORY_MASTER AS (
            SELECT DISTINCT bc.book_id
            FROM book_category bc
            WHERE (:categories IS NULL OR bc.category_id IN (:categories))
        ), BOOK_AUTHOR_MASTER AS (
            SELECT DISTINCT ba.book_id
            FROM book_author ba
            WHERE (:authors IS NULL OR ba.author_id IN (:authors))
        )
        SELECT COUNT(*)
        FROM BOOK_MASTER bm
        INNER JOIN BOOK_AUTHOR_MASTER bam ON bam.book_id = bm.book_id
        INNER JOIN BOOK_CATEGORY_MASTER bcm ON bcm.book_id = bm.book_id
    """,
            nativeQuery = true)
    Page<ClientBookCard> getBookCards(
            @Param("title") String title,
            @Param("publisher") String publisher,
            @Param("rating") Integer rating,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("authors") List<String> authors,
            @Param("categories") List<String> categories,
            Pageable pageable
    );

    @Query(value = """    
    WITH BOOK_MASTER AS (
    SELECT b.book_id, b.title, b.thumbnail, b.description, b.quantity,
           COALESCE(AVG(r.rating), 0) AS averageRating,
           COALESCE(COUNT(r.review_id), 0) AS totalReviewCounts,
          pr.base_price, pr.discount_price, JSON_OBJECT(
              'publisherId', p.publisher_id,
              'name', p.name
              ) AS publisher
    FROM book b
    LEFT JOIN price pr ON pr.book_id = b.book_id
    LEFT JOIN publisher p ON b.publisher_id = p.publisher_id
    LEFT JOIN review r ON r.book_id = b.book_id
    WHERE b.book_id = :bookId
    AND b.status = TRUE
    GROUP BY b.book_id, b.title, b.thumbnail, b.quantity,
            pr.base_price, pr.discount_price, p.name, b.description
    ), SALES_COUNT_MASTER AS (
        SELECT od.book_id, SUM(quantity) AS totalSalesCount
        FROM order_detail od
        WHERE od.book_id = :bookId
        GROUP BY od.book_id
    ), BOOK_CATEGORY_MASTER AS (
        SELECT bc.book_id,
        JSON_ARRAYAGG(JSON_OBJECT(
              'categoryId', ca.category_id,
              'name', ca.name
              )) AS book_category_json
        FROM book_category bc
        INNER JOIN category ca ON ca.category_id = bc.category_id
        WHERE bc.book_id = :bookId
        GROUP BY bc.book_id
    ), BOOK_AUTHOR_MASTER AS (
        SELECT ba.book_id,
       JSON_ARRAYAGG(JSON_OBJECT(
              'bookAuthorId', ba.book_author_id,
              'name', a.name
              )) AS book_author_json
        FROM book_author ba
        INNER JOIN author a ON a.author_id = ba.author_id
        WHERE ba.book_id = :bookId
        GROUP BY ba.book_id
    ), BOOK_IMAGES AS (
        SELECT b.book_id,
        JSON_ARRAYAGG(JSON_OBJECT(
                      'bookImageId', bi.book_image_id,
                      'imageUrl', bi.image_url
                      )) AS book_image_json
        FROM book b
        INNER JOIN book_image bi ON bi.book_id = b.book_id
        WHERE b.book_id = :bookId
        GROUP BY b.book_id
    ), BOOK_PROPERTY AS ( 
        SELECT b.book_id,
        JSON_ARRAYAGG(JSON_OBJECT(
                      'bookPropertyId', bp.book_property_id,
                      'name', p.name,
                      'value', bp.value
                      )) AS book_property_json
        FROM book b
        INNER JOIN book_property bp ON bp.book_id = b.book_id
        INNER JOIN property p ON p.property_id = bp.property_id
        WHERE b.book_id = :bookId
        GROUP BY b.book_id
    )
    SELECT bm.book_id, bm.title, bm.thumbnail, bm.base_price, bm.discount_price, bm.description, bm.quantity,
           bm.publisher, bm.totalReviewCounts,
           bm.averageRating, IFNULL(scm.totalSalesCount, 0) AS totalSalesCount,
           bam.book_author_json, bcm.book_category_json, 
           bi.book_image_json, bp.book_property_json
    FROM BOOK_MASTER bm
    LEFT JOIN SALES_COUNT_MASTER scm ON scm.book_id = bm.book_id
    INNER JOIN BOOK_AUTHOR_MASTER bam ON bam.book_id = bm.book_id
    INNER JOIN BOOK_CATEGORY_MASTER bcm ON bcm.book_id = bm.book_id
    INNER JOIN BOOK_IMAGES bi ON bi.book_id = bm.book_id
    INNER JOIN BOOK_PROPERTY bp ON bp.book_id = bm.book_id
    """, nativeQuery = true)
    Optional<Map<String, Object>> findBookDetailById(@Param("bookId") String bookId);

    boolean existsByTitle(@Param("title") String title);

    @Query(value = """
    WITH BOOK_MASTER AS (
    SELECT b.book_id, b.title, b.thumbnail, COALESCE(AVG(r.rating), 0) AS averageRating,
          pr.base_price, pr.discount_price, p.name AS publisherName
    FROM book b
            LEFT JOIN price pr ON pr.book_id = b.book_id
            LEFT JOIN publisher p ON b.publisher_id = p.publisher_id
            LEFT JOIN review r ON r.book_id = b.book_id
    WHERE b.status = 1
    GROUP BY b.book_id, b.title, b.thumbnail,
            pr.base_price, pr.discount_price, p.name
    ), SALES_COUNT_MASTER AS (
        SELECT od.book_id, SUM(quantity) AS totalSalesCount
        FROM order_detail od
        GROUP BY od.book_id
    ), BOOK_CATEGORY_MASTER AS (
        SELECT bc.book_id,
            GROUP_CONCAT(ca.name ORDER BY ca.name SEPARATOR ', ') as categoryName
        FROM book_category bc
        INNER JOIN category ca ON ca.category_id = bc.category_id
        GROUP BY bc.book_id
    ), BOOK_AUTHOR_MASTER AS (
        SELECT ba.book_id,
               GROUP_CONCAT(a.name ORDER BY a.name SEPARATOR ', ') as authorName
        FROM book_author ba
        INNER JOIN author a ON a.author_id = ba.author_id
        GROUP BY ba.book_id
    )
    SELECT bm.book_id, bm.title, bm.thumbnail, bm.base_price, bm.discount_price, bm.publisherName,
           bm.averageRating, IFNULL(scm.totalSalesCount, 0) AS totalSalesCount, bam.authorName, bcm.categoryName
    FROM BOOK_MASTER bm
    LEFT JOIN SALES_COUNT_MASTER scm ON scm.book_id = bm.book_id
    INNER JOIN BOOK_AUTHOR_MASTER bam ON bam.book_id = bm.book_id
    INNER JOIN BOOK_CATEGORY_MASTER bcm ON bcm.book_id = bm.book_id
    ORDER BY totalSalesCount DESC
    LIMIT 10
    """, nativeQuery = true)
    Optional<List<ClientBookCard>> findTopSeller();
}
