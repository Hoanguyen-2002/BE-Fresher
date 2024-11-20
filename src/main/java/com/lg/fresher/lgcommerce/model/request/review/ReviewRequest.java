package com.lg.fresher.lgcommerce.model.request.review;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : ReviewRequest
 * @ Description : lg_ecommerce_be ReviewRequest
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
public class ReviewRequest {
    @NotNull(message = "Xếp loại sản phẩm không được trống")
    private Integer rating;

    @NotBlank(message = "Không được để trống bình luận")
    private String comment;

    private List<ReviewImageRequest> images;
}