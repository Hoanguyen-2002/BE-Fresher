package com.lg.fresher.lgcommerce.utils;

import com.lg.fresher.lgcommerce.constant.Status;
import com.lg.fresher.lgcommerce.exception.InvalidRequestException;
import com.lg.fresher.lgcommerce.model.request.common.SortRequest;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * -------------------------------------------------------------------------
 * LG CNS Ecommerce
 * ------------------------------------------------------------------------
 *
 * @ Class Name : SearchUtil
 * @ Description : lg_ecommerce_be SearchUtil
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/12/2024
 * ------------------------------------------------------------------------
 * Modification Information
 * ------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/12/2024       63200502      first creation
 * 11/22/2024       63200502      add method to handle order sort
 */
@UtilityClass
public class SearchUtil {
    /**
     * @ Description : lg_ecommerce_be SearchUtil Member Field parseSortRequest
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/12/2024           63200502    first creation
     * 11/13/2024           63200502    fix default value
     * <pre>
     * @param sortString
     * @return List<SortRequest>
     */
    public List<SortRequest> parseSortRequest(String sortString) {
        List<SortRequest> sortRequestList = new ArrayList<>();

        String[] fields = sortString.split(",");
        for (String field : fields) {
            SortRequest sortRequest = new SortRequest();
            if (field.startsWith("+")) {
                sortRequest.setSortField(field.substring(1));
                sortRequest.setSortDirection(Sort.Direction.ASC);
            } else if (field.startsWith("-")) {
                sortRequest.setSortField(field.substring(1));
                sortRequest.setSortDirection(Sort.Direction.DESC);
            } else {
                sortRequest.setSortField(field);
                sortRequest.setSortDirection(Sort.Direction.DESC); // default
            }
            sortRequestList.add(sortRequest);
        }
        return sortRequestList;
    }

    /**
     * @ Description : lg_ecommerce_be AccountServiceImpl Member Field appendSort
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/22/2024           63200502    first creation
     * <pre>
     * @param sortRequest
     * @return List<Sort.Order>
     */
    public List<Sort.Order> appendOrderSort(String sortRequest, Set<String> validSortFields) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortRequest == null || sortRequest.isEmpty()) {
            orders.add(new Sort.Order(Sort.Direction.ASC, "createdAt"));
            return orders;
        }

        SearchUtil.parseSortRequest(sortRequest).forEach(request -> {
            if (!validSortFields.contains(request.getSortField())) {
                throw new InvalidRequestException(Status.FAIL_SEARCH_INVALID_PARAM);
            }
            orders.add(new Sort.Order(request.getSortDirection(), request.getSortField()));
        });
        return orders;
    }
}
