package com.lg.fresher.lgcommerce.utils;

import com.lg.fresher.lgcommerce.model.request.common.SortRequest;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : SearchUtil
 * @ Description : lg_ecommerce_be SearchUtil
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/12/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/12/2024       63200502      first creation */
@UtilityClass
public class SearchUtil {
    /**
     *
     * @ Description : lg_ecommerce_be SearchUtil Member Field parseSortRequest
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/12/2024           63200502    first creation
     *<pre>
     * @param sortString
     * @return  List<SortRequest>
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
                sortRequest.setSortDirection(Sort.Direction.ASC); // default
            }
            sortRequestList.add(sortRequest);
        }
        return sortRequestList;
    }
}
