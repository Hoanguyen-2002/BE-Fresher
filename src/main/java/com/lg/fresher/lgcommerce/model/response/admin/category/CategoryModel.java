package com.lg.fresher.lgcommerce.model.response.admin.category;


import lombok.*;

import java.time.LocalDateTime;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : CategoryModel
 * @ Description : lg_ecommerce_be CategoryModel
 * @ author lg_ecommerce_be Dev Team 63200485
 * @ since 11/6/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/6/2024       63200485      first creation */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryModel {

    private String categoryId;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

}
