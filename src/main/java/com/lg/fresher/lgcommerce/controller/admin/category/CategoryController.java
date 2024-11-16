package com.lg.fresher.lgcommerce.controller.admin.category;

import com.lg.fresher.lgcommerce.model.request.category.CategoryRequest;
import com.lg.fresher.lgcommerce.model.request.category.CategoryRequestDTO;
import com.lg.fresher.lgcommerce.model.response.admin.category.CategoryModel;
import com.lg.fresher.lgcommerce.model.response.admin.category.CategoryResponse;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.service.admin.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : CategoryController
 * @ Description : lg_ecommerce_be CategoryController
 * @ author lg_ecommerce_be Dev Team 63200485
 * @ since 11/6/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/6/2024       63200485      first creation */
@RestController
@RequestMapping("/api/v1/admin/categories")
public class CategoryController {
    private final CategoryService categoryService;

    /**
     *
     * @ Description : lg_ecommerce_be CategoryController constructor
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/6/2024         63200485    first creation
     *<pre>

     field:

     * @ Description : lg_ecommerce_be CategoryController Member Field
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/6/2024          63200485    first creation
     *<pre>     */
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    /**
     *
     * @ Description : lg_ecommerce_be CategoryController Member Field getAllCategories
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/6/2024           63200485    first creation
     *<pre>
     * @param pageNo
     * @param pageSize
     * @return  ResponseEntity<CommonResponse<CategoryResponse>>
     */
    @GetMapping("")
    public ResponseEntity<CommonResponse<CategoryResponse>> getAllCategories(
            @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ){
        return new ResponseEntity<>(categoryService.getAllCategory(pageNo,pageSize), HttpStatus.OK);
    }

    /**
     *
     * @ Description : lg_ecommerce_be CategoryController Member Field getCategoryDetail
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/6/2024           63200485    first creation
     *<pre>
     * @param id
     * @return  ResponseEntity<CommonResponse<CategoryModel>>
     */
    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<Map<String, CategoryModel>>> getCategoryDetail(@PathVariable String id){
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    /**
     *
     * @ Description : lg_ecommerce_be CategoryController Member Field createCategory
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/6/2024           63200485    first creation
     *<pre>
     * @param categoryRequestDTO
     * @return  ResponseEntity<CommonResponse<CategoryModel>>
     */
    @PostMapping("")
    public ResponseEntity<CommonResponse<Map<String, CategoryModel>>> createCategory(@RequestBody CategoryRequestDTO categoryRequestDTO) {
        return new ResponseEntity<>(categoryService.createCategory(categoryRequestDTO),HttpStatus.CREATED);
    }

    /**
     *
     * @ Description : lg_ecommerce_be CategoryController Member Field updatePokemon
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/6/2024           63200485    first creation
     *<pre>
     * @param categoryId
     * @param categoryRequestDTO
     * @return  ResponseEntity<CommonResponse<CategoryModel>>
     */
    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<Map<String, CategoryModel>>> updateCategory(@PathVariable("id") String categoryId, @RequestBody CategoryRequestDTO categoryRequestDTO){
        return ResponseEntity.ok(categoryService.updateCategory(categoryRequestDTO, categoryId));
    }

//    /**
//     *
//     * @ Description : lg_ecommerce_be CategoryController Member Field searchCategories
//     *<pre>
//     * Date of Revision Modifier Revision
//     * ---------------  ---------   -----------------------------------------------
//     * 11/6/2024           63200485    first creation
//     *<pre>
//     * @param keyword
//     * @return  ResponseEntity<CommonResponse<List<CategoryModel>>>
//     */
//    @GetMapping("/search")
//    public ResponseEntity<CommonResponse<CategoryResponse>> searchCategories(
//            @RequestParam(value = "keyword", required = false) String keyword,
//            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
//            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
//        return ResponseEntity.ok(categoryService.searchCategory(keyword, pageNo, pageSize));
//    }
    @GetMapping("/search")
    public ResponseEntity<CommonResponse<CategoryResponse>> searchCategories(
        @RequestParam(value = "keyword", required = false) String keyword,
        @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
        @RequestParam(value = "useElasticsearch", defaultValue = "true") boolean useElasticsearch) {

    return ResponseEntity.ok(categoryService.searchCategoryInElasticsearch(keyword, pageNo, pageSize));
    }

    @PostMapping("/sync-elasticsearch")
    public ResponseEntity<String> syncDataToElasticsearch() {
        categoryService.syncDatabaseToElasticsearch();
        return ResponseEntity.ok("Data synchronized to Elasticsearch.");
    }
}
