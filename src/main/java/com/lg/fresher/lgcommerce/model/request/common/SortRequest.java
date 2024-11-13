package com.lg.fresher.lgcommerce.model.request.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
@NoArgsConstructor
public class SortRequest {
    private String sortField;
    private Sort.Direction sortDirection;
}
