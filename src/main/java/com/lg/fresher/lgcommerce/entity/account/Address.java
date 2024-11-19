package com.lg.fresher.lgcommerce.entity.account;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lg.fresher.lgcommerce.entity.core.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "address", schema = "lgecommerce", indexes = {
        @Index(name = "account_id", columnList = "account_id")
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address extends BaseEntity {
    @Id
    @Column(name = "address_id", nullable = false, length = 50)
    private String addressId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "province")
    private String province;

    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String district;

    @Column(name = "detail_address")
    private String detailAddress;

    @Override
    public String toString() {
        return "Address{" +
                "addressId='" + addressId + '\'' +
                ", account=" + account +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                '}';
    }

    @PreUpdate
    public void onPreUpdate(){
        this.account.setUpdatedAt(LocalDateTime.now());
    }
}