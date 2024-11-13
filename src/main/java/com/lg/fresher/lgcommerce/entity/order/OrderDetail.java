package com.lg.fresher.lgcommerce.entity.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lg.fresher.lgcommerce.entity.book.Book;
import com.lg.fresher.lgcommerce.entity.core.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "order_detail", schema = "lgecommerce", indexes = {
        @Index(name = "order_id", columnList = "order_id"),
        @Index(name = "book_id", columnList = "book_id")
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetail extends BaseEntity {
    @Id
    @Size(max = 50)
    @Column(name = "order_detail_id", nullable = false, length = 50)
    private String orderDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "book_id")
    private Book book;

    @Min(value = 0, message = "Quantity must be a positive number")
    @Column(name = "quantity")
    private Integer quantity;

    @Min(value = 0, message = "Base price must be a positive real number")
    @Column(name = "base_price")
    private Double basePrice;

    @Min(value = 0, message = "Base price must be a positive real number")
    @Column(name = "discount_price")
    private Double discountPrice;

    @Min(value = 0, message = "Final price must be a positive real number")
    @Column(name = "final_price")
    private Double finalPrice;

    @Column(name = "is_reviewed")
    private Boolean isReviewed;

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderDetailId='" + orderDetailId + '\'' +
                ", quantity=" + quantity +
                ", basePrice=" + basePrice +
                ", discountPrice=" + discountPrice +
                ", finalPrice=" + finalPrice +
                ", isReviewed=" + isReviewed +
                '}';
    }
}