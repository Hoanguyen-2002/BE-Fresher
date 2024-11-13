package com.lg.fresher.lgcommerce.entity.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lg.fresher.lgcommerce.entity.account.Account;
import com.lg.fresher.lgcommerce.entity.core.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "`order`", schema = "lgecommerce", indexes = {
        @Index(name = "account_id", columnList = "account_id"),
        @Index(name = "shipping_id", columnList = "shipping_id"),
        @Index(name = "payment_id", columnList = "payment_id"),
        @Index(name = "status_id", columnList = "status_id")
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order extends BaseEntity {
    @Id
    @Size(max = 50)
    @Column(name = "order_id", nullable = false, length = 50)
    private String orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "shipping_id")
    private ShippingMethod shippingMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "payment_id", referencedColumnName = "payment_method_id")
    private PaymentMethod paymentMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "status_id")
    private Status status;

    @OneToMany(mappedBy = "order")
    @JsonManagedReference
    private List<OrderDetail> orderDetails;

    @Min(value = 0, message = "total must be a positive real number")
    @Column(name = "total_amount")
    private Double totalAmount;

    @Length(max = 50)
    @Column(name = "recipient", nullable = false, length = 50)
    private String recipient;

    @Length(max = 320, message = "The length of email has max 320 characters")
    @Column(name = "email")
    private String email;

    @Length(max = 50)
    @Column(name = "phone", length = 50)
    private String phone;

    @Min(value = 0, message = "Shipping Fee must be a positive real number")
    @Column(name = "shipping_fee")
    private Double shippingFee;

    @Length(max = 500, message = "The length of address detail must have max 500 characters")
    @Column(name = "detail_address")
    private String detailAddress;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", total=" + totalAmount +
                ", recipient='" + recipient + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", shippingFee=" + shippingFee +
                ", detailAddress='" + detailAddress + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }
}