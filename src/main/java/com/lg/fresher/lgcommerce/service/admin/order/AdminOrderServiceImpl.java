package com.lg.fresher.lgcommerce.service.admin.order;

import com.lg.fresher.lgcommerce.constant.OrderStatus;
import com.lg.fresher.lgcommerce.constant.SearchConstant;
import com.lg.fresher.lgcommerce.constant.Status;
import com.lg.fresher.lgcommerce.entity.order.Order;
import com.lg.fresher.lgcommerce.exception.InvalidRequestException;
import com.lg.fresher.lgcommerce.exception.book.BookOutOfStockException;
import com.lg.fresher.lgcommerce.exception.data.DataNotFoundException;
import com.lg.fresher.lgcommerce.mapping.order.OrderMapper;
import com.lg.fresher.lgcommerce.model.request.order.SearchOrderRequest;
import com.lg.fresher.lgcommerce.model.request.order.UpdateOrderStatusRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.StringResponse;
import com.lg.fresher.lgcommerce.model.response.common.MetaData;
import com.lg.fresher.lgcommerce.model.response.order.OrderDetailItem;
import com.lg.fresher.lgcommerce.repository.book.BookRepository;
import com.lg.fresher.lgcommerce.repository.order.OrderDetailRepository;
import com.lg.fresher.lgcommerce.repository.order.OrderRepository;
import com.lg.fresher.lgcommerce.service.order.OrderSpecification;
import com.lg.fresher.lgcommerce.utils.OrderUtil;
import com.lg.fresher.lgcommerce.utils.SearchUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * -------------------------------------------------------------------------
 * LG CNS Ecommerce
 * ------------------------------------------------------------------------
 *
 * @ Class Name : AdminOrderServiceImpl
 * @ Description : lg_ecommerce_be AdminOrderServiceImpl
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/21/2024
 * ------------------------------------------------------------------------
 * Modification Information
 * ------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/21/2024       63200502      add method to get list order
 * 11/25/2024       63200502      add method to update order status
 */
@Service
@RequiredArgsConstructor
public class AdminOrderServiceImpl implements AdminOrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderMapper orderMapper;
    private final BookRepository bookRepository;

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

        List<Sort.Order> orders = SearchUtil.appendOrderSort(sortRequest, SearchConstant.VALID_ORDER_SORT_FIELD);

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
     * @param orderId
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = BookOutOfStockException.class)
    public synchronized CommonResponse<StringResponse> acceptOrder(String orderId) {
        checkingOrderValidForUpdateStatus(orderId, OrderStatus.PROCESSING);

        List<OrderDetailItem> items = orderDetailRepository.getOrderDetailItemByOrderId(orderId);

        List<String> invalidOrderItemsId = new ArrayList<>();
        List<String> validOrderItemsId = new ArrayList<>();

        items.forEach(detailItem -> {
            if (detailItem.getQuantity() > detailItem.getStockQuantity()) {
                invalidOrderItemsId.add(detailItem.getOrderDetailId());
            } else {
                validOrderItemsId.add(detailItem.getOrderDetailId());
            }
        });

        if (!invalidOrderItemsId.isEmpty()) {
            orderRepository.appendMessage(orderId, Status.ACCEPT_ORDER_FAIL_ITEM_OUT_OF_STOCK.label());
            orderDetailRepository.appendMessage(invalidOrderItemsId, Status.ACCEPT_ORDER_FAIL_DETAIL_ITEM_OUT_OF_STOCK.label());
            orderDetailRepository.appendMessage(validOrderItemsId, OrderUtil.DEFAULT_DETAIL_ITEM_NOTE);
            throw new BookOutOfStockException(Status.ACCEPT_ORDER_FAIL_ITEM_OUT_OF_STOCK);
        }

        updateBookStock(orderId, OrderUtil.PLACE_ORDER);
        changeOrderStatus(orderId, OrderStatus.PROCESSING);
        orderRepository.appendMessage(orderId, Status.ACCEPT_ORDER_SUCCESS.label());
        orderDetailRepository.appendMessage(validOrderItemsId, OrderUtil.DEFAULT_DETAIL_ITEM_NOTE);

        return CommonResponse.success(Status.ACCEPT_ORDER_SUCCESS.label());
    }

    /**
     * @param orderId
     * @param updateOrderStatusRequest
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public CommonResponse<StringResponse> updateOrderStatus(String orderId, UpdateOrderStatusRequest updateOrderStatusRequest) {
        OrderStatus newStatus = OrderStatus.valueOf(updateOrderStatusRequest.getNewOrderStatus());
        checkingOrderValidForUpdateStatus(orderId, newStatus);
        if (newStatus == OrderStatus.DENIED) {
            updateBookStock(orderId, OrderUtil.CANCEL_ORDER);
            String message = updateOrderStatusRequest.getMessage();
            if (message == null || message.isEmpty()) {
                message = OrderUtil.DEFAULT_ADMIN_REASON_DENIED_ORDER;
            }
            orderRepository.appendMessage(orderId, message);
        }
        changeOrderStatus(orderId, newStatus);
        return CommonResponse.success(Status.UPDATE_ORDER_STATUS_SUCCESS.label());
    }

    /**
     * @ Description : lg_ecommerce_be AdminOrderServiceImpl Member Field checkingOrderValidForUpdateStatus
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/25/2024           63200502    first creation
     * <pre>
     * @param orderId
     * @param orderStatus
     */
    private void checkingOrderValidForUpdateStatus(String orderId, OrderStatus orderStatus) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new DataNotFoundException(Status.ACCEPT_ORDER_FAIL_ORDER_NOT_FOUND.label()));
        if (!OrderUtil.isOrderStatusValid(order.getOrderStatus(), orderStatus))
            throw new InvalidRequestException(Status.ACCEPT_ORDER_FAIL_ORDER_STATUS_HAVE_CHANGED);
    }

    /**
     *
     * @ Description : lg_ecommerce_be AdminOrderServiceImpl Member Field updateBookStock
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/26/2024           63200502    first creation
     *<pre>
     * @param orderId
     * @param adjustment
     */
    private void updateBookStock(String orderId, int adjustment) {
        bookRepository.updateBookStock(orderId, adjustment);
    }

    /**
     * @ Description : lg_ecommerce_be AdminOrderServiceImpl Member Field changeOrderStatus
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/25/2024           63200502    first creation
     * <pre>
     * @param orderId
     * @param orderStatus
     */
    private void changeOrderStatus(String orderId, OrderStatus orderStatus) {
        orderRepository.updateOrderStatus(orderId, orderStatus);
    }
}
