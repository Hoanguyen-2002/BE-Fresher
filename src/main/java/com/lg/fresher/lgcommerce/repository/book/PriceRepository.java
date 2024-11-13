package com.lg.fresher.lgcommerce.repository.book;

import com.lg.fresher.lgcommerce.entity.book.Price;
import com.lg.fresher.lgcommerce.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends BaseRepository<Price, String> {
}
