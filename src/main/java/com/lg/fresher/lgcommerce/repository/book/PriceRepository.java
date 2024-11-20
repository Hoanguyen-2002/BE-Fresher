package com.lg.fresher.lgcommerce.repository.book;

import com.lg.fresher.lgcommerce.entity.book.Price;
import com.lg.fresher.lgcommerce.model.response.checkout.CheckoutItemResponse;
import com.lg.fresher.lgcommerce.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Interface Name : PriceRepository
 * @ Description : lg_ecommerce_be PriceRepository
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/19/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/19/2024       63200502      first creation */
@Repository
public interface PriceRepository extends BaseRepository<Price, String> {
    /**
     *
     * @ Description : lg_ecommerce_be PriceRepository Member Field captureListBookPrice
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/19/2024           63200502    first creation
     *<pre>
     * @param listBookId
     * @return  List<CheckoutItemResponse>
     */
    @Query(value = """
            SELECT new com.lg.fresher.lgcommerce.model.response.checkout.CheckoutItemResponse(
                b.bookId, b.title, b.thumbnail, p.basePrice, p.discountPrice)
            FROM Price p
            RIGHT JOIN Book b
            ON p.book.bookId = b.bookId
            WHERE p.book.bookId IN :bookIds
            """)
    List<CheckoutItemResponse> captureListBookPrice(@Param("bookIds") List<String> listBookId);
}
