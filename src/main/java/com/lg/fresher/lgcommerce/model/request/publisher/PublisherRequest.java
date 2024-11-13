package com.lg.fresher.lgcommerce.model.request.publisher;

import jakarta.validation.constraints.NotBlank;
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
 * @ Class Name : PublisherRequest
 * @ Description : lg_ecommerce_be PublisherRequest
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
public class PublisherRequest {
    private String publisherId;

    @NotBlank
    @Length(max = 255, message = "Độ dài tên nhà xuất bản không được vượt quá 255 ký tự")
    private String name;
}
