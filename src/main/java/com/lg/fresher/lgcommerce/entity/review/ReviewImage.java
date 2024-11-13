package com.lg.fresher.lgcommerce.entity.review;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lg.fresher.lgcommerce.entity.core.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "review_id")
    private Review review;

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