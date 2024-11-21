package com.lg.fresher.lgcommerce.service.book;

import com.lg.fresher.lgcommerce.constant.Status;
import com.lg.fresher.lgcommerce.entity.author.Author;
import com.lg.fresher.lgcommerce.entity.book.*;
import com.lg.fresher.lgcommerce.entity.category.Category;
import com.lg.fresher.lgcommerce.entity.publisher.Publisher;
import com.lg.fresher.lgcommerce.exception.DuplicateDataException;
import com.lg.fresher.lgcommerce.exception.InvalidRequestException;
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
import com.lg.fresher.lgcommerce.repository.book.custom.BookCard;
import com.lg.fresher.lgcommerce.repository.book.custom.BookCustomRepository;
import com.lg.fresher.lgcommerce.repository.category.CategoryRepository;
import com.lg.fresher.lgcommerce.repository.publisher.PublisherRepository;
import com.lg.fresher.lgcommerce.model.response.book.itf.ClientBookCard;
import com.lg.fresher.lgcommerce.utils.JsonUtils;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final BookCustomRepository bookCustomRepository;
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
    private final Set<String> VALID_SORT_COLUMNS = Set.of("title", "sellingPrice", "averageRating", "totalSalesCount");


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
                                                                      List<String> authors, List<String> categories, List<String> sort, Boolean status, Integer page, Integer size) {

        //handle sort
        Sort orders = getSort(sort);

        Pageable pageable = PageRequest.of(page, size, orders);

        Map<String, Object> resources = bookCustomRepository.getBookCard(title, publisher, rating, minPrice, maxPrice, authors, categories, status, pageable);
        List<BookCard> books = (List<BookCard>) resources.get("data");
        Map<String, Object> res = new HashMap<>();
        res.put("content", books.stream().map(BookCardResponse::from).toList());
        res.put("metaData", Map.of("totalElements", (Long) resources.get("total"),
                                    "limit", size,
                                    "offset", page));
        return CommonResponse.success(res);
    }

    /**
     *
     * @ Description : lg_ecommerce_be BookServiceImpl Member Field getSort
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/18/2024           63200504    first creation
     *<pre>
     * @param sort
     * @return  Sort
     */
    private Sort getSort(List<String> sort) {

        if (sort == null || sort.isEmpty()) {
            return Sort.by(new Sort.Order(Sort.Direction.DESC, "created_at"));
        }

        return Sort.by(sort.stream()
                .map(param -> {
                    String[] parts = param.split("_");
                    String column = parts[0].trim();
                    String direction = "asc";

                    if (parts.length == 2) {
                        direction = parts[1].trim().toLowerCase();
                    }

                    if (!VALID_SORT_COLUMNS.contains(column) ||
                            (!direction.equalsIgnoreCase("asc") && !direction.equalsIgnoreCase("desc"))) {
                        throw new InvalidRequestException(Status.FAIL_SEARCH_INVALID_PARAM);
                    }

                    Sort.Direction sortDirection = direction.equalsIgnoreCase("desc")
                            ? Sort.Direction.DESC
                            : Sort.Direction.ASC;

                    return new Sort.Order(sortDirection, column);
                })
                .toList());
    }

    /**
     *
     * @param bookId
     * @return
     */
    @Override
    @Transactional
    public CommonResponse<Map<String, Object>> getBookDetailByClient(String bookId) {
        Map<String, Object> res = bookRepository.findBookDetailById(bookId).orElse(new HashMap<>());

        if (res.isEmpty()) throw new DataNotFoundException("Không tìm thấy sách hợp lệ!");

        Double basePrice = res.get("base_price") == null ? null : Double.parseDouble(res.get("base_price").toString());
        Double discountPrice = res.get("discount_price") == null ? null : Double.parseDouble(res.get("discount_price").toString());

        //tranfer tro dto
        BookResponse bookRes = BookResponse.builder()
                .bookId(res.get("book_id").toString())
                .title(res.get("title").toString())
                .description(res.get("description").toString())
                .quantity(Integer.parseInt(res.get("quantity").toString()))
                .thumbnail(res.get("thumbnail").toString())
                .status(Boolean.parseBoolean(res.get("status").toString()))
                .publisher(res.get("publisher") == null ? null : jsonUtils.fromJson(res.get("publisher").toString(), PublisherResponse.class))
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
    @Transactional
    public CommonResponse<StringResponse> updateBook(BookRequest bookRequest, String id) {
        Book refBook = bookRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy book có id: "+ id));

        if (bookRepository.existsByTitle(bookRequest.getTitle()) && !refBook.getTitle().equals(bookRequest.getTitle())) {
            throw new DuplicateDataException("Title đã tồn tại!");
        }

        //update publisher
        Optional<Publisher> optionalPublisher = publisherRepository.findByName(bookRequest.getPublisher().getName().toLowerCase());
        Publisher publisher = optionalPublisher.orElseGet(() -> publisherRepository.save(Publisher.builder()
                .publisherId(UUID.randomUUID().toString()).name(bookRequest.getPublisher().getName()).build()));

        // update common information
        refBook.setTitle(bookRequest.getTitle());
        refBook.setThumbnail(bookRequest.getThumbnail());
        refBook.setStatus(bookRequest.getStatus() == null || refBook.getStatus());
        refBook.setDescription(bookRequest.getDescription());
        refBook.setQuantity(bookRequest.getQuantity());
        refBook.setPublisher(publisher);
        bookRepository.save(refBook);

        //update image
        List<BookImageRequest> bookImageRequests = bookRequest.getImages();
        updateBookImagesFromBookRequest(bookImageRequests, refBook);

        //update price
        PriceRequest priceRequest = bookRequest.getPrice();
        updatePriceFromBookRequest(priceRequest, refBook);

        //update categories
        List<CategoryRequest> categoryRequests = bookRequest.getCategories();
        updateCategoryFromBookRequest(categoryRequests, refBook);

        //update author
        List<AuthorRequest> authorRequests = bookRequest.getAuthors();
        updateAuthorFromBookRequest(authorRequests, refBook);

        //update property
        List<PropertyRequest> propertyRequests = bookRequest.getProperties();
        updatePropertyFromBookRequest(propertyRequests, refBook);

        return CommonResponse.success("Cập nhật thành công");
    }

    /**
     *
     * @return
     */
    @Override
    public CommonResponse<Map<String, Object>> getTopSeller() {
        List<ClientBookCard> data = bookRepository.findTopSeller().orElse(new ArrayList<>());
        Map<String, Object> res = new HashMap<>();
        res.put("content", data.stream().map(clientBookCard -> BookCardResponse.builder()
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
        return CommonResponse.success(res);
    }

    /**
     *
     * @ Description : lg_ecommerce_be BookServiceImpl Member Field updatePropertyFromBookRequest
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/14/2024           63200504    first creation
     *<pre>
     * @param propertyRequests
     * @param refBook
     */
    private void updatePropertyFromBookRequest(List<PropertyRequest> propertyRequests, Book refBook) {
        //collect origin property
        Map<String, BookProperty> originalProperties = refBook.getBookProperties().stream()
                .collect(Collectors.toMap(BookProperty::getBookPropertyId, bookProperty -> bookProperty));

        //collect input property
        Map<String, PropertyRequest> newProperties = new HashMap<>();
        Map<String, BookProperty> existedProperties = new HashMap<>();
        if (propertyRequests != null){
            propertyRequests.forEach(propertyRequest -> {
                if (propertyRequest.getBookPropertyId() != null) {
                    BookProperty existed = originalProperties.get(propertyRequest.getBookPropertyId());
                    existed.setValue(propertyRequest.getValue());
                    existedProperties.putIfAbsent(existed.getBookPropertyId(), existed);
                    originalProperties.keySet().remove(existed.getBookPropertyId());
                }
                else newProperties.put(propertyRequest.getName(), propertyRequest);
            });
        }

        //delete properties
        if (!originalProperties.isEmpty()) bookPropertyRepository.deleteAll(originalProperties.values().stream().toList());

        //update value for existed property
        if (!existedProperties.isEmpty()) bookPropertyRepository.saveAll(existedProperties.values().stream().toList());

        //update new property
        if (!newProperties.isEmpty()) addPropertyFromBookRequest(newProperties.values().stream().toList(), refBook);
    }

    /**
     *
     * @ Description : lg_ecommerce_be BookServiceImpl Member Field updateAuthorFromBookRequest
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/13/2024           63200504    first creation
     *<pre>
     * @param authorRequests
     * @param refBook
     */
    private void updateAuthorFromBookRequest(List<AuthorRequest> authorRequests, Book refBook){
        //collect origin authors
        Map<String, BookAuthor> originalAuthors = refBook.getAuthorBooks()
                .stream().collect(Collectors.toMap(BookAuthor::getBookAuthorId, bookAuthor -> bookAuthor));
        //Collect new author and deleted author
        Map<String, AuthorRequest> newAuthorRequest = new HashMap<>();
        authorRequests.forEach((authorRequest) -> {
            if (authorRequest.getBookAuthorId() == null) newAuthorRequest.putIfAbsent(authorRequest.getName(), authorRequest);
            else originalAuthors.keySet().remove(authorRequest.getBookAuthorId());
        });

        //delete author
        if (!originalAuthors.isEmpty()) bookAuthorRepository.deleteAll(originalAuthors.values().stream().toList());

        //update new author
        if (!newAuthorRequest.isEmpty()) addAuthorFromBookRequest(newAuthorRequest.values().stream().toList(), refBook);
    }

    /**
     *
     * @ Description : lg_ecommerce_be BookServiceImpl Member Field updatePriceFromBookRequest
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/13/2024           63200504    first creation
     *<pre>
     * @param priceRequest
     * @param refBook
     */
    private void updatePriceFromBookRequest(PriceRequest priceRequest, Book refBook) {
        Price price = refBook.getPrice();
        price.setBasePrice(priceRequest.getBasePrice());
        price.setDiscountPrice(priceRequest.getDiscountPrice() == null ? 0 : priceRequest.getDiscountPrice());
        priceRepository.save(price);
    }

    /**
     *
     * @ Description : lg_ecommerce_be BookServiceImpl Member Field updateBookImagesFromBookRequest
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/13/2024           63200504    first creation
     *<pre>
     * @param images
     * @param refBook
     */
    private void updateBookImagesFromBookRequest(List<BookImageRequest> images, Book refBook){

        List<BookImage> bookImages = refBook.getBookImages();
        if (bookImages == null) {
            bookImages = new ArrayList<>(); // Initialize as an empty list if null
            refBook.setBookImages(bookImages);
        }

        Map<String, BookImage> originalImages = bookImages.stream()
                .collect(Collectors.toMap(BookImage::getBookImageId, bookImage -> bookImage));

        //collect new images and deleted images
        List<BookImage> newBookImages = new ArrayList<>();
        if (images != null) {
            images.forEach(bookImageRequest -> {
                if (bookImageRequest.getBookImageId() != null)
                    originalImages.keySet().remove(bookImageRequest.getBookImageId());
                else newBookImages.add(BookImage.builder()
                        .bookImageId(UUID.randomUUID().toString())
                        .imageUrl(bookImageRequest.getImageUrl())
                        .book(refBook)
                        .build());
            });
        }
        //delete image
        if (!originalImages.isEmpty()){
            bookImageRepository.deleteAll(originalImages.values().stream().toList());
        }

        //update new images
        if (!newBookImages.isEmpty()){
            bookImageRepository.saveAll(newBookImages);
        }
    }


    /**
     *
     * @ Description : lg_ecommerce_be BookServiceImpl Member Field updateCategoryFromBookRequest
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/13/2024           63200504    first creation
     *<pre>
     * @param categoryRequests
     * @param refBook
     */
    private void updateCategoryFromBookRequest(List<CategoryRequest> categoryRequests, Book refBook) {

        List<String> ids = categoryRequests.stream().map(CategoryRequest::getCategoryId).toList();

        //Collect all category that client enter
        List<Category> inputCategories = categoryRepository.findCategoryByListIds(ids).orElse(new ArrayList<>());
        if (inputCategories.isEmpty()) throw new DataNotNullException("Không tìm thấy danh mục hợp lệ");
        Map<String, Category> newCategories = inputCategories.stream().collect(Collectors.toMap(Category::getCategoryId, category -> category));

        //Collect existed category of book in database
        List<BookCategory> existedCategories = bookCategoryRepository.findByIds(newCategories.keySet().stream().toList(), refBook.getBookId())
                .orElse(new ArrayList<>());

        //collect new category and deleted category
        Map<String, BookCategory> deletedBooCategories = refBook.getCategoryBooks().stream()
                .collect(Collectors.toMap(BookCategory::getBookCategoryId, bookCategory -> bookCategory));
        existedCategories.forEach(bookCategory -> {
            newCategories.keySet().remove(bookCategory.getCategory().getCategoryId());
            deletedBooCategories.keySet().remove(bookCategory.getBookCategoryId());
        });

        //delete invalid category
        bookCategoryRepository.deleteAll(deletedBooCategories.values().stream().toList());

        //update new category
        bookCategoryRepository.saveAll(newCategories.keySet().stream().map(id -> BookCategory.builder()
                .bookCategoryId(UUID.randomUUID().toString())
                .book(refBook)
                .category(newCategories.get(id))
                .build()).toList());
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
                .orElse(new ArrayList<>());

        if (categories.isEmpty()) throw new DataNotNullException("Không tìm thấy danh mục hợp lệ");

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
                .discountPrice(priceRequest.getDiscountPrice() == null ? 0 : priceRequest.getDiscountPrice()).book(book).build());
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
