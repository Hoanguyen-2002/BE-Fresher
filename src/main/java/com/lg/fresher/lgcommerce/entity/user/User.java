package com.lg.fresher.lgcommerce.entity.user;

import com.lg.fresher.lgcommerce.entity.core.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Accessors(chain = true)
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @Column(name = "user_id", length = 100, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;

    @Column(name= "user_name", nullable = false)
    private String userName;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @Column(name = "user_role", nullable = false)
    private String userRole;

    public User(String userNm, String email, String password, String userRole) {
        this.userName = userNm;
        this.userEmail = email;
        this.userPassword = password;
        this.userRole = userRole;
    }
}
