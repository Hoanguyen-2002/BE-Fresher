package com.lg.fresher.lgcommerce.service.book;

import com.lg.fresher.lgcommerce.entity.author.Author;
import com.lg.fresher.lgcommerce.entity.book.*;
import com.lg.fresher.lgcommerce.entity.category.Category;
import com.lg.fresher.lgcommerce.entity.publisher.Publisher;
import com.lg.fresher.lgcommerce.exception.DuplicateDataException;
import com.lg.fresher.lgcommerce.exception.data.DataNotFoundException;
import com.lg.fresher.lgcommerce.exception.data.DataNotNullException;
import com.lg.fresher.lgcommerce.model.request.author.AuthorRequest;
import com.lg.fresher.lgcommerce.model.request.book.BookImageRequest;
import com.lg.fresher.lgcommerce.model.request.book.BookRequest;
import com.lg.fresher.lgcommerce.model.request.book.PriceRequest;
import com.lg.fresher.lgcommerce.model.request.category.CategoryRequest;
import com.lg.fresher.lgcommerce.model.request.property.PropertyRequest;
import com.lg.fresher.lgcommerce.model.request.publisher.PublisherRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.StringResponse;
import com.lg.fresher.lgcommerce.model.response.author.AuthorResponse;
import com.lg.fresher.lgcommerce.model.response.book.BookCardResponse;
import com.lg.fresher.lgcommerce.model.response.book.BookResponse;
import com.lg.fresher.lgcommerce.model.response.book_image.BookImageResponse;
import com.lg.fresher.lgcommerce.model.response.admin.category.CategoryDTO;
import com.lg.fresher.lgcommerce.model.response.property.PropertyResponse;
import com.lg.fresher.lgcommerce.repository.author.AuthorRepository;
import com.lg.fresher.lgcommerce.repository.book.*;
import com.lg.fresher.lgcommerce.model.response.book.itf.ClientBookCard;
import com.lg.fresher.lgcommerce.repository.category.CategoryRepository;
import com.lg.fresher.lgcommerce.repository.publisher.PublisherRepository;
import com.lg.fresher.lgcommerce.utils.JsonUtils;
import io.netty.util.internal.ObjectUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PublisherRepository publisherRepository;

    @Mock
    private PriceRepository priceRepository;

    @Mock
    private BookImageRepository bookImageRepository;

    @Mock
    private BookCategoryRepository bookCategoryRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookAuthorRepository bookAuthorRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private BookPropertyRepository bookPropertyRepository;

    @Mock
    private JsonUtils jsonUtils;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //test get list book by client when have result
    @Test
    void testGetAllBookListByClient_WithResults() {
        // Arrange
        String title = "Test Title";
        String publisher = "Test Publisher";
        Integer rating = 5;
        Double minPrice = 10.0;
        Double maxPrice = 50.0;
        List<String> authors = List.of("Author1", "Author2");
        List<String> categories = List.of("Category1", "Category2");
        Integer page = 0;
        Integer size = 10;

        ClientBookCard bookCard = Mockito.mock(ClientBookCard.class);
        when(bookCard.getBookId()).thenReturn("abcs");
        when(bookCard.getTitle()).thenReturn("Test Book");
        when(bookCard.getThumbnail()).thenReturn("test_thumbnail.png");
        when(bookCard.getAuthorName()).thenReturn("Author1");
        when(bookCard.getCategoryName()).thenReturn("Category1");
        when(bookCard.getAverageRating()).thenReturn(4.5);
        when(bookCard.getBasePrice()).thenReturn(20.0);
        when(bookCard.getDiscountPrice()).thenReturn(15.0);
        when(bookCard.getTotalSalesCount()).thenReturn(100);
        Page<ClientBookCard> bookPage = new PageImpl<>(List.of(bookCard), PageRequest.of(page, size), 1);
        when(bookRepository.getBookCards(title, publisher, rating, minPrice, maxPrice, authors, categories, PageRequest.of(page, size)))
                .thenReturn(bookPage);
        // Act
        CommonResponse<Map<String, Object>> response = bookService.getAllBookListByClient(title, publisher, rating, minPrice, maxPrice, authors, categories, page, size);

        // Assert
        assertEquals(1, ((List<BookCardResponse>) response.getData().get("content")).size());
        assertEquals("abcs", ((List<BookCardResponse>) response.getData().get("content")).get(0).getBookId());
        assertEquals("Test Book", ((List<BookCardResponse>) response.getData().get("content")).get(0).getTitle());
    }

    //test get list book by client when have no result
    @Test
    void testGetAllBookListByClient_NoResults() {
        // Arrange
        String title = "Nonexistent Title";
        String publisher = "Nonexistent Publisher";
        Integer rating = null;
        Double minPrice = null;
        Double maxPrice = null;
        List<String> authors = Collections.emptyList();
        List<String> categories = Collections.emptyList();
        Integer page = 0;
        Integer size = 10;
        Page<ClientBookCard> emptyPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(page, size), 0);
        when(bookRepository.getBookCards(title, publisher, rating, minPrice, maxPrice, authors, categories, PageRequest.of(page, size)))
                .thenReturn(emptyPage);
        // Act
        CommonResponse<Map<String, Object>> response = bookService.getAllBookListByClient(title, publisher, rating, minPrice, maxPrice, authors, categories, page, size);

        // Assert
        assertEquals(0, ((List<?>) response.getData().get("content")).size());
    }

    @Test
    public void getBookDetailByClient_BookExists_ReturnsBookResponse() {
        Map<String, Object> bookDetails = new HashMap<>();
        bookDetails.put("book_id", "123");
        bookDetails.put("title", "Sample Book");
        bookDetails.put("description", "This is a sample book description");
        bookDetails.put("quantity", 10);
        bookDetails.put("thumbnail", "http://example.com/thumbnail.jpg");
        bookDetails.put("publisherName", "Sample Publisher");
        bookDetails.put("book_image_json", "[{\"url\":\"http://example.com/image1.jpg\"}]");
        bookDetails.put("book_author_json", "[{\"name\":\"Author 1\"}]");
        bookDetails.put("book_category_json", "[{\"name\":\"Category 1\"}]");
        bookDetails.put("base_price", 100.0);
        bookDetails.put("discount_price", 90.0);
        bookDetails.put("book_property_json", "[{\"name\":\"Property 1\", \"value\":\"Value 1\"}]");
        bookDetails.put("totalReviewCounts", 50);
        bookDetails.put("averageRating", 4.5);
        bookDetails.put("totalSalesCount", 200);

        // Giả lập dữ liệu trả về từ bookRepository và jsonUtils
        when(bookRepository.findBookDetailById("123")).thenReturn(Optional.of(bookDetails));
        when(jsonUtils.fromJson(eq("[{\"url\":\"http://example.com/image1.jpg\"}]"), eq(BookImageResponse[].class)))
                .thenReturn(new BookImageResponse[]{BookImageResponse.builder().imageUrl("http://example.com/image1.jpg").build()});
        when(jsonUtils.fromJson(eq("[{\"name\":\"Author 1\"}]"), eq(AuthorResponse[].class)))
                .thenReturn(new AuthorResponse[]{AuthorResponse.builder().name("Author 1").build()});
        when(jsonUtils.fromJson(eq("[{\"name\":\"Category 1\"}]"), eq(CategoryDTO[].class)))
                .thenReturn(new CategoryDTO[]{CategoryDTO.builder().name("Category 1").build()});
        when(jsonUtils.fromJson(eq("[{\"name\":\"Property 1\", \"value\":\"Value 1\"}]"), eq(PropertyResponse[].class)))
                .thenReturn(new PropertyResponse[]{PropertyResponse.builder().name("Property 1").value("Value 1").build()});

        // Gọi phương thức cần kiểm tra
        CommonResponse<Map<String, Object>> response = bookService.getBookDetailByClient("123");

        // Kiểm tra kết quả
        assertNotNull(response);
        assertEquals("123", ((List<BookResponse>) response.getData().get("content")).get(0).getBookId());
        assertEquals("Sample Book", ((List<BookResponse>) response.getData().get("content")).get(0).getTitle());
        assertEquals("This is a sample book description", ((List<BookResponse>) response.getData().get("content")).get(0).getDescription());
        assertEquals(10, ((List<BookResponse>) response.getData().get("content")).get(0).getQuantity());
        assertEquals("http://example.com/thumbnail.jpg", ((List<BookResponse>) response.getData().get("content")).get(0).getThumbnail());
        assertEquals("Sample Publisher", ((List<BookResponse>) response.getData().get("content")).get(0).getPublisher().getName());
        assertEquals(100.0, ((List<BookResponse>) response.getData().get("content")).get(0).getPrice().getBasePrice());
        assertEquals(90.0, ((List<BookResponse>) response.getData().get("content")).get(0).getPrice().getDiscountPrice());
        assertEquals(50, ((List<BookResponse>) response.getData().get("content")).get(0).getTotalReviewsCount());
        assertEquals(4.5, ((List<BookResponse>) response.getData().get("content")).get(0).getAverageRating());
        assertEquals(200, ((List<BookResponse>) response.getData().get("content")).get(0).getTotalSalesCount());

        assertNotNull(((List<BookResponse>) response.getData().get("content")).get(0).getImages());
        assertEquals(1, ((List<BookResponse>) response.getData().get("content")).get(0).getImages().size());
        assertEquals("http://example.com/image1.jpg", ((List<BookResponse>) response.getData().get("content")).get(0).getImages().get(0).getImageUrl());

        assertNotNull(((List<BookResponse>) response.getData().get("content")).get(0).getAuthors());
        assertEquals(1, ((List<BookResponse>) response.getData().get("content")).get(0).getAuthors().size());
        assertEquals("Author 1", ((List<BookResponse>) response.getData().get("content")).get(0).getAuthors().get(0).getName());

        assertNotNull(((List<BookResponse>) response.getData().get("content")).get(0).getCategories());
        assertEquals(1, ((List<BookResponse>) response.getData().get("content")).get(0).getCategories().size());
        assertEquals("Category 1", ((List<BookResponse>) response.getData().get("content")).get(0).getCategories().get(0).getName());

        assertNotNull(((List<BookResponse>) response.getData().get("content")).get(0).getProperties());
        assertEquals(1, ((List<BookResponse>) response.getData().get("content")).get(0).getProperties().size());
        assertEquals("Property 1", ((List<BookResponse>) response.getData().get("content")).get(0).getProperties().get(0).getName());
        assertEquals("Value 1", ((List<BookResponse>) response.getData().get("content")).get(0).getProperties().get(0).getValue());
    }

    @Test
    void testGetBookDetailByClient_fail_data_not_found() {
        String bookId = "invalid_book_id";

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        //Act and Assert
        assertThrows(DataNotFoundException.class, () -> {
            CommonResponse<Map<String, Object>> response = bookService.getBookDetailByClient(bookId);
        });
    }


    @Test
    public void addNewBook_Successfully() {

        BookRequest bookRequest = BookRequest.builder()
                .title("Test Book")
                .thumbnail("http://example.com/thumbnail.jpg")
                .description("Sample description")
                .quantity(10)
                .publisher(PublisherRequest.builder().name("Test Publisher").build())
                .images(List.of(BookImageRequest.builder().imageUrl("http://example.com/image1.jpg").build()))
                .price(PriceRequest.builder().basePrice(100.0).discountPrice(10.0).build())
                .categories(List.of(CategoryRequest.builder().categoryId("001").name("Category 1").build()))
                .authors(List.of(AuthorRequest.builder().name("Author 1").build()))  // Đảm bảo có AuthorRequest
                .properties(List.of(PropertyRequest.builder().name("Property 1").value("Value 1").build()))
                .build();

        when(bookRepository.existsByTitle(bookRequest.getTitle())).thenReturn(false);
        when(publisherRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(publisherRepository.save(any(Publisher.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(priceRepository.save(any(Price.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Author mockAuthor = Author.builder()
                .authorId("001")
                .name("Author 1")
                .build();
        when(authorRepository.findAuthorsByNames(anyList())).thenReturn(Optional.of(List.of(mockAuthor)));

        Category mockCategory = new Category();
        mockCategory.setCategoryId("001");
        mockCategory.setName("Category 1");
        when(categoryRepository.findCategoryByListIds(anyList())).thenReturn(Optional.of(List.of(mockCategory)));

        Property existingProperty = new Property();
        existingProperty.setPropertyId("existing-id");
        existingProperty.setName("Property 1");
        when(propertyRepository.findByName(anyList())).thenReturn(Optional.of(List.of(existingProperty)));

        when(bookAuthorRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        CommonResponse<StringResponse> response = bookService.addNewBook(bookRequest);

        assertEquals("Tạo mới thành công!", response.getMsg());
        verify(bookRepository).save(any(Book.class));
        verify(publisherRepository).save(any(Publisher.class));
        verify(priceRepository).save(any(Price.class));
        verify(categoryRepository).findCategoryByListIds(anyList());
        verify(bookAuthorRepository).saveAll(anyList()); // xác minh saveAll thay vì save
        verify(bookPropertyRepository).saveAll(anyList());
    }

    @Test
    public void addNewBook_DuplicateTitle() {
        // Tạo dữ liệu mẫu cho BookRequest
        BookRequest bookRequest = BookRequest.builder()
                .title("Duplicate Title") // Tiêu đề trùng
                .thumbnail("http://example.com/thumbnail.jpg")
                .description("Sample description")
                .quantity(10)
                .publisher(PublisherRequest.builder().name("Test Publisher").build())
                .images(List.of(BookImageRequest.builder().imageUrl("http://example.com/image1.jpg").build()))
                .price(PriceRequest.builder().basePrice(100.0).discountPrice(10.0).build())
                .categories(List.of(CategoryRequest.builder().categoryId("001").name("Category 1").build()))
                .authors(List.of(AuthorRequest.builder().name("Author 1").build()))
                .properties(List.of(PropertyRequest.builder().name("Property 1").value("Value 1").build()))
                .build();

        // Giả lập rằng tiêu đề đã tồn tại trong cơ sở dữ liệu
        when(bookRepository.existsByTitle(bookRequest.getTitle())).thenReturn(true);

        // Kiểm tra xem ngoại lệ DuplicateDataException có được ném ra không
        assertThrows(DuplicateDataException.class, () -> bookService.addNewBook(bookRequest));

        // Kiểm tra rằng phương thức save của bookRepository không được gọi
        verify(bookRepository, never()).save(any(Book.class));
    }

}
