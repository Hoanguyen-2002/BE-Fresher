package com.lg.fresher.lgcommerce.entity.book;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lg.fresher.lgcommerce.entity.core.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "price", schema = "lgecommerce", indexes = {
        @Index(name = "book_id", columnList = "book_id")
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Price extends BaseEntity {
    @Id
    @Size(max = 50)
    @Column(name = "price_id", nullable = false, length = 50)
    private String priceId;

    @Min(value = 0, message = "Base price must be a positive real number")
    @Column(name = "base_price")
    private Double basePrice;

    @Min(value = 0, message = "Discount price must be a positive real number")
    @Column(name = "discount_price")
    private Double discountPrice;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "book_id", referencedColumnName = "book_id")
    private Book book;

    @Override
    public String toString() {
        return "Price{" +
                "priceId='" + priceId + '\'' +
                ", basePrice=" + basePrice +
                ", discountPrice=" + discountPrice +
                '}';
    }
}