package com.lg.fresher.lgcommerce.service.book;

import com.lg.fresher.lgcommerce.entity.author.Author;
import com.lg.fresher.lgcommerce.entity.book.Book;
import com.lg.fresher.lgcommerce.entity.book.BookCategory;
import com.lg.fresher.lgcommerce.entity.book.Price;
import com.lg.fresher.lgcommerce.entity.book.Property;
import com.lg.fresher.lgcommerce.entity.category.Category;
import com.lg.fresher.lgcommerce.entity.publisher.Publisher;
import com.lg.fresher.lgcommerce.exception.DuplicateDataException;
import com.lg.fresher.lgcommerce.exception.data.DataNotFoundException;
import com.lg.fresher.lgcommerce.model.request.author.AuthorRequest;
import com.lg.fresher.lgcommerce.model.request.book.BookImageRequest;
import com.lg.fresher.lgcommerce.model.request.book.BookRequest;
import com.lg.fresher.lgcommerce.model.request.book.PriceRequest;
import com.lg.fresher.lgcommerce.model.request.category.CategoryRequest;
import com.lg.fresher.lgcommerce.model.request.property.PropertyRequest;
import com.lg.fresher.lgcommerce.model.request.publisher.PublisherRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.StringResponse;
import com.lg.fresher.lgcommerce.model.response.admin.category.CategoryDTO;
import com.lg.fresher.lgcommerce.model.response.author.AuthorResponse;
import com.lg.fresher.lgcommerce.model.response.book.BookCardResponse;
import com.lg.fresher.lgcommerce.model.response.book.BookResponse;
import com.lg.fresher.lgcommerce.model.response.book.itf.ClientBookCard;
import com.lg.fresher.lgcommerce.model.response.book_image.BookImageResponse;
import com.lg.fresher.lgcommerce.model.response.property.PropertyResponse;
import com.lg.fresher.lgcommerce.model.response.publisher.PublisherResponse;
import com.lg.fresher.lgcommerce.repository.author.AuthorRepository;
import com.lg.fresher.lgcommerce.repository.book.*;
import com.lg.fresher.lgcommerce.repository.category.CategoryRepository;
import com.lg.fresher.lgcommerce.repository.publisher.PublisherRepository;
import com.lg.fresher.lgcommerce.utils.JsonUtils;
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
        Map<String, Object> mockBookData = new HashMap<>();
        // Setup mock data to simulate what would be returned by the SQL query
        mockBookData = new HashMap<>();
        mockBookData.put("book_id", "123");
        mockBookData.put("title", "Test Book Title");
        mockBookData.put("description", "Test Book Description");
        mockBookData.put("quantity", 10);
        mockBookData.put("thumbnail", "test-thumbnail-url");
        mockBookData.put("publisher", "{\"publisherId\":\"pub1\",\"name\":\"Test Publisher\"}");
        mockBookData.put("base_price", "100.0");
        mockBookData.put("discount_price", "80.0");
        mockBookData.put("book_image_json", "[{\"bookImageId\":\"img1\",\"imageUrl\":\"image1-url\"}]");
        mockBookData.put("book_author_json", "[{\"bookAuthorId\":\"auth1\",\"name\":\"Author Name\"}]");
        mockBookData.put("book_category_json", "[{\"categoryId\":\"cat1\",\"name\":\"Category Name\"}]");
        mockBookData.put("book_property_json", "[{\"bookPropertyId\":\"prop1\",\"name\":\"Property Name\",\"value\":\"Property Value\"}]");
        mockBookData.put("totalReviewCounts", 20);
        mockBookData.put("averageRating", 4.5);
        mockBookData.put("totalSalesCount", 150);

        // Mock the repository to return the mockBookData
        when(bookRepository.findBookDetailById("123")).thenReturn(Optional.of(mockBookData));

        // Mock jsonUtils deserialization for complex JSON objects
        when(jsonUtils.fromJson("{\"publisherId\":\"pub1\",\"name\":\"Test Publisher\"}", PublisherResponse.class))
                .thenReturn(PublisherResponse.builder().publisherId("pub1").name("Test Publisher").build());

        when(jsonUtils.fromJson("[{\"bookImageId\":\"img1\",\"imageUrl\":\"image1-url\"}]", BookImageResponse[].class))
                .thenReturn(new BookImageResponse[]{BookImageResponse.builder().bookImageId("img1").imageUrl("image1-url").build()});

        when(jsonUtils.fromJson("[{\"bookAuthorId\":\"auth1\",\"name\":\"Author Name\"}]", AuthorResponse[].class))
                .thenReturn(new AuthorResponse[]{AuthorResponse.builder().bookAuthorId("auth1").name("Author Name").build()});

        when(jsonUtils.fromJson("[{\"categoryId\":\"cat1\",\"name\":\"Category Name\"}]", CategoryDTO[].class))
                .thenReturn(new CategoryDTO[]{CategoryDTO.builder().categoryId("cat1").name("Category Name").build()});

        when(jsonUtils.fromJson("[{\"bookPropertyId\":\"prop1\",\"name\":\"Property Name\",\"value\":\"Property Value\"}]", PropertyResponse[].class))
                .thenReturn(new PropertyResponse[]{PropertyResponse.builder().bookPropertyId("prop1").name("Property Name").value("Property Value").build()});

        // Call the method under test
        CommonResponse<Map<String, Object>> response = bookService.getBookDetailByClient("123");

        // Assertions
        assertNotNull(response);
        assertTrue(response.getData().containsKey("content"));
        BookResponse bookRes = (BookResponse) response.getData().get("content");

        // Verify BookResponse details
        assertEquals("123", bookRes.getBookId());
        assertEquals("Test Book Title", bookRes.getTitle());
        assertEquals("Test Book Description", bookRes.getDescription());
        assertEquals(10, bookRes.getQuantity());
        assertEquals("test-thumbnail-url", bookRes.getThumbnail());
        assertEquals("Test Publisher", bookRes.getPublisher().getName());
        assertEquals(100.0, bookRes.getPrice().getBasePrice());
        assertEquals(80.0, bookRes.getPrice().getDiscountPrice());
        assertEquals(1, bookRes.getImages().size());
        assertEquals("image1-url", bookRes.getImages().get(0).getImageUrl());
        assertEquals(1, bookRes.getAuthors().size());
        assertEquals("Author Name", bookRes.getAuthors().get(0).getName());
        assertEquals(1, bookRes.getCategories().size());
        assertEquals("Category Name", bookRes.getCategories().get(0).getName());
        assertEquals(1, bookRes.getProperties().size());
        assertEquals("Property Name", bookRes.getProperties().get(0).getName());
        assertEquals("Property Value", bookRes.getProperties().get(0).getValue());
        assertEquals(20, bookRes.getTotalReviewsCount());
        assertEquals(4.5, bookRes.getAverageRating());
        assertEquals(150, bookRes.getTotalSalesCount());
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

    @Test
    public void updateBook_Successfully() {
        // Initialize BookRequest
        BookRequest bookRequest = BookRequest.builder()
                .bookId("123")
                .title("Updated Title")
                .thumbnail("new-thumbnail-url")
                .status(true)
                .description("Updated description")
                .quantity(20)
                .publisher(new PublisherRequest("456", "New Publisher"))
                .price(new PriceRequest(null, 100.0, 80.0))
                .categories(List.of(new CategoryRequest("789", "Category 1")))
                .authors(List.of(new AuthorRequest(null, "Author 1")))
                .properties(List.of(new PropertyRequest(null, "Property 1", "Value 1")))
                .images(List.of(new BookImageRequest(null, "image-url-1")))
                .build();

        // Initialize an existing Book for repository to return
        Book existingBook = new Book();
        existingBook.setBookId("123");
        existingBook.setTitle("Existing Title");
        existingBook.setStatus(true);

        // Initialize the Price object to avoid NullPointerException
        Price price = new Price();
        price.setPriceId("price123");
        price.setBasePrice(50.0);
        price.setDiscountPrice(45.0);
        existingBook.setPrice(price);

        // Initialize categories in the repository mock
        Category category = Category.builder().categoryId("123").name("category name").build();
        when(categoryRepository.findCategoryByListIds(anyList())).thenReturn(Optional.of(List.of(category)));

        // Initialize other required fields
        existingBook.setCategoryBooks(List.of(BookCategory.builder().bookCategoryId("123").book(existingBook).category(category).build()));

        // Initialize authorBooks list to avoid NullPointerException
        existingBook.setAuthorBooks(new ArrayList<>());

        // Initialize bookProperties list to avoid NullPointerException
        existingBook.setBookProperties(new ArrayList<>());

        // Mock behavior for propertyRepository.findByName to return a list with a valid Property
        Property mockProperty = new Property();
        mockProperty.setPropertyId("prop1");
        mockProperty.setName("Property 1");

        when(propertyRepository.findByName(anyList())).thenReturn(Optional.of(List.of(mockProperty)));

        // Mock repository behavior
        when(bookRepository.findById("123")).thenReturn(Optional.of(existingBook));
        when(bookRepository.existsByTitle("Updated Title")).thenReturn(false);

        // Configure behavior for the publisher repository
        when(publisherRepository.findByName("new publisher")).thenReturn(Optional.empty());
        when(publisherRepository.save(any())).thenAnswer(invocation -> {
            Publisher publisher = invocation.getArgument(0);
            publisher.setPublisherId(UUID.randomUUID().toString());
            return publisher;
        });

        // Call the updateBook method
        CommonResponse<StringResponse> response = bookService.updateBook(bookRequest, "123");

        // Verify interactions and repository updates
        verify(bookRepository, times(1)).findById("123");
        verify(bookRepository, times(1)).save(existingBook);
        verify(publisherRepository, times(1)).findByName("new publisher");

        // Assertions on the response
        assertNotNull(response);
        assertEquals("Cập nhật thành công", response.getMsg());
    }

    @Test
    public void updateBook_failed_DataNotFound() {
        String bookId = "invalid_book_id";

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

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        //Act and Assert
        assertThrows(DataNotFoundException.class, () -> {
            CommonResponse<StringResponse> response = bookService.updateBook(bookRequest, bookId);
        });
    }

    @Test
    public void getTopSeller_succesfully(){
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

        when(bookRepository.findTopSeller()).thenReturn(Optional.of(List.<ClientBookCard>of(bookCard)));

        // Act
        CommonResponse<Map<String, Object>> response = bookService.getTopSeller();

        // Assert
        assertEquals(1, ((List<BookCardResponse>) response.getData().get("content")).size());
        assertEquals("abcs", ((List<BookCardResponse>) response.getData().get("content")).get(0).getBookId());
        assertEquals("Test Book", ((List<BookCardResponse>) response.getData().get("content")).get(0).getTitle());
    }

    @Test
    public void getTopSeller_succesfully_noResult(){


        when(bookRepository.findTopSeller()).thenReturn(Optional.empty());
        // Act
        CommonResponse<Map<String, Object>> response = bookService.getTopSeller();

        // Assert
        assertEquals(0, ((List<BookCardResponse>) response.getData().get("content")).size());
    }
}
