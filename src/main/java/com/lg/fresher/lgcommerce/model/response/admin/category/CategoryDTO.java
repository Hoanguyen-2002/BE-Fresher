package com.lg.fresher.lgcommerce.model.response.admin.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lg.fresher.lgcommerce.model.response.base.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : CategoryDTO
 * @ Description : lg_ecommerce_be CategoryDTO
 * @ author lg_ecommerce_be Dev Team 63200504
 * @ since 11/7/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/7/2024       63200504      first creation */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDTO extends BaseResponse {
    @JsonProperty("category_id")
    private String categoryId;

    private String name;
}
