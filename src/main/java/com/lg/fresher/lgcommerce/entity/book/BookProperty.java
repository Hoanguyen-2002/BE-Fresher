package com.lg.fresher.lgcommerce.entity.book;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lg.fresher.lgcommerce.entity.core.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "book_property", schema = "lgecommerce", indexes = {
        @Index(name = "book_id", columnList = "book_id"),
        @Index(name = "property_id", columnList = "property_id")
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookProperty extends BaseEntity {
    @Id
    @Size(max = 50)
    @Column(name = "book_property_id", nullable = false, length = 50)
    private String bookPropertyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "property_id")
    private Property property;

    @Size(max = 255)
    @Column(name = "value")
    private String value;

    @Override
    public String toString() {
        return "BookProperty{" +
                "bookPropertyId='" + bookPropertyId + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}