package com.lg.fresher.lgcommerce.service.book;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
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
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.StringResponse;
import com.lg.fresher.lgcommerce.model.response.author.AuthorResponse;
import com.lg.fresher.lgcommerce.model.response.book.BookCardResponse;
import com.lg.fresher.lgcommerce.model.response.book.BookResponse;
import com.lg.fresher.lgcommerce.model.response.book_image.BookImageResponse;
import com.lg.fresher.lgcommerce.model.response.admin.category.CategoryDTO;
import com.lg.fresher.lgcommerce.model.response.price.PriceResponse;
import com.lg.fresher.lgcommerce.model.response.property.PropertyResponse;
import com.lg.fresher.lgcommerce.model.response.publisher.PublisherResponse;
import com.lg.fresher.lgcommerce.repository.author.AuthorRepository;
import com.lg.fresher.lgcommerce.repository.book.*;
import com.lg.fresher.lgcommerce.repository.category.CategoryRepository;
import com.lg.fresher.lgcommerce.repository.publisher.PublisherRepository;
import com.lg.fresher.lgcommerce.model.response.book.itf.ClientBookCard;
import com.lg.fresher.lgcommerce.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : BookServiceImpl
 * @ Description : lg_ecommerce_be BookServiceImpl
 * @ author lg_ecommerce_be Dev Team 63200504
 * @ since 11/6/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/6/2024       63200504      first creation */
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final JsonUtils jsonUtils;
    private final PublisherRepository publisherRepository;
    private final BookImageRepository bookImageRepository;
    private final BookAuthorRepository bookAuthorRepository;
    private final AuthorRepository authorRepository;
    private final BookCategoryRepository bookCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final PropertyRepository propertyRepository;
    private final PriceRepository priceRepository;
    private final BookPropertyRepository bookPropertyRepository;


    /**
     *
     * @param title
     * @param publisher
     * @param rating
     * @param minPrice
     * @param maxPrice
     * @param authors
     * @param categories
     * @param page
     * @param size
     * @return
     */
    @Override
    public CommonResponse<Map<String, Object>> getAllBookListByClient(String title, String publisher, Integer rating, Double minPrice, Double maxPrice,
                                                 List<String> authors, List<String> categories, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<ClientBookCard> books = bookRepository.getBookCards(title, publisher, rating, minPrice, maxPrice, authors, categories, pageable);

        Map<String, Object> res = new HashMap<>();
        res.put("content", books.stream().map(clientBookCard -> BookCardResponse.builder()
                        .bookId(clientBookCard.getBookId())
                        .title(clientBookCard.getTitle())
                        .thumbnail(clientBookCard.getThumbnail())
                        .authorName(clientBookCard.getAuthorName())
                        .categoryName(clientBookCard.getCategoryName())
                        .publisherName(clientBookCard.getPublisherName())
                        .averageRating(clientBookCard.getAverageRating())
                        .basePrice(clientBookCard.getBasePrice())
                        .discountPrice(clientBookCard.getDiscountPrice())
                        .totalSalesCount(clientBookCard.getTotalSalesCount())
                        .build())
                .toList());
        res.put("metaData", Map.of("totalElements", books.getTotalElements(),
                                    "limit", page,
                                    "offset", size));
        return CommonResponse.success(res);
    }

    /**
     *
     * @param bookId
     * @return
     */
    @Override
    @Transactional
    public CommonResponse<Map<String, Object>> getBookDetailByClient(String bookId) {
        Map<String, Object> res = bookRepository.findBookDetailById(bookId).orElseThrow(() -> new DataNotFoundException("Book not found"));

        Double basePrice = res.get("base_price") == null ? null : Double.parseDouble(res.get("base_price").toString());
        Double discountPrice = res.get("discount_price") == null ? null : Double.parseDouble(res.get("discount_price").toString());

        //tranfer tro dto
        BookResponse bookRes = BookResponse.builder()
                .bookId(res.get("book_id").toString())
                .title(res.get("title").toString())
                .description(res.get("description").toString())
                .quantity(Integer.parseInt(res.get("quantity").toString()))
                .thumbnail(res.get("thumbnail").toString())
                .publisher(res.get("publisherName") == null ? null : PublisherResponse.builder()
                        .name(res.get("publisherName").toString())
                        .build())
                .images(res.get("book_image_json") == null ? null :
                        Arrays.stream((jsonUtils.fromJson(res.get("book_image_json").toString(), BookImageResponse[].class))).toList())
                .authors(res.get("book_author_json") == null ? null :
                        Arrays.stream((jsonUtils.fromJson(res.get("book_author_json").toString(), AuthorResponse[].class))).toList())
                .categories(res.get("book_category_json") == null ? null :
                        Arrays.stream((jsonUtils.fromJson(res.get("book_category_json").toString(), CategoryDTO[].class))).toList())
                .price(PriceResponse.builder()
                        .discountPrice(discountPrice)
                        .basePrice(basePrice)
                        .build())
                .properties(res.get("book_property_json") == null ? null :
                        Arrays.stream((jsonUtils.fromJson(res.get("book_property_json").toString(), PropertyResponse[].class))).toList())
                .totalReviewsCount(Integer.valueOf(res.get("totalReviewCounts").toString()))
                .averageRating(Double.valueOf(res.get("averageRating").toString()))
                .totalSalesCount(Integer.valueOf(res.get("totalSalesCount").toString()))
                .build();
        return CommonResponse.success(Map.of("content", List.of(bookRes)));
    }

    /**
     *
     * @param bookRequest
     * @return
     */
    @Override
    @Transactional
    public CommonResponse<StringResponse> addNewBook(BookRequest bookRequest) {
        if(bookRequest.getPublisher() == null || bookRequest.getAuthors().isEmpty() ||
                bookRequest.getPrice() == null || bookRequest.getCategories().isEmpty()  )
            throw new DataNotNullException("Data is required");

        if (bookRepository.existsByTitle(bookRequest.getTitle())) {
                throw new DuplicateDataException("Title already exists");
        }

        //handle publisher
        Optional<Publisher> optionalPublisher = publisherRepository.findByName(bookRequest.getPublisher().getName().toLowerCase());
        Publisher publisher = optionalPublisher.orElseGet(() -> publisherRepository.save(Publisher.builder()
                .publisherId(UUID.randomUUID().toString()).name(bookRequest.getPublisher().getName()).build()));

        //handle book
        Book book = bookRepository.save(Book.builder()
                .bookId(UUID.randomUUID().toString()).title(bookRequest.getTitle())
                .thumbnail(bookRequest.getThumbnail()).description(bookRequest.getDescription() == null ? null : bookRequest.getDescription())
                .status(true).quantity(bookRequest.getQuantity()).publisher(publisher).build());

        //handle image
        List<BookImageRequest> bookImageRequests = bookRequest.getImages();
        addBookImageFromBookRequest(bookImageRequests, book);

        //handle price
        PriceRequest priceRequest = bookRequest.getPrice();
        addPriceFromBookRequest(priceRequest, book);

        //handle category
        List<CategoryRequest> categoryRequests = bookRequest.getCategories();
        addCategoryFromBookRequest(categoryRequests, book);

        //handle author
        List<AuthorRequest> authorRequests = bookRequest.getAuthors();
        addAuthorFromBookRequest(authorRequests, book);

        //handle property
        List<PropertyRequest> propertyRequests = bookRequest.getProperties();
        addPropertyFromBookRequest(propertyRequests, book);

        return CommonResponse.success("Tạo mới thành công!");
    }

    @Override
    public CommonResponse<StringResponse> updateBook(BookRequest bookRequest, String id) {
        Book refBook = bookRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy book có id: "+ id));

        if (bookRepository.existsByTitle(bookRequest.getTitle()) && !refBook.getTitle().equals(bookRequest.getTitle())) {
            throw new DuplicateDataException("Title already exists");
        }

        //handle update categories
        List<CategoryRequest> categoryRequests = bookRequest.getCategories();
        // Filter out existing categories in the system
        List<Category> categories = categoryRepository.findCategoryByListIds(categoryRequests
                        .stream().map(CategoryRequest::getCategoryId).toList())
                .orElseThrow(() -> new DataNotFoundException("Category không tồn tại"));

        List<BookCategory> bookCategories = refBook.getCategoryBooks();

        //delete un-macthing categories
        List<BookCategory> deletedBookCategorides = bookCategories.stream()
                .filter(bookCategory -> !categories.contains(bookCategory.getCategory())).toList();
        if (!deletedBookCategorides.isEmpty()) {
            bookCategoryRepository.deleteAll(deletedBookCategorides);
        }

        //update new categories
//        List<BookCategory> newBookCategorides = bookCategories.stream()
//                .filter(bookCategory -> ).toList();

        return null;
    }

    /**
     *
     * @ Description : lg_ecommerce_be BookServiceImpl Member Field extracted
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/11/2024           63200504    first creation
     *<pre>
     * @param propertyRequests
     * @param book
     */
    private void addPropertyFromBookRequest(List<PropertyRequest> propertyRequests, Book book) {
        if (propertyRequests == null || propertyRequests.isEmpty()) {
            return;
        }

        //collect properties that client enter
        Map<String, PropertyRequest> originalPropertyRequestMap = propertyRequests
                .stream().collect(Collectors.toMap(propertyRequest -> propertyRequest.getName().toLowerCase(), propertyRequest -> propertyRequest));


        //collect all matching property in database
        Map<String, BookProperty> matchingProperties= new HashMap<>();
        propertyRepository.findByName(originalPropertyRequestMap.keySet().stream().toList())
        .orElse(new ArrayList<>())
        .forEach(property -> {
            String name = property.getName().toLowerCase();
            matchingProperties.putIfAbsent(name, BookProperty.builder()
                    .bookPropertyId(UUID.randomUUID().toString())
                    .property(Property.builder()
                            .propertyId(property.getPropertyId())
                            .name(name)
                            .build())
                    .book(book)
                    .value(originalPropertyRequestMap.get(name).getValue()).build());
            originalPropertyRequestMap.keySet().remove(name);
        });


        //save unique property in database and collect to list
        for (String key : originalPropertyRequestMap.keySet()){
            Property property = propertyRepository.save(Property.builder()
                            .propertyId(UUID.randomUUID().toString())
                            .name(key)
                            .build());
            matchingProperties.putIfAbsent(key, BookProperty.builder()
                            .bookPropertyId(UUID.randomUUID().toString())
                            .property(Property.builder()
                                    .propertyId(property.getPropertyId())
                                    .name(property.getName())
                                    .build())
                            .book(book)
                            .value(originalPropertyRequestMap.get(key).getValue())
                            .build());
        }

        //collect book_property and save in database
        bookPropertyRepository.saveAll(matchingProperties.values().stream().toList());
    }

    /**
     *
     * @ Description : lg_ecommerce_be BookServiceImpl Member Field addAuthorFromBookRequest
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/11/2024           63200504    first creation
     *<pre>
     * @param authorRequests
     * @param book
     * @return  List<BookAuthor>
     */
    private void addAuthorFromBookRequest(List<AuthorRequest> authorRequests, Book book) {
        //List author name that client enter
        Set<String> inputAuthorNames = authorRequests
                .stream().map(authorRequest -> authorRequest.getName().toLowerCase()).collect(Collectors.toSet());

        //Collect matching author names in database
        List<Author> authors = authorRepository.findAuthorsByNames(inputAuthorNames.stream().toList()).orElse(new ArrayList<>());

        List<BookAuthor> bookAuthors = new ArrayList<>();

        //collect unique author name and save in database
        Set<String> uniqueAuthors = new HashSet<>(inputAuthorNames);
        authors.forEach(author -> {
            String name = author.getName().toLowerCase();
            uniqueAuthors.remove(name);
            bookAuthors.add(BookAuthor.builder()
                    .bookAuthorId(UUID.randomUUID().toString())
                    .book(book)
                    .author(author)
                    .build());
        });

        if (!uniqueAuthors.isEmpty()) {
            List<Author> newAuthors = uniqueAuthors.stream().map(uniqueAuthor -> {
                Author newAuthor = Author.builder()
                        .authorId(UUID.randomUUID().toString())
                        .name(uniqueAuthor)
                        .build();
                bookAuthors.add(BookAuthor.builder()
                        .bookAuthorId(UUID.randomUUID().toString())
                        .book(book)
                        .author(newAuthor)
                        .build());
                return newAuthor;
            }).toList();
            authorRepository.saveAll(newAuthors);
        }

        bookAuthorRepository.saveAll(bookAuthors);
    }

    /**
     *
     * @ Description : lg_ecommerce_be BookServiceImpl Member Field addCategoryFromBookRequest
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/11/2024           63200504    first creation
     *<pre>
     * @param categoryRequests
     * @param book
     */
    private void addCategoryFromBookRequest(List<CategoryRequest> categoryRequests, Book book) {
        // Filter out existing categories in the system
        List<Category> categories = categoryRepository.findCategoryByListIds(categoryRequests
                        .stream().map(CategoryRequest::getCategoryId).toList())
                .orElseThrow(() -> new DataNotFoundException("Category không tồn tại"));

        //save book_category in database
        bookCategoryRepository.saveAll(categories.stream().map(category ->
                BookCategory.builder()
                        .bookCategoryId(UUID.randomUUID().toString())
                        .book(book)
                        .category(category)
                        .build()).toList()
        );
    }

    /**
     *
     * @ Description : lg_ecommerce_be BookServiceImpl Member Field addPriceFromBookRequest
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/11/2024           63200504    first creation
     *<pre>
     * @param priceRequest
     * @param book
     * @return  Price
     */
    private void addPriceFromBookRequest(PriceRequest priceRequest, Book book) {
        priceRepository.save(Price.builder()
                .priceId(UUID.randomUUID().toString()).basePrice(priceRequest.getBasePrice())
                .discountPrice(priceRequest.getDiscountPrice()).book(book).build());
    }

    /**
     *
     * @ Description : lg_ecommerce_be BookServiceImpl Member Field addBookImageFromBookRequest
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/11/2024           63200504    first creation
     *<pre>
     * @param bookImageRequests
     * @param book
     * @return  List<BookImage>
     */
    private void addBookImageFromBookRequest(List<BookImageRequest> bookImageRequests, Book book) {
        if (bookImageRequests == null || bookImageRequests.isEmpty()) return;

        bookImageRepository.saveAll(bookImageRequests.stream().map(bookImageRequest ->
                BookImage.builder().bookImageId(UUID.randomUUID().toString())
                        .imageUrl(bookImageRequest.getImageUrl()).book(book).build()
        ).toList());
    }
}
