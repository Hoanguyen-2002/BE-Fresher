package com.lg.fresher.lgcommerce.model.request.author;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : AuthorRequest
 * @ Description : lg_ecommerce_be AuthorRequest
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
public class AuthorRequest {

    private String bookAuthorId;

    @NotBlank(message = "Không được để trống tên tác giả")
    @Size(max = 255, message = "Độ dài của tên tác giả không được vượt quá 255 ký tự")
    private String name;
}
