package com.lg.fresher.lgcommerce.service.admin.order;

import com.lg.fresher.lgcommerce.constant.Status;
import com.lg.fresher.lgcommerce.entity.order.Order;
import com.lg.fresher.lgcommerce.exception.InvalidRequestException;
import com.lg.fresher.lgcommerce.mapping.order.OrderMapper;
import com.lg.fresher.lgcommerce.model.request.order.SearchOrderRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.common.MetaData;
import com.lg.fresher.lgcommerce.repository.order.OrderRepository;
import com.lg.fresher.lgcommerce.service.order.OrderSpecification;
import com.lg.fresher.lgcommerce.utils.SearchUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : AdminOrderServiceImpl
 * @ Description : lg_ecommerce_be AdminOrderServiceImpl
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/21/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/21/2024       63200502      add method to get list order */
@Service
@RequiredArgsConstructor
public class AdminOrderServiceImpl implements AdminOrderService{
    private static final Set<String> VALID_SORT_FIELD = Set.of("createdAt", "updatedAt");
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    /**
     * @param searchOrderRequest
     * @return
     */
    @Override
    public CommonResponse<Map<String, Object>> getListOrder(SearchOrderRequest searchOrderRequest) {
        String keyword = searchOrderRequest.getSearchKeyword();
        String searchBy = searchOrderRequest.getSearchBy();
        String sortRequest = searchOrderRequest.getSortRequest();
        String status = searchOrderRequest.getStatus();
        int pageNo = searchOrderRequest.getPageNo();
        int pageSize = searchOrderRequest.getPageSize();

        Specification<Order> orderSpecification = Specification
                .where(OrderSpecification.searchByKeyword(keyword, searchBy))
                .and(OrderSpecification.filterByOrderStatus(status));

        List<Sort.Order> orders = appendSort(sortRequest);

        Sort sort = Sort.by(orders);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        MetaData metaData = new MetaData();
        Page<Order> orderResponse = Page.empty();

        orderResponse = orderRepository.findAll(orderSpecification, pageable);

        metaData.setOffSet(pageNo);
        metaData.setTotalElements(orderResponse.getTotalElements());
        metaData.setLimit(pageSize);

        Map<String, Object> res = new HashMap<>();
        res.put("content", orderResponse.getContent().stream().map(orderMapper::toGetListOrderResponse));
        res.put("metaData", metaData);
        return CommonResponse.success(res);
    }

    /**
     *
     * @ Description : lg_ecommerce_be AdminOrderServiceImpl Member Field appendSort
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/22/2024           63200502    first creation
     *<pre>
     * @param sortRequest
     * @return  List<Sort.Order>
     */
    private List<Sort.Order> appendSort(String sortRequest){
        List<Sort.Order> orders = new ArrayList<>();
        if (sortRequest == null || sortRequest.isEmpty()) {
            orders.add(new Sort.Order(Sort.Direction.ASC, "createdAt"));
            return orders;
        }

        SearchUtil.parseSortRequest(sortRequest).forEach(request -> {
            if (!VALID_SORT_FIELD.contains(request.getSortField())) {
                throw new InvalidRequestException(Status.FAIL_SEARCH_INVALID_PARAM);
            }
            orders.add(new Sort.Order(request.getSortDirection(), request.getSortField()));
        });
        return orders;
    }

}
