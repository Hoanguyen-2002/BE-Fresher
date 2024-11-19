package com.lg.fresher.lgcommerce.model.request.book;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : PriceRequest
 * @ Description : lg_ecommerce_be PriceRequest
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
public class PriceRequest {

    private String priceId;

    @NotNull(message = "Không được để trống giá gốc")
    @Min(value = 0, message = "Giá bán cơ sở không được là số âm")
    private Double basePrice;

    @Min(value = 0, message = "Giá giảm không được là số âm")
    private Double discountPrice;
}
