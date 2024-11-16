package com. lg. fresher. lgcommerce. service. admin. category;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.lg.fresher.lgcommerce.entity.category.Category;
import com.lg.fresher.lgcommerce.exception.DuplicateDataException;
import com.lg.fresher.lgcommerce.exception.data.DataNotFoundException;
import com.lg.fresher.lgcommerce.mapping.category.CategoryMapper;
import com.lg.fresher.lgcommerce.model.request.category.CategoryRequestDTO;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.admin.category.CategoryModel;
import com.lg.fresher.lgcommerce.model.response.admin.category.CategoryResponse;
import com.lg.fresher.lgcommerce.repository.category.CategoryRepository;
import com.lg.fresher.lgcommerce.utils.UUIDUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import java.util.*;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : CategoryServiceImplTest
 * @ Description : lg_ecommerce_be CategoryServiceImplTest
 * @ author lg_ecommerce_be Dev Team 63200485
 * @ since 11/13/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/13/2024       63200485      first creation */
@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;
    @InjectMocks
    private CategoryServiceImpl categoryServiceImpl;
    private Category category;
    private CategoryModel categoryModel;
    private CategoryRequestDTO categoryRequestDTO;
    @BeforeEach
    void setUp() {
        category = new Category();
        category.setCategoryId(UUIDUtil.generateId());
        category.setName("Test Category");
        categoryModel = new CategoryModel(category.getCategoryId(), category.getName(), null, null, null, null);
        categoryRequestDTO = new CategoryRequestDTO();
        categoryRequestDTO.setName("Test Category");
    }


    @Test
    void testGetAllCategory() {
        List<Category> categories = List.of(category);
        Page<Category> categoriesPage = new PageImpl<>(categories);
        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(categoriesPage);
        when(categoryMapper.toModel(any(Category.class))).thenReturn(categoryModel);
        CommonResponse<CategoryResponse> response = categoryServiceImpl.getAllCategory(1, 10);
        assertNotNull(response);
        assertEquals(1, response.getData().getContent().size());
        verify(categoryRepository, times(1)).findAll(any(Pageable.class));
    }


    @Test
    void testGetCategoryById() {
        when(categoryRepository.findById(anyString())).thenReturn(Optional.of(category));
        when(categoryMapper.toModel(any(Category.class))).thenReturn(categoryModel);
        CommonResponse<Map<String, CategoryModel>> response = categoryServiceImpl.getCategoryById(category.getCategoryId());
        assertNotNull(response);
        assertEquals(category.getCategoryId(), response.getData().get("content").getCategoryId());
        verify(categoryRepository, times(1)).findById(anyString());
    }


    @Test
    void testGetCategoryById_NotFound() {
        when(categoryRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> categoryServiceImpl.getCategoryById(category.getCategoryId()));
        verify(categoryRepository, times(1)).findById(anyString());
    }


    @Test
    void testCreateCategory() {
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(categoryMapper.toModel(any(Category.class))).thenReturn(categoryModel);
        CommonResponse<Map<String, CategoryModel>> response = categoryServiceImpl.createCategory(categoryRequestDTO);
        assertNotNull(response);
        assertEquals(categoryRequestDTO.getName(), response.getData().get("content").getName());
        verify(categoryRepository, times(1)).findByName(anyString());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }


    @Test
    void testCreateCategory_Duplicate() {
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(category));
        assertThrows(DuplicateDataException.class, () -> categoryServiceImpl.createCategory(categoryRequestDTO));
        verify(categoryRepository, times(1)).findByName(anyString());
    }


    @Test
    void testUpdateCategory() {
        when(categoryRepository.findById(anyString())).thenReturn(Optional.of(category));
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(categoryMapper.toModel(any(Category.class))).thenReturn(categoryModel);
        CommonResponse<Map<String, CategoryModel>> response = categoryServiceImpl.updateCategory(categoryRequestDTO, category.getCategoryId());
        assertNotNull(response);
        assertEquals(categoryRequestDTO.getName(), response.getData().get("content").getName());
        verify(categoryRepository, times(1)).findById(anyString());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }


    @Test
    void testUpdateCategory_Duplicate() {
        Category anotherCategory = new Category();
        anotherCategory.setCategoryId(UUIDUtil.generateId());
        when(categoryRepository.findById(anyString())).thenReturn(Optional.of(category));
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(anotherCategory));
        assertThrows(DuplicateDataException.class, () -> categoryServiceImpl.updateCategory(categoryRequestDTO, category.getCategoryId()));
        verify(categoryRepository, times(1)).findById(anyString());
        verify(categoryRepository, times(1)).findByName(anyString());
    }


//    @Test
//    void testSearchCategory_WithKeyword() {
//        List<Category> categories = List.of(category);
//        Page<Category> categoriesPage = new PageImpl<>(categories);
//        when(categoryRepository.searchByKeyword(anyString(), any(Pageable.class))).thenReturn(categoriesPage);
//        when(categoryMapper.toModel(any(Category.class))).thenReturn(categoryModel);
//        CommonResponse<CategoryResponse> response = categoryServiceImpl.searchCategory("Test", 1, 10);
//        assertNotNull(response);
//        assertEquals(1, response.getData().getContent().size());
//        verify(categoryRepository, times(1)).searchByKeyword(anyString(), any(Pageable.class));
//    }
//
//
//    @Test
//    void testSearchCategory_WithoutKeyword() {
//        List<Category> categories = List.of(category);
//        Page<Category> categoriesPage = new PageImpl<>(categories);
//        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(categoriesPage);
//        when(categoryMapper.toModel(any(Category.class))).thenReturn(categoryModel);
//        CommonResponse<CategoryResponse> response = categoryServiceImpl.searchCategory(null, 1, 10);
//        assertNotNull(response);
//        assertEquals(1, response.getData().getContent().size());
//        verify(categoryRepository, times(1)).findAll(any(Pageable.class));
//    }
}