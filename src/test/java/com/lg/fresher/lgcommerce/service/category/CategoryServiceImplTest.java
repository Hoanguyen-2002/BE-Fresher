package com.lg.fresher.lgcommerce.service.category;

import com.lg.fresher.lgcommerce.entity.category.Category;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.admin.category.CategoryResponse;
import com.lg.fresher.lgcommerce.repository.category.CategoryRepository;
import com.lg.fresher.lgcommerce.service.admin.category.CategoryService;
import com.lg.fresher.lgcommerce.service.admin.category.CategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;

public class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getListCategory_successfully() {
        List<Category> mockCategoryList = List.of(Category.builder()
                .categoryId("valid 1")
                .name("valid 1")
                .build(), Category.builder()
                .categoryId("valid 2")
                .name("valid 2")
                .build());

        Mockito.when(categoryRepository.findAll()).thenReturn(mockCategoryList);

        CommonResponse<Map<String, Object>> response = categoryService.getListCategory();

        assertNotNull(response);
        assertNotNull(response.getData().get("content"));

        List<CategoryResponse> content = (List<CategoryResponse>) response.getData().get("content");
        assertEquals(2, content.size());
    }

    @Test
    public void getListCategory_successfully_noresult() {

        Mockito.when(categoryRepository.findAll()).thenReturn(Collections.emptyList());

        CommonResponse<Map<String, Object>> response = categoryService.getListCategory();

        assertNotNull(response);
        assertNotNull(response.getData().get("content"));

        List<CategoryResponse> content = (List<CategoryResponse>) response.getData().get("content");
        assertEquals(0, content.size());
    }

}
