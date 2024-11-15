package com.lg.fresher.lgcommerce.controller.book.admin;


import com.lg.fresher.lgcommerce.model.request.book.BookRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.service.book.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/products")
@Validated
@RequiredArgsConstructor
public class AdminBookController {
    private final BookService bookService;

    @PostMapping("/create")
    public CommonResponse<?> addBook(@RequestBody @Valid BookRequest request) {
        return bookService.addNewBook(request);
    }

    @PutMapping("/update/{id}")
    public CommonResponse<?> updateBook(@PathVariable("id") String id, @RequestBody @Valid BookRequest request) {
        return bookService.updateBook(request, id);
    }

}
