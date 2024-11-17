package com.lg.fresher.lgcommerce.service.admin.category;

import com.lg.fresher.lgcommerce.model.request.category.CategoryRequest;
import com.lg.fresher.lgcommerce.model.request.category.CategoryRequestDTO;
import com.lg.fresher.lgcommerce.model.response.admin.category.CategoryModel;
import com.lg.fresher.lgcommerce.model.response.admin.category.CategoryResponse;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;

import java.util.Map;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Interface Name : CategoryService
 * @ Description : lg_ecommerce_be CategoryService
 * @ author lg_ecommerce_be Dev Team 63200485
 * @ since 11/6/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/6/2024       63200485      first creation */
public interface CategoryService {
    CommonResponse<CategoryResponse> getAllCategory(int pageNo, int pageSize);
    CommonResponse<Map<String,CategoryModel>> getCategoryById(String id);
    CommonResponse <Map<String,CategoryModel>> createCategory(CategoryRequestDTO categoryRequestDTO);
    CommonResponse<Map<String,CategoryModel>> updateCategory(CategoryRequestDTO categoryRequestDTO , String id);
    CommonResponse<CategoryResponse> searchCategory(String keyword, int pageNo, int pageSize);

}
