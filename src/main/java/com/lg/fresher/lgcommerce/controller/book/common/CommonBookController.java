package com.lg.fresher.lgcommerce.controller.book.common;

import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.service.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : CommonBookController
 * @ Description : lg_ecommerce_be CommonBookController
 * @ author lg_ecommerce_be Dev Team 63200504
 * @ since 11/6/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/6/2024       63200504      first creation */
@RestController
@RequestMapping("/api/v1/products")
@Validated
@RequiredArgsConstructor
public class CommonBookController {

    private final BookService bookService;

    @GetMapping("/cards")
    public CommonResponse<?> getAllBookCards(@RequestParam(name = "title", required = false) String title,
                                             @RequestParam(name = "publisher", required = false) String publisher,
                                             @RequestParam(name = "rating", required = false) Integer rating,
                                             @RequestParam(name = "minPrice", required = false) Double minPrice,
                                             @RequestParam(name = "maxPrice", required = false) Double maxPrice,
                                             @RequestParam(name = "authors", required = false) List<String> authors,
                                             @RequestParam(name = "categories", required = false) List<String> categories,
                                             @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                             @RequestParam(name = "size", required = false, defaultValue = "20") Integer size) {
        return bookService.getAllBookListByClient(title, publisher,
                rating, minPrice, maxPrice, authors, categories, page, size);
    }

    @GetMapping("/{id}")
    public CommonResponse<?> getBookDetail(@PathVariable("id") String id) {
        return bookService.getBookDetailByClient(id);
    }

}
