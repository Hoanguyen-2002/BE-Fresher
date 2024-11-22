package com.lg.fresher.lgcommerce.model.request.order;

import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : SearchOrderRequest
 * @ Description : lg_ecommerce_be SearchOrderRequest
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/21/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/21/2024       63200502      first creation */
@Data
@NoArgsConstructor
public class SearchOrderRequest {
    private String searchKeyword;
    private String searchBy;
    private String sortRequest;
    private String status;
    @Min(value = 0, message = "Số trang không hợp lệ")
    private int pageNo;
    @Min(value = 5, message = "Số lượng thành phần hiển thị trong 1 trang không hợp lệ, tối thiểu là 5")
    private int pageSize;
}
