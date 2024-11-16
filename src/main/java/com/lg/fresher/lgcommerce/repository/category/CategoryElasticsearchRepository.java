package com.lg.fresher.lgcommerce.repository.category;

import com.lg.fresher.lgcommerce.model.document.CategoryDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryElasticsearchRepository extends ElasticsearchRepository<CategoryDocument, String> {
    List<CategoryDocument> findByNameContainingIgnoreCase(String keyword);
}
