package com.lg.fresher.lgcommerce.entity.category;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lg.fresher.lgcommerce.entity.book.BookCategory;
import com.lg.fresher.lgcommerce.entity.core.BaseEntity;
import com.lg.fresher.lgcommerce.utils.UUIDUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : Category
 * @ Description : lg_ecommerce_be Category
 * @ author lg_ecommerce_be Dev Team 63200485
 * @ since 11/6/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/6/2024       63200485      first creation */
@SuperBuilder
@Getter
@Setter
@Entity
@Table(name = "category", schema = "lgecommerce")
@AllArgsConstructor
@Builder
public class Category extends BaseEntity {
    @Id
    @Size(max = 50)
    @Column(name = "category_id", nullable = false, length = 50)
    private String categoryId;

    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "category")
    @JsonManagedReference
    private List<BookCategory> categoryBooks;

    @Override
    public String toString() {
        return "Category{" +
                "categoryId='" + categoryId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }


    public Category() {
            this.categoryId = UUIDUtil.generateId();
        }
}