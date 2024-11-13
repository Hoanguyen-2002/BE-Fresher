package com.lg.fresher.lgcommerce.entity.book;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lg.fresher.lgcommerce.entity.author.Author;
import com.lg.fresher.lgcommerce.entity.core.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "book_author", schema = "lgecommerce", indexes = {
        @Index(name = "book_id", columnList = "book_id"),
        @Index(name = "author_id", columnList = "author_id")
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookAuthor extends BaseEntity {
    @Id
    @Size(max = 50)
    @Column(name = "book_author_id", nullable = false, length = 50)
    private String bookAuthorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "author_id")
    private Author author;

}