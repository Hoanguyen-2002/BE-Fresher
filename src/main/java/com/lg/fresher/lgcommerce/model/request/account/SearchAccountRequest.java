package com.lg.fresher.lgcommerce.model.request.account;

import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : SearchAccountRequest
 * @ Description : lg_ecommerce_be SearchAccountRequest
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/13/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/13/2024       63200502      first creation
 * */
@Data
@NoArgsConstructor
public class SearchAccountRequest {
    private String keyword;
    private String sortRequest;
    private String filterRequest;
    @Min(value = 0, message = "Số trang không hợp lệ")
    private int pageNo;
    @Min(value = 5, message = "Số lượng thành phần hiển thị trong 1 trang không hợp lệ, tối thiểu là 5")
    private int pageSize;
}
