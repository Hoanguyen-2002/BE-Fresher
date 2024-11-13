package com.lg.fresher.lgcommerce.entity.role;

import com.lg.fresher.lgcommerce.entity.core.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "role", schema = "lgecommerce")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role extends BaseEntity {
    @Id
    @Size(max = 50)
    @Column(name = "role_id", nullable = false, length = 50)
    private String roleId;

    @Size(max = 50)
    @Column(name = "name")
    private String name;

}