package com.lg.fresher.lgcommerce.entity.book;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lg.fresher.lgcommerce.entity.core.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "book_image", schema = "lgecommerce", indexes = {
        @Index(name = "book_id", columnList = "book_id")
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookImage extends BaseEntity {
    @Id
    @Size(max = 50)
    @Column(name = "book_image_id", nullable = false, length = 50)
    private String bookImageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "book_id")
    private Book book;

    @Size(max = 255)
    @Column(name = "image_url")
    private String imageUrl;

    @Override
    public String toString() {
        return "BookImage{" +
                "bookImageId='" + bookImageId + '\'' +
                ", url='" + imageUrl + '\'' +
                '}';
    }
}