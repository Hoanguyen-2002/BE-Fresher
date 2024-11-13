package com.lg.fresher.lgcommerce.repository.author;

import com.lg.fresher.lgcommerce.entity.author.Author;
import com.lg.fresher.lgcommerce.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends BaseRepository<Author, String> {

    @Query(value = """
    SELECT a.* FROM author a 
    WHERE a.name IN (:names)
    """, nativeQuery = true)
    Optional<List<Author>> findAuthorsByNames(@Param("names") List<String> names);
}
