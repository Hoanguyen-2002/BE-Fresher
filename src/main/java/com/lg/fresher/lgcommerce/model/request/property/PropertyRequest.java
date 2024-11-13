package com.lg.fresher.lgcommerce.model.request.property;

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
 * @ Class Name : PropertyRequest
 * @ Description : lg_ecommerce_be PropertyRequest
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
public class PropertyRequest {
    private String propertyId;

    @NotBlank
    @Length(max = 50, message = "Độ dài của tên tên thuộc tính không được vượt quá 50 ký tự")
    private String name;

    @NotBlank
    @Length(max = 255, message = "Giá trị của thuộc tính không được vượt quá 255 ký tự")
    private String value;
}
