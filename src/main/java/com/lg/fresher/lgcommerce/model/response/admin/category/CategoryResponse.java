package com.lg.fresher.lgcommerce.model.response.admin.category;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lg.fresher.lgcommerce.model.response.common.MetaData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import lombok.Builder;

import java.util.List;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : CategoryResponse
 * @ Description : lg_ecommerce_be CategoryResponse
 * @ author lg_ecommerce_be Dev Team 63200485
 * @ since 11/6/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/6/2024       63200485      first creation */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryResponse {

    @JsonProperty("category_id")
    private String categoryId;
    private String name;
    private List<CategoryModel> content;
    private MetaData metadata;

}