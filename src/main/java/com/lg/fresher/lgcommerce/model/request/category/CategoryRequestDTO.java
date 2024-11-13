package com.lg.fresher.lgcommerce.model.request.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : CategoryRequestDTO
 * @ Description : lg_ecommerce_be CategoryRequestDTO
 * @ author lg_ecommerce_be Dev Team 63200485
 * @ since 11/13/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/13/2024       63200485      first creation */
@Data
public class CategoryRequestDTO {
    @NotBlank
    private String name;
}
