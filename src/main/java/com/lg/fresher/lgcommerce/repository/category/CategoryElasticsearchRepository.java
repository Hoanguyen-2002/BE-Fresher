package com.lg.fresher.lgcommerce.repository.category;

import com.lg.fresher.lgcommerce.model.document.CategoryDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryElasticsearchRepository extends ElasticsearchRepository<CategoryDocument, String> {

    // Tìm kiếm danh mục bằng từ khóa với phân trang
    @Query("""
    {
           "match": {
               "name": {
                  "query": "?0",
                  "operator": "and",
                  "fuzziness": "AUTO"
                }
            }
        }
    """)
    Page<CategoryDocument> findByKeyword(String keyword, Pageable pageable);

    // Lấy tất cả danh mục với phân trang
    Page<CategoryDocument> findAll(Pageable pageable);
}
