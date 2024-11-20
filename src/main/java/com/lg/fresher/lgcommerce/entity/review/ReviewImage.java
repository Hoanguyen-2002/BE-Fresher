package com.lg.fresher.lgcommerce.entity.review;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lg.fresher.lgcommerce.entity.core.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : ReviewImage
 * @ Description : lg_ecommerce_be ReviewImage
 * @ author lg_ecommerce_be Dev Team 63200485
 * @ since 11/20/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/20/2024       63200485      first creation */
@Getter
@Setter
@Entity
@Table(name = "review_image", schema = "lgecommerce", indexes = {
        @Index(name = "review_id", columnList = "review_id")
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewImage extends BaseEntity {
    @Id
    @Size(max = 50)
    @Column(name = "review_image_id", nullable = false, length = 50)
    private String reviewImageId;

    @Size(max = 255)
    @Column(name = "image_url")
    private String imageUrl;

    @Override
    public String toString() {
        return "ReviewImage{" +
                "reviewImageId='" + reviewImageId + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}