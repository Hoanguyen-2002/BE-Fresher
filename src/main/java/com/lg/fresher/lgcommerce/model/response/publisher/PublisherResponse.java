package com.lg.fresher.lgcommerce.model.response.publisher;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lg.fresher.lgcommerce.model.response.base.BaseResponse;
import com.lg.fresher.lgcommerce.model.response.book.BookResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : PublisherResponse
 * @ Description : lg_ecommerce_be PublisherResponse
 * @ author lg_ecommerce_be Dev Team 63200504
 * @ since 11/6/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/6/2024       63200504      first creation */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PublisherResponse {
    @JsonProperty("publisher_id")
    private String publisherId;

    private String name;

    private List<BookResponse> books;

}
