package com.lg.fresher.lgcommerce.model.request.review;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class ReviewImageRequest {
    @Length(max = 255, message = "Độ dài link ảnh không được vượt quá 255 ký tự")
    private String imageUrl;
}
