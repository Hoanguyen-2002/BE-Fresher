package com.lg.fresher.lgcommerce.service.admin.category;
import com.lg.fresher.lgcommerce.entity.category.Category;
import com.lg.fresher.lgcommerce.exception.DuplicateDataException;
import com.lg.fresher.lgcommerce.exception.data.DataNotFoundException;
import com.lg.fresher.lgcommerce.mapping.category.CategoryMapper;
import com.lg.fresher.lgcommerce.model.request.category.CategoryRequestDTO;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.admin.category.CategoryModel;
import com.lg.fresher.lgcommerce.model.response.admin.category.CategoryResponse;
import com.lg.fresher.lgcommerce.model.response.common.MetaData;
import com.lg.fresher.lgcommerce.repository.category.CategoryRepository;
import com.lg.fresher.lgcommerce.utils.UUIDUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import java.time.LocalDateTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;
    @InjectMocks
    private CategoryServiceImpl categoryService;
    private Category category;
    private CategoryModel categoryModel;
    private CategoryRequestDTO categoryRequestDTO;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        category = new Category();
        category.setCategoryId(UUID.randomUUID().toString());
        category.setName("Electronics");
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        categoryModel = new CategoryModel();
        categoryModel.setCategoryId(category.getCategoryId());
        categoryModel.setName(category.getName());
        categoryModel.setCreatedAt(category.getCreatedAt());
        categoryModel.setUpdatedAt(category.getUpdatedAt());
        categoryRequestDTO = new CategoryRequestDTO();
        categoryRequestDTO.setName("Electronics");
    }
    @Test
    public void testGetAllCategory_Success() {
        int pageNo = 1;
        int pageSize = 10;
        int offset = (pageNo - 1) * pageSize;
        List<Category> categories = Collections.singletonList(category);
        Page<Category> categoryPage = new PageImpl<>(categories, PageRequest.of(offset, pageSize), categories.size());
        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(categoryPage);
        when(categoryMapper.toModel(any(Category.class))).thenReturn(categoryModel);
        CommonResponse<CategoryResponse> response = categoryService.getAllCategory(pageNo, pageSize);
        assertNotNull(response);
        assertNotNull(response.getData());
        MetaData metadata = response.getData().getMetadata();
        assertEquals(categories.size(), metadata.getTotalElements());
        assertEquals(offset, metadata.getOffSet());
        assertEquals(pageSize, metadata.getLimit());
        assertEquals(1, response.getData().getContent().size());
        verify(categoryRepository, times(1)).findAll(any(Pageable.class));
        verify(categoryMapper, times(1)).toModel(any(Category.class));
    }
    @Test
    public void testGetAllCategory_Exception() {
        int pageNo = 1;
        int pageSize = 10;
        when(categoryRepository.findAll(any(Pageable.class))).thenThrow(new RuntimeException("Database error"));
        CommonResponse<CategoryResponse> response = categoryService.getAllCategory(pageNo, pageSize);
        assertNotNull(response);
        assertNull(response.getData());
        assertEquals("Failed to retrieve categories due to an internal error.",response);
        verify(categoryRepository, times(1)).findAll(any(Pageable.class));
    }
    @Test
    public void testGetCategoryById_Success() {
        String id = category.getCategoryId();
        try (MockedStatic<UUIDUtil> utilities = Mockito.mockStatic(UUIDUtil.class)) {
            utilities.when(() -> UUIDUtil.isValidUUID(id)).thenReturn(true);
            when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
            when(categoryMapper.toModel(category)).thenReturn(categoryModel);
            CommonResponse<Map<String, CategoryModel>> response = categoryService.getCategoryById(id);
            assertNotNull(response);
            assertNotNull(response.getData());
            assertEquals(categoryModel, response.getData().get("content"));
            verify(categoryRepository, times(1)).findById(id);
            verify(categoryMapper, times(1)).toModel(category);
        }
    }
    @Test
    public void testGetCategoryById_InvalidUUID() {
        String invalidId = "invalid-uuid";
        try (MockedStatic<UUIDUtil> utilities = Mockito.mockStatic(UUIDUtil.class)) {
            utilities.when(() -> UUIDUtil.isValidUUID(invalidId)).thenReturn(false);
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                categoryService.getCategoryById(invalidId);
            });
            assertEquals("ID không hợp lệ!", exception.getMessage());
            verify(categoryRepository, never()).findById(anyString());
        }
    }
    @Test
    public void testGetCategoryById_NotFound() {
        String id = category.getCategoryId();
        try (MockedStatic<UUIDUtil> utilities = Mockito.mockStatic(UUIDUtil.class)) {
            utilities.when(() -> UUIDUtil.isValidUUID(id)).thenReturn(true);
            when(categoryRepository.findById(id)).thenReturn(Optional.empty());
            DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
                categoryService.getCategoryById(id);
            });
            assertEquals("Không tìm thấy danh mục!", exception.getMessage());
            verify(categoryRepository, times(1)).findById(id);
        }
    }
    @Test
    public void testCreateCategory_Success() {
        when(categoryRepository.findByName(categoryRequestDTO.getName())).thenReturn(Optional.empty());
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(categoryMapper.toModel(any(Category.class))).thenReturn(categoryModel);
        CommonResponse<Map<String, CategoryModel>> response = categoryService.createCategory(categoryRequestDTO);
        assertNotNull(response);
        assertNotNull(response.getData());
        assertEquals(categoryModel, response.getData().get("content"));
        verify(categoryRepository, times(1)).findByName(categoryRequestDTO.getName());
        verify(categoryRepository, times(1)).save(any(Category.class));
        verify(categoryMapper, times(1)).toModel(any(Category.class));
    }
    @Test
    public void testCreateCategory_DuplicateName() {
        when(categoryRepository.findByName(categoryRequestDTO.getName())).thenReturn(Optional.of(category));
        DuplicateDataException exception = assertThrows(DuplicateDataException.class, () -> {
            categoryService.createCategory(categoryRequestDTO);
        });
        assertEquals("Danh mục với tên 'Electronics' đã tồn tại!", exception.getMessage());
        verify(categoryRepository, times(1)).findByName(categoryRequestDTO.getName());
        verify(categoryRepository, never()).save(any(Category.class));
    }
    @Test
    public void testUpdateCategory_Success() {
        String id = category.getCategoryId();
        categoryRequestDTO.setName("Updated Electronics");
        try (MockedStatic<UUIDUtil> utilities = Mockito.mockStatic(UUIDUtil.class)) {
            utilities.when(() -> UUIDUtil.isValidUUID(id)).thenReturn(true);
            when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
            when(categoryRepository.findByName(categoryRequestDTO.getName())).thenReturn(Optional.empty());
            when(categoryRepository.save(any(Category.class))).thenReturn(category);
            when(categoryMapper.toModel(any(Category.class))).thenReturn(categoryModel);
            CommonResponse<Map<String, CategoryModel>> response = categoryService.updateCategory(categoryRequestDTO, id);
            assertNotNull(response);
            assertNotNull(response.getData());
            assertEquals(categoryModel, response.getData().get("content"));
            verify(categoryRepository, times(1)).findById(id);
            verify(categoryRepository, times(1)).findByName(categoryRequestDTO.getName());
            verify(categoryRepository, times(1)).save(any(Category.class));
            verify(categoryMapper, times(1)).toModel(any(Category.class));
        }
    }
    @Test
    public void testUpdateCategory_InvalidUUID() {
        String invalidId = "invalid-uuid";
        try (MockedStatic<UUIDUtil> utilities = Mockito.mockStatic(UUIDUtil.class)) {
            utilities.when(() -> UUIDUtil.isValidUUID(invalidId)).thenReturn(false);
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                categoryService.updateCategory(categoryRequestDTO, invalidId);
            });
            assertEquals("ID không hợp lệ!", exception.getMessage());
            verify(categoryRepository, never()).findById(anyString());
        }
    }
    @Test
    public void testUpdateCategory_NotFound() {
        String id = category.getCategoryId();
        try (MockedStatic<UUIDUtil> utilities = Mockito.mockStatic(UUIDUtil.class)) {
            utilities.when(() -> UUIDUtil.isValidUUID(id)).thenReturn(true);
            when(categoryRepository.findById(id)).thenReturn(Optional.empty());
            DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
                categoryService.updateCategory(categoryRequestDTO, id);
            });
            assertEquals("Không tìm thấy danh mục!", exception.getMessage());
            verify(categoryRepository, times(1)).findById(id);
        }
    }
    @Test
    public void testUpdateCategory_DuplicateName() {
        String id = category.getCategoryId();
        Category duplicateCategory = new Category();
        duplicateCategory.setCategoryId(UUID.randomUUID().toString());
        duplicateCategory.setName("Electronics");
        try (MockedStatic<UUIDUtil> utilities = Mockito.mockStatic(UUIDUtil.class)) {
            utilities.when(() -> UUIDUtil.isValidUUID(id)).thenReturn(true);
            when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
            when(categoryRepository.findByName(categoryRequestDTO.getName())).thenReturn(Optional.of(duplicateCategory));
            DuplicateDataException exception = assertThrows(DuplicateDataException.class, () -> {
                categoryService.updateCategory(categoryRequestDTO, id);
            });
            assertEquals("Danh mục với tên 'Electronics' đã tồn tại!", exception.getMessage());
            verify(categoryRepository, times(1)).findById(id);
            verify(categoryRepository, times(1)).findByName(categoryRequestDTO.getName());
            verify(categoryRepository, never()).save(any(Category.class));
        }
    }
    @Test
    public void testSearchCategory_WithKeyword() {
        int pageNo = 1;
        int pageSize = 10;
        int offset = (pageNo - 1) * pageSize;
        String keyword = "Electronics";
        List<Category> categories = Collections.singletonList(category);
        Page<Category> categoryPage = new PageImpl<>(categories, PageRequest.of(offset, pageSize), categories.size());
        when(categoryRepository.searchByKeyword(eq(keyword), any(Pageable.class))).thenReturn(categoryPage);
        when(categoryMapper.toModel(any(Category.class))).thenReturn(categoryModel);
        CommonResponse<CategoryResponse> response = categoryService.searchCategory(keyword, pageNo, pageSize);
        assertNotNull(response);
        assertNotNull(response.getData());
        MetaData metadata = response.getData().getMetadata();
        assertEquals(categories.size(), metadata.getTotalElements());
        assertEquals(offset, metadata.getOffSet());
        assertEquals(pageSize, metadata.getLimit());
        assertEquals(1, response.getData().getContent().size());
        verify(categoryRepository, times(1)).searchByKeyword(eq(keyword), any(Pageable.class));
        verify(categoryMapper, times(1)).toModel(any(Category.class));
    }
    @Test
    public void testSearchCategory_WithoutKeyword() {
        int pageNo = 1;
        int pageSize = 10;
        int offset = (pageNo - 1) * pageSize;
        String keyword = null;
        List<Category> categories = Collections.singletonList(category);
        Page<Category> categoryPage = new PageImpl<>(categories, PageRequest.of(offset, pageSize), categories.size());
        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(categoryPage);
        when(categoryMapper.toModel(any(Category.class))).thenReturn(categoryModel);
        CommonResponse<CategoryResponse> response = categoryService.searchCategory(keyword, pageNo, pageSize);
        assertNotNull(response);
        assertNotNull(response.getData());
        MetaData metadata = response.getData().getMetadata();
        assertEquals(categories.size(), metadata.getTotalElements());
        assertEquals(offset, metadata.getOffSet());
        assertEquals(pageSize, metadata.getLimit());
        assertEquals(1, response.getData().getContent().size());
        verify(categoryRepository, times(1)).findAll(any(Pageable.class));
        verify(categoryMapper, times(1)).toModel(any(Category.class));
    }
}