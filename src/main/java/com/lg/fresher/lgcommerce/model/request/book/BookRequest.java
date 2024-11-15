package com.lg.fresher.lgcommerce.model.request.book;

import com.lg.fresher.lgcommerce.entity.book.*;
import com.lg.fresher.lgcommerce.entity.order.OrderDetail;
import com.lg.fresher.lgcommerce.entity.publisher.Publisher;
import com.lg.fresher.lgcommerce.entity.review.Review;
import com.lg.fresher.lgcommerce.model.request.author.AuthorRequest;
import com.lg.fresher.lgcommerce.model.request.category.CategoryRequest;
import com.lg.fresher.lgcommerce.model.request.property.PropertyRequest;
import com.lg.fresher.lgcommerce.model.request.publisher.PublisherRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : BookRequest
 * @ Description : lg_ecommerce_be BookRequest
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
public class BookRequest {
    private String bookId;

    @NotBlank(message = "Không được để trống tiêu đề")
    @Length(max = 255, message = "Độ dài tiêu dề quyền sách không được vượt quá 255 ký tự")
    private String title;

    @Length(max = 255, message = "Độ dài của link ảnh không được vượt quá 255 ký tự")
    private String thumbnail;

    private Boolean status;

    private String description;

    @NotNull(message = "Không được để trống số lượng")
    @Min(value = 0, message = "Số lượng phải là một số dương")
    private Integer quantity;

    @NotNull(message = "Không được để trống nhà xuất bản")
    private @Valid PublisherRequest publisher;

    @NotNull(message = "Không được để trống tác giả")
    private List<@Valid AuthorRequest> authors;

    private List<@Valid BookImageRequest> images;

    @NotNull(message = "Không được để trống danh mục sách")
    private List<@Valid CategoryRequest> categories;

    private List<@Valid PropertyRequest> properties;

    @NotNull(message = "Không được để trống giá sách")
    private @Valid PriceRequest price;
}
