package com.lg.fresher.lgcommerce.controller.category.common;

import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.service.admin.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : CommonCategoryController
 * @ Description : lg_ecommerce_be CommonCategoryController
 * @ author lg_ecommerce_be Dev Team 63200504
 * @ since 11/19/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/19/2024       63200504      first creation */
@RestController
@RequestMapping("/api/v1/categories")
@Validated
@RequiredArgsConstructor
public class CommonCategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public CommonResponse<?> getAllCategories(){
        return categoryService.getListCategory();
    }

}
