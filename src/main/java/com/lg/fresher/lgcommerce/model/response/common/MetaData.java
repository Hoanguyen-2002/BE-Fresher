package com.lg.fresher.lgcommerce.model.response.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : MetaData
 * @ Description : lg_ecommerce_be MetaData
 * @ author lg_ecommerce_be Dev Team 63200485
 * @ since 11/13/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/13/2024       63200485      first creation */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetaData {
    private long totalElements;
    private int offSet;
    private int limit;
}
