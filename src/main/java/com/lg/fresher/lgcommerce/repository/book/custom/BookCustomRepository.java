package com.lg.fresher.lgcommerce.repository.book.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : BookCustomRepository
 * @ Description : lg_ecommerce_be BookCustomRepository
 * @ author lg_ecommerce_be Dev Team 63200504
 * @ since 11/20/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/20/2024       63200504      first creation */
@Repository
@RequiredArgsConstructor
public class BookCustomRepository {

    private final EntityManager entityManager;


    /**
     *
     * @ Description : lg_ecommerce_be BookCustomRepository Member Field getBookCard
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/20/2024           63200504    first creation
     *<pre>
     * @param title
     * @param publisher
     * @param rating
     * @param minPrice
     * @param maxPrice
     * @param authors
     * @param categories
     * @param status
     * @param pageable
     * @return  Map<String, Object>
     */
    public Map<String, Object> getBookCard(String title,
                                           String publisher,
                                           Integer rating,
                                           Double minPrice,
                                           Double maxPrice,
                                           List<String> authors,
                                           List<String> categories,
                                           Boolean status,
                                           Pageable pageable) {
        // Kiểm tra title có chứa ký tự tiếng Việt có dấu hay không
        boolean isTitleVietnamese = containsDiacritics(title);

        StringBuilder queryString = new StringBuilder("WITH BOOK_MASTER AS (\n" +
                "                                    SELECT b.book_id, b.title, b.thumbnail, COALESCE(AVG(r.rating), 0) AS averageRating,\n" +
                "                                          pr.base_price, pr.discount_price, p.name AS publisherName, b.created_at, (pr.base_price - pr.discount_price) AS sellingPrice\n" +
                "                                    FROM book b\n" +
                "                                            LEFT JOIN price pr ON pr.book_id = b.book_id\n" +
                "                                            LEFT JOIN publisher p ON b.publisher_id = p.publisher_id\n" +
                "                                            LEFT JOIN review r ON r.book_id = b.book_id\n" +
                (isTitleVietnamese ?
                        "                                    WHERE (:title IS NULL OR BINARY LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%')) )\n" :
                        "                                    WHERE (:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%')) )\n")+
                "                                    AND (:publisher IS NULL OR p.publisher_id = :publisher)\n" +
                "                                    AND (:minPrice IS NULL OR pr.base_price >= :minPrice)\n" +
                "                                    AND (:maxPrice IS NULL OR pr.base_price <= :maxPrice)\n" +
                "                                    AND b.status = :status\n" +
                "                                    GROUP BY b.book_id, b.title, b.thumbnail,\n" +
                "                                            pr.base_price, pr.discount_price, p.name, b.created_at\n" +
                "                                    HAVING (:rating IS NULL OR FLOOR(COALESCE(AVG(r.rating), 0)) = :rating)\n" +
                "                                ), SALES_COUNT_MASTER AS (\n" +
                "                                    SELECT od.book_id, SUM(quantity) AS totalSalesCount\n" +
                "                                    FROM order_detail od\n" +
                "                                    GROUP BY od.book_id\n" +
                "                                ), BOOK_CATEGORY_MASTER AS (\n" +
                "                                    SELECT bc.book_id,\n" +
                "                                        GROUP_CONCAT(ca.name ORDER BY ca.name SEPARATOR ', ') as categoryName\n" +
                "                                    FROM book_category bc\n" +
                "                                    INNER JOIN category ca ON ca.category_id = bc.category_id\n" +
                "                                    WHERE (:categories IS NULL OR bc.category_id IN (?1))\n" +
                "                                    GROUP BY bc.book_id\n" +
                "                                ), BOOK_AUTHOR_MASTER AS (\n" +
                "                                    SELECT ba.book_id,\n" +
                "                                           GROUP_CONCAT(a.name ORDER BY a.name SEPARATOR ', ') as authorName\n" +
                "                                    FROM book_author ba\n" +
                "                                    INNER JOIN author a ON a.author_id = ba.author_id\n" +
                "                                    WHERE (:authors IS NULL OR ba.author_id IN (?2))\n" +
                "                                    GROUP BY ba.book_id\n" +
                "                                )\n" +
                "                                SELECT\n" +
                "                                    bm.book_id AS bookId,\n" +
                "                                    bm.title AS title,\n" +
                "                                    bm.base_price AS basePrice,\n" +
                "                                    bm.thumbnail AS thumbnail,\n" +
                "                                    bm.discount_price AS discountPrice,\n" +
                "                                    bm.publisherName AS publisherName,\n" +
                "                                    bm.sellingPrice AS sellingPrice,\n" +
                "                                    bm.averageRating AS averageRating,\n" +
                "                                    IFNULL(scm.totalSalesCount, 0) AS totalSalesCount,\n" +
                "                                    bam.authorName AS authorName,\n" +
                "                                    bcm.categoryName AS categoryName\n" +
                "                                FROM BOOK_MASTER bm\n" +
                "                                LEFT JOIN SALES_COUNT_MASTER scm ON scm.book_id = bm.book_id\n" +
                "                                INNER JOIN BOOK_AUTHOR_MASTER bam ON bam.book_id = bm.book_id\n" +
                "                                INNER JOIN BOOK_CATEGORY_MASTER bcm ON bcm.book_id = bm.book_id");

        String countString = "WITH BOOK_MASTER AS (\n" +
                "                SELECT b.book_id, b.title, b.thumbnail, COALESCE(AVG(r.rating), 0) AS averageRating,\n" +
                "                      pr.base_price, pr.discount_price, p.name AS publisherName, b.created_at\n" +
                "                FROM book b\n" +
                "                        LEFT JOIN price pr ON pr.book_id = b.book_id\n" +
                "                        LEFT JOIN publisher p ON b.publisher_id = p.publisher_id\n" +
                "                        LEFT JOIN review r ON r.book_id = b.book_id\n" +
                (isTitleVietnamese ?
                        "                                    WHERE (:title IS NULL OR BINARY LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%')) )\n" :
                        "                                    WHERE (:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%')) )\n")+
                "                AND (:publisher IS NULL OR p.publisher_id = :publisher)\n" +
                "                AND (:minPrice IS NULL OR pr.base_price >= :minPrice)\n" +
                "                AND (:maxPrice IS NULL OR pr.base_price <= :maxPrice)\n" +
                "                AND b.status = :status\n" +
                "                GROUP BY b.book_id, b.title, b.thumbnail,\n" +
                "                        pr.base_price, pr.discount_price, p.name, b.created_at\n" +
                "                HAVING (:rating IS NULL OR FLOOR(COALESCE(AVG(r.rating), 0)) = :rating)\n" +
                "            ), BOOK_CATEGORY_MASTER AS (\n" +
                "                SELECT DISTINCT bc.book_id\n" +
                "                FROM book_category bc\n" +
                "                WHERE (:categories IS NULL OR bc.category_id IN (?1))\n" +
                "            ), BOOK_AUTHOR_MASTER AS (\n" +
                "                SELECT DISTINCT ba.book_id\n" +
                "                FROM book_author ba\n" +
                "                WHERE (:authors IS NULL OR ba.author_id IN (?2))\n" +
                "            )\n" +
                "            SELECT COUNT(*)\n" +
                "            FROM BOOK_MASTER bm\n" +
                "            INNER JOIN BOOK_AUTHOR_MASTER bam ON bam.book_id = bm.book_id\n" +
                "            INNER JOIN BOOK_CATEGORY_MASTER bcm ON bcm.book_id = bm.book_id";

        // Handle sorting
        queryString.append(" ORDER BY ");
        String orderByClause = pageable.getSort().stream()
                .map(order -> order.getProperty() + " " + order.getDirection().name())
                .collect(Collectors.joining(", "));
        queryString.append(orderByClause);

        Query query = entityManager.createNativeQuery(queryString.toString(), Tuple.class);
        Query count = entityManager.createNativeQuery(countString);
        this.buildParams(query, title, publisher, rating, minPrice, maxPrice, authors, categories, status);
        query.setFirstResult(pageable.getPageNumber());
        query.setMaxResults(pageable.getPageSize());
        this.buildParams(count, title, publisher, rating, minPrice, maxPrice, authors, categories, status);
        List<Tuple> data = query.getResultList();
        long total = (Long) count.getSingleResult();
        return Map.of(
                "data", this.convert(data, BookCard.class),
                "total", total
        );
    }

    /**
     *
     * @param input
     * @return
     */
    private boolean containsDiacritics(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);

        Pattern diacriticsPattern = Pattern.compile("\\p{M}");

        return diacriticsPattern.matcher(normalized).find();
    }

    /**
     *
     * @ Description : lg_ecommerce_be BookCustomRepository Member Field buildParams
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/20/2024           63200504    first creation
     *<pre>
     * @param query
     * @param title
     * @param publisher
     * @param rating
     * @param minPrice
     * @param maxPrice
     * @param authors
     * @param categories
     * @param status
     */
    private void buildParams(Query query,
                             String title,
                             String publisher,
                             Integer rating,
                             Double minPrice,
                             Double maxPrice,
                             List<String> authors,
                             List<String> categories,
                             Boolean status) {
        query.setParameter("title", title);
        query.setParameter("publisher", publisher);
        query.setParameter("rating", rating);
        query.setParameter("minPrice", minPrice);
        query.setParameter("maxPrice", maxPrice);
        if (authors == null || authors.isEmpty()) {
            query.setParameter("authors", null);
            query.setParameter(2, null);
        } else {
            query.setParameter("authors", 1);
            query.setParameter(2, authors);
        }
        if (categories == null || categories.isEmpty()) {
            query.setParameter("categories", null);
            query.setParameter(1, null);
        } else {
            query.setParameter("categories", 1);
            query.setParameter(1, categories);
        }
        query.setParameter("status", status);
    }

    /**
     *
     * @ Description : lg_ecommerce_be BookCustomRepository Member Field convert
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/20/2024           63200504    first creation
     *<pre>
     * @param data
     * @param clazz
     * @return  List<T>
     * @param <T>
     */
    private <T> List<T> convert(List<Tuple> data, Class<T> clazz){
        ObjectMapper objectMapper = new ObjectMapper();
        return data.stream().map(e -> {
            var maps = new HashMap<String, Object>();
            e.getElements().forEach(te -> maps.put(te.getAlias(), e.get(te.getAlias())));
            return objectMapper.convertValue(maps, clazz);
        }).toList();
    }
}