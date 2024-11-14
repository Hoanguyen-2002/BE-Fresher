package com.lg.fresher.lgcommerce.entity.review;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lg.fresher.lgcommerce.entity.account.Account;
import com.lg.fresher.lgcommerce.entity.book.Book;
import com.lg.fresher.lgcommerce.entity.core.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "review", schema = "lgecommerce", indexes = {
        @Index(name = "book_id", columnList = "book_id"),
        @Index(name = "account_id", columnList = "account_id")
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review extends BaseEntity {
    @Id
    @Size(max = 50)
    @Column(name = "review_id", nullable = false, length = 50)
    private String reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "review")
    private List<ReviewImage> reviewImages;

    @Column(name = "rating")
    private Integer rating;

    @Length(max = 255)
    @Column(name = "comment")
    private String comment;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @Override
    public String toString() {
        return "Review{" +
                "reviewId='" + reviewId + '\'' +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                '}';
    }
}