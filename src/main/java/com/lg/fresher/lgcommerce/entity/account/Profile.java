package com.lg.fresher.lgcommerce.entity.account;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lg.fresher.lgcommerce.entity.core.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "profile", schema = "lgecommerce", indexes = {
        @Index(name = "account_id", columnList = "account_id")
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Profile extends BaseEntity {
    @Id
    @Size(max = 50)
    @Column(name = "profile_id", nullable = false, length = 50)
    private String profileId;

    @Size(max = 50)
    @Column(name = "fullname")
    private String fullname;

    @Size(max = 255)
    @Column(name = "avatar")
    private String avatar;

    @Size(max = 10)
    @Column(name = "phone", length = 10)
    private String phone;

    @OneToOne(mappedBy = "profile")
    private Account account;

    @Override
    public String toString() {
        return "Profile{" +
                "profileId='" + profileId + '\'' +
                ", fullname='" + fullname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    @PreUpdate
    public void onPreUpdate(){
        this.account.setUpdatedAt(LocalDateTime.now());
    }
}