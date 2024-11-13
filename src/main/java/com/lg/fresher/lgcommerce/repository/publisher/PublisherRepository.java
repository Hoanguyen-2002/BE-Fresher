package com.lg.fresher.lgcommerce.repository.publisher;

import com.lg.fresher.lgcommerce.entity.publisher.Publisher;
import com.lg.fresher.lgcommerce.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublisherRepository extends BaseRepository<Publisher, String> {

    Optional<Publisher> findByName(String name);

}
