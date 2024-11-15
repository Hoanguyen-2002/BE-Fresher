package com.lg.fresher.lgcommerce.model.request.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : CategoryRequest
 * @ Description : lg_ecommerce_be CategoryRequest
 * @ author lg_ecommerce_be Dev Team 63200504
 * @ since 11/8/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/8/2024       63200504      first creation */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequest {
    @NotBlank(message = "Không được để trống mã của danh mục")
    @Length(max = 50, message = "Độ dài của tên mã dạnh mục không được vượt quá 50 ký tự")
    private String categoryId;

    @NotBlank(message = "Không được để trống tên danh mục")
    @Size(max = 255, message = "Độ dài của tên danh mục không được vượt quá 255 ký tự")
    private String name;
}
