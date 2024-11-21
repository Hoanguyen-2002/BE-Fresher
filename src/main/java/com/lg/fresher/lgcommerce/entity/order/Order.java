package com.lg.fresher.lgcommerce.entity.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lg.fresher.lgcommerce.constant.OrderStatus;
import com.lg.fresher.lgcommerce.entity.account.Account;
import com.lg.fresher.lgcommerce.entity.core.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * -------------------------------------------------------------------------
 * LG CNS Ecommerce
 * ------------------------------------------------------------------------
 *
 * @ Class Name : Order
 * @ Description : lg_ecommerce_be Order
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/19/2024
 * ------------------------------------------------------------------------
 * Modification Information
 * ------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/19/2024       63200502      first creation
 */
@Getter
@Setter
@Entity
@Table(name = "`order`", schema = "lgecommerce", indexes = {
        @Index(name = "account_id", columnList = "account_id"),
        @Index(name = "shipping_id", columnList = "shipping_id"),
        @Index(name = "payment_id", columnList = "payment_id")
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

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<OrderDetail> orderDetails;

    @Min(value = 0, message = "Số lượng đặt phải là một số nguyên dương")
    @Column(name = "total_amount")
    private Double totalAmount;

    @Length(max = 50)
    @Column(name = "recipient", length = 50)
    private String recipient;

    @Length(max = 320, message = "Độ dài tối đa của email là 320 ký tự")
    @Column(name = "email")
    private String email;

    @Length(max = 50)
    @Column(name = "phone", length = 50)
    private String phone;

    @Min(value = 0, message = "Phí vận chuyển phải là một số nguyên dương")
    @Column(name = "shipping_fee")
    private Double shippingFee;

    @Length(max = 500, message = "Độ dài tối đa của địa chỉ là 500 ký tự")
    @Column(name = "detail_address")
    private String detailAddress;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "is_guest_checkout")
    private Boolean isGuestCheckout;

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", account=" + account +
                ", shippingMethod=" + shippingMethod +
                ", paymentMethod=" + paymentMethod +
                ", orderStatus=" + orderStatus +
                ", orderDetails=" + orderDetails +
                ", totalAmount=" + totalAmount +
                ", recipient='" + recipient + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", shippingFee=" + shippingFee +
                ", detailAddress='" + detailAddress + '\'' +
                ", isDeleted=" + isDeleted +
                ", isGuestCheckout=" + isGuestCheckout +
                '}';
    }
}