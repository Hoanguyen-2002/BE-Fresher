package com.lg.fresher.lgcommerce.model.request.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : ReviewImageRequest
 * @ Description : lg_ecommerce_be ReviewImageRequest
 * @ author lg_ecommerce_be Dev Team 63200485
 * @ since 11/20/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/20/2024       63200485      first creation */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewImageRequest {
    @Length(max = 255, message = "Độ dài link ảnh không được vượt quá 255 ký tự")
    private String imageUrl;
}
