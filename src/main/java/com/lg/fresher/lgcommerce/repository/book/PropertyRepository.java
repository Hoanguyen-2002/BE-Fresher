package com.lg.fresher.lgcommerce.repository.book;

import com.lg.fresher.lgcommerce.entity.book.Property;
import com.lg.fresher.lgcommerce.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PropertyRepository extends BaseRepository<Property, String> {
    @Query(value = """
    SELECT p.* FROM property p 
    WHERE p.name IN (:names)
    """, nativeQuery = true)
    Optional<List<Property>> findByName(@Param("names") List<String> names);

}
