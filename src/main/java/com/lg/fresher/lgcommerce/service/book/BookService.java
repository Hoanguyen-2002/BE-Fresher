package com.lg.fresher.lgcommerce.service.book;

import com.lg.fresher.lgcommerce.model.request.book.BookRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.StringResponse;
import com.lg.fresher.lgcommerce.model.response.book.BookResponse;

import java.util.List;
import java.util.Map;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Interface Name : BookService
 * @ Description : lg_ecommerce_be BookService
 * @ author lg_ecommerce_be Dev Team 63200504
 * @ since 11/6/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/6/2024       63200504      first creation */
public interface BookService {
    CommonResponse<Map<String, Object>> getAllBookListByClient(String title, String publisher, Integer rating, Double minPrice, Double maxPrice,
                                          List<String> authors, List<String> categories, Integer page, Integer size);

    CommonResponse<Map<String, Object>> getBookDetailByClient(String bookId);

    CommonResponse<StringResponse> addNewBook(BookRequest bookRequest);

    CommonResponse<StringResponse> updateBook(BookRequest bookRequest, String id);

}
