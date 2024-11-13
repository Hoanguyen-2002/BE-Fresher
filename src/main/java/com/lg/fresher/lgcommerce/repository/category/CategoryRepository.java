package com.lg.fresher.lgcommerce.repository.category;

import com.lg.fresher.lgcommerce.entity.category.Category;
import com.lg.fresher.lgcommerce.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Interface Name : CategoryRepository
 * @ Description : lg_ecommerce_be CategoryRepository
 * @ author lg_ecommerce_be Dev Team 63200485
 * @ since 11/6/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/6/2024       63200485      first creation */
@Repository
public interface CategoryRepository extends BaseRepository<Category, String> {
    @Query(value = "SELECT * FROM category WHERE LOWER(name COLLATE utf8mb4_vietnamese_ci) LIKE LOWER(CONCAT('%', :keyword, '%')) COLLATE utf8mb4_vietnamese_ci", nativeQuery = true)
    Page<Category> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    Optional<Category> findByName(String name);

    @Query(value = """
    SELECT *
    FROM category ca
    WHERE ca.category_id IN :ids;
    """, nativeQuery = true)
    Optional<List<Category>> findCategoryByListIds(@Param("ids") List<String> ids);
}
