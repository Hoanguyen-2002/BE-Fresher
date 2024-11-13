package com.lg.fresher.lgcommerce.entity.book;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lg.fresher.lgcommerce.entity.core.BaseEntity;
import com.lg.fresher.lgcommerce.entity.category.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "book_category", schema = "lgecommerce", indexes = {
        @Index(name = "book_id", columnList = "book_id"),
        @Index(name = "category_id", columnList = "category_id")
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookCategory extends BaseEntity {
    @Id
    @Size(max = 50)
    @Column(name = "book_category_id", nullable = false, length = 50)
    private String bookCategoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "category_id")
    private Category category;

    @Override
    public String toString() {
        return "BookCategory{" +
                "bookCategoryId='" + bookCategoryId + '\'' +
                '}';
    }
}