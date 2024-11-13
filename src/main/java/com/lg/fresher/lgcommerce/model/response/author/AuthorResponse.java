package com.lg.fresher.lgcommerce.model.response.author;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lg.fresher.lgcommerce.model.response.base.BaseResponse;
import com.lg.fresher.lgcommerce.model.response.book.BookResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : AuthorResponse
 * @ Description : lg_ecommerce_be AuthorResponse
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
public class AuthorResponse extends BaseResponse {

    @JsonProperty("author_id")
    private String authorId;

    private String name;

    private List<BookResponse> books;

}
