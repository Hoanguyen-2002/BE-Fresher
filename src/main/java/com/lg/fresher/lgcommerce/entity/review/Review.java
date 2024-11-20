package com.lg.fresher.lgcommerce.entity.review;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lg.fresher.lgcommerce.entity.account.Account;
import com.lg.fresher.lgcommerce.entity.book.Book;
import com.lg.fresher.lgcommerce.entity.core.BaseEntity;
import com.lg.fresher.lgcommerce.utils.UUIDUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : Review
 * @ Description : lg_ecommerce_be Review
 * @ author lg_ecommerce_be Dev Team 63200485
 * @ since 11/20/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/20/2024       63200485      first creation */
@SuperBuilder
@Getter
@Setter
@Entity
@Table(name = "review", schema = "lgecommerce", indexes = {
        @Index(name = "book_id", columnList = "book_id"),
        @Index(name = "account_id", columnList = "account_id")
})
@AllArgsConstructor
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

    @OneToMany(fetch = FetchType.LAZY)
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

    public Review() {
        this.reviewId = UUIDUtil.generateId();
    }
}