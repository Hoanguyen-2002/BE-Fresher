package com.lg.fresher.lgcommerce.service.admin.category;

import com.lg.fresher.lgcommerce.entity.category.Category;
import com.lg.fresher.lgcommerce.exception.data.DataNotFoundException;
import com.lg.fresher.lgcommerce.exception.DuplicateDataException;
import com.lg.fresher.lgcommerce.mapping.category.CategoryMapper;
import com.lg.fresher.lgcommerce.model.request.category.CategoryRequestDTO;
import com.lg.fresher.lgcommerce.model.response.admin.category.CategoryModel;
import com.lg.fresher.lgcommerce.model.response.admin.category.CategoryResponse;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.common.MetaData;
import com.lg.fresher.lgcommerce.repository.category.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.lg.fresher.lgcommerce.utils.UUIDUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : CategoryServiceImpl
 * @ Description : lg_ecommerce_be CategoryServiceImpl
 * @ author lg_ecommerce_be Dev Team 63200485
 * @ since 11/6/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/6/2024       63200485      first creation */
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    /**
     *
     * @ Description : lg_ecommerce_be CategoryServiceImpl constructor
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/6/2024         63200485    first creation
     *<pre>

     field:

     * @ Description : lg_ecommerce_be CategoryServiceImpl Member Field
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/6/2024          63200485    first creation
     *<pre>     */
    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }


    /**
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public CommonResponse<CategoryResponse> getAllCategory(int pageNo, int pageSize) {
        try {
            pageNo = Math.max(0, pageNo - 1);
            Pageable pageable = PageRequest.of(pageNo, pageSize);
            Page<Category> categoriesPage = categoryRepository.findAll(pageable);
            List<Category> listOfCategory = categoriesPage.getContent();

            List<CategoryModel> content = listOfCategory.stream().map(categoryMapper::toModel).toList();

            MetaData metadata = new MetaData(
                    categoriesPage.getTotalElements(),
                    pageNo + 1 ,
                    pageSize
            );

            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setMetadata(metadata);
            categoryResponse.setContent(content);

            return new CommonResponse<>(categoryResponse);
        } catch (Exception ex){
            logger.error("Failed to retrieve categories: {}", ex.getMessage());
            return CommonResponse.fail("Failed to retrieve categories due to an internal error.",4004,null);
        }
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public CommonResponse<Map<String, CategoryModel>> getCategoryById(String id) {
        if (!UUIDUtil.isValidUUID(id)) {
            throw new IllegalArgumentException("ID không hợp lệ!");
        }

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy danh mục!"));

        Map<String, CategoryModel> content = Map.of("content", categoryMapper.toModel(category));

        return new CommonResponse<>(content);
    }

    /**
     *
     * @param categoryRequestDTO
     * @return
     */
    @Override
    public CommonResponse<Map<String,CategoryModel>> createCategory(CategoryRequestDTO categoryRequestDTO) {
        if (categoryRepository.findByName(categoryRequestDTO.getName()).isPresent()) {
            throw new DuplicateDataException("Danh mục với tên '" + categoryRequestDTO.getName() + "' đã tồn tại!");
        }

        Category category = new Category();
        category.setName(categoryRequestDTO.getName());

        Category newCategory = categoryRepository.save(category);
        Map<String, CategoryModel> content = Map.of("content", categoryMapper.toModel(newCategory));
        return new CommonResponse<>(content);
    }

    /**
     *
     * @param categoryRequestDTO
     * @param id
     * @return
     */
    @Override
    public CommonResponse<Map<String,CategoryModel>> updateCategory(CategoryRequestDTO categoryRequestDTO, String id) {
        if (!UUIDUtil.isValidUUID(id)) {
            throw new IllegalArgumentException("ID không hợp lệ!");
        }

        Category category = categoryRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Không tìm thấy danh mục!"));

        Optional<Category> duplicateCategory = categoryRepository.findByName(categoryRequestDTO.getName());
        if (duplicateCategory.isPresent() && !duplicateCategory.get().getCategoryId().equals(id)) {
            throw new DuplicateDataException("Danh mục với tên '" + categoryRequestDTO.getName() + "' đã tồn tại!");
        }
        category.setName(categoryRequestDTO.getName());
        category.setUpdatedAt(LocalDateTime.now());

        Category updateCategory = categoryRepository.save(category);
        Map<String, CategoryModel> content = Map.of("content", categoryMapper.toModel(updateCategory));
        return new CommonResponse<>(content);
    }

    /**
     *
     * @param keyword
     * @return
     */
    @Override
    public CommonResponse<CategoryResponse> searchCategory(String keyword, int pageNo, int pageSize) {
        pageNo = Math.max(0, pageNo - 1);
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Category> categoriesPage;

        if (keyword == null || keyword.trim().isEmpty()) {
            categoriesPage = categoryRepository.findAll(pageable);
        } else {
            categoriesPage = categoryRepository.searchByKeyword(keyword, pageable);
        }
        List<CategoryModel> content = categoriesPage.getContent().stream()
                .map(categoryMapper::toModel)
                .toList();

        MetaData metadata = new MetaData(
                categoriesPage.getTotalElements(),
                pageNo + 1 ,
                pageSize
        );

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(content);
        categoryResponse.setMetadata(metadata);
        return new CommonResponse<>(categoryResponse);
    }

    /**
     *
     * @return
     */
    @Override
    public CommonResponse<Map<String, Object>> getListCategory() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryResponse> res = categories.stream()
                .map(category -> CategoryResponse.builder()
                        .categoryId(category.getCategoryId())
                        .name(category.getName())
                        .build())
                .toList();
        return CommonResponse.success(Map.of("content", res));
    }
}