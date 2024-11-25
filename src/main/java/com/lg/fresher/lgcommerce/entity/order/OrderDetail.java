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

    @Min(value = 0, message = "Số lượng đặt phải là một số nguyên dương")
    @Column(name = "quantity")
    private Integer quantity;

    @Min(value = 0, message = "Giá gốc phải lớn hơn 0")
    @Column(name = "base_price")
    private Double basePrice;

    @Min(value = 0, message = "Giá giảm phải lớn hơn hoặc bằng 0")
    @Column(name = "discount_price")
    private Double discountPrice;

    @Min(value = 0, message = "Giá cuối cùng phải lớn hơn 0")
    @Column(name = "final_price")
    private Double finalPrice;

    @Column(name = "is_reviewed")
    private Boolean isReviewed;

    @Min(value = 0, message = "Tổng giá tiền không thể âm")
    @Column(name = "total")
    private Double total;

    @Column(name = "note", length = 150)
    private String note;

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderDetailId='" + orderDetailId + '\'' +
                ", order=" + order +
                ", book=" + book +
                ", quantity=" + quantity +
                ", basePrice=" + basePrice +
                ", discountPrice=" + discountPrice +
                ", finalPrice=" + finalPrice +
                ", isReviewed=" + isReviewed +
                ", total=" + total +
                ", note='" + note + '\'' +
                '}';
    }
}