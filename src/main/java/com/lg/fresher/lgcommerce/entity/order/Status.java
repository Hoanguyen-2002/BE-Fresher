package com.lg.fresher.lgcommerce.entity.order;

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
@Table(name = "status", schema = "lgecommerce")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Status extends BaseEntity {
    @Id
    @Size(max = 50)
    @Column(name = "status_id", nullable = false, length = 50)
    private String statusId;

    @Size(max = 50)
    @Column(name = "status_name")
    private String statusName;

    @Size(max = 255)
    @Column(name = "description")
    private String description;
}