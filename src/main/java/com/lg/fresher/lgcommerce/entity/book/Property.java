package com.lg.fresher.lgcommerce.entity.book;

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
@Table(name = "property", schema = "lgecommerce")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Property extends BaseEntity {
    @Id
    @Size(max = 50)
    @Column(name = "property_id", nullable = false, length = 50)
    private String propertyId;

    @Size(max = 255)
    @Column(name = "name")
    private String name;
}