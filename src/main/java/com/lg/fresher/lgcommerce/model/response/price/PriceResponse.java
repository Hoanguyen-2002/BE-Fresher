package com.lg.fresher.lgcommerce.model.response.price;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lg.fresher.lgcommerce.model.response.base.BaseResponse;
import com.lg.fresher.lgcommerce.model.response.book.BookResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : PriceResponse
 * @ Description : lg_ecommerce_be PriceResponse
 * @ author lg_ecommerce_be Dev Team 63200504
 * @ since 11/6/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/6/2024       63200504      first creation */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PriceResponse extends BaseResponse {
    @JsonProperty("price_id")
    private String priceId;

    @JsonProperty("base_price")
    private Double basePrice;

    @JsonProperty("discount_price")
    private Double discountPrice;

    private BookResponse book;
}
