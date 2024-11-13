package com.lg.fresher.lgcommerce.entity.book;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lg.fresher.lgcommerce.entity.order.OrderDetail;
import com.lg.fresher.lgcommerce.entity.publisher.Publisher;
import com.lg.fresher.lgcommerce.entity.review.Review;
import com.lg.fresher.lgcommerce.entity.core.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "book", schema = "lgecommerce", indexes = {
        @Index(name = "publisher_id", columnList = "publisher_id")
})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book extends BaseEntity {
    @Id
    @Size(max = 50)
    @Column(name = "book_id", nullable = false, length = 50)
    private String bookId;

    @Size(max = 255)
    @Column(name = "title")
    private String title;

    @Size(max = 255)
    @Column(name = "thumbnail")
    private String thumbnail;

    @Lob
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @OneToMany(mappedBy = "book")
    @JsonManagedReference
    private List<BookAuthor> authorBooks;

    @OneToMany(mappedBy = "book")
    @JsonManagedReference
    private List<BookImage> bookImages;

    @OneToMany(mappedBy = "book")
    @JsonManagedReference
    private List<BookCategory> categoryBooks;

    @OneToMany(mappedBy = "book")
    @JsonManagedReference
    private List<BookProperty> bookProperties;

    @OneToMany(mappedBy = "book")
    @JsonManagedReference
    private List<Review> reviews;

    @OneToMany(mappedBy = "book")
    @JsonManagedReference
    private List<OrderDetail> orderDetails;

    @OneToOne(mappedBy = "book")
    @JsonManagedReference
    private Price price;

    @Override
    public String toString() {
        return "Book{" +
                "bookId='" + bookId + '\'' +
                ", title='" + title + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", status=" + status +
                '}';
    }
}