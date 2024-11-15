package com.lg.fresher.lgcommerce.repository.book;

import com.lg.fresher.lgcommerce.entity.book.BookCategory;
import com.lg.fresher.lgcommerce.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookCategoryRepository extends BaseRepository<BookCategory, String> {

    @Query(value = """
    SELECT bc.* FROM
    book_category bc
    WHERE bc.book_id = :book_id AND bc.category_id IN (:category_ids)
    """, nativeQuery = true)
    Optional<List<BookCategory>> findByIds(@Param("category_ids") List<String> categoryIds, @Param("book_id") String bookId);
}
