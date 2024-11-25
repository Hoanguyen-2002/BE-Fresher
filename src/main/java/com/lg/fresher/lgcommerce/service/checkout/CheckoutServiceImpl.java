package com.lg.fresher.lgcommerce.service.checkout;

import com.lg.fresher.lgcommerce.constant.OrderStatus;
import com.lg.fresher.lgcommerce.constant.Status;
import com.lg.fresher.lgcommerce.entity.book.Book;
import com.lg.fresher.lgcommerce.entity.order.Order;
import com.lg.fresher.lgcommerce.entity.order.OrderDetail;
import com.lg.fresher.lgcommerce.model.request.checkout.CheckoutItemRequest;
import com.lg.fresher.lgcommerce.model.request.checkout.CheckoutRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.checkout.CheckoutItemResponse;
import com.lg.fresher.lgcommerce.model.response.checkout.CheckoutResponse;
import com.lg.fresher.lgcommerce.repository.book.PriceRepository;
import com.lg.fresher.lgcommerce.repository.order.OrderDetailRepository;
import com.lg.fresher.lgcommerce.repository.order.OrderRepository;
import com.lg.fresher.lgcommerce.utils.UUIDUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * -------------------------------------------------------------------------
 * LG CNS Ecommerce
 * ------------------------------------------------------------------------
 *
 * @ Class Name : CheckoutServiceImpl
 * @ Description : lg_ecommerce_be CheckoutServiceImpl
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/18/2024
 * ------------------------------------------------------------------------
 * Modification Information
 * ------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/18/2024       63200502      first creation
 */
@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {
    private final PriceRepository priceRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    /**
     * @param checkoutRequest
     * @return
     */
    @Override
    @Transactional
    public CommonResponse<Map<String, Object>> captureOrder(CheckoutRequest checkoutRequest) {
        boolean isOrderValid = true;
        double totalPrice = 0.0;
        List<String> bookIds = checkoutRequest.getItemList().stream()
                .map(CheckoutItemRequest::getBookId)
                .toList();

        List<CheckoutItemResponse> items = priceRepository.captureListBookPrice(bookIds);

        Map<String, Object> response = new HashMap<>();
        Map<String, CheckoutItemRequest> quantityMap = new HashMap<>();
        for (CheckoutItemRequest request : checkoutRequest.getItemList()) {
            quantityMap.put(request.getBookId(), request);
        }

        for (CheckoutItemResponse item : items) {
            CheckoutItemRequest requestItem = quantityMap.get(item.getId());
            item.setQuantity(requestItem.getQuantity());
            item.setTotalPrice(calculateTotalPrice(
                    item.getOriginalPrice(),
                    item.getSalePrice(),
                    requestItem.getQuantity()));
            totalPrice += item.getTotalPrice();
            if (item.getOriginalPrice() != requestItem.getOriginalPrice() || item.getSalePrice() != requestItem.getSalePrice()) {
                item.setNote(Status.CHECKOUT_FAIL_PRICE_PRODUCT_HAVE_CHANGED.label());
                isOrderValid = false;
            }

        }
        CheckoutResponse res = new CheckoutResponse();
        res.setItemResponseList(items);

        if (!isOrderValid || items.isEmpty() || items.size() != checkoutRequest.getItemList().size()) {
            response.put("content", res);
            return CommonResponse.fail(
                    Status.CHECKOUT_FAIL_PRODUCT_HAVE_CHANGED.label(),
                    Status.CHECKOUT_FAIL_PRODUCT_HAVE_CHANGED.code(),
                    response);
        }
        res.setOrderId(generateDaftOrder(items, totalPrice));
        response.put("content", res);
        return CommonResponse.success(response);
    }

    /**
     * @ Description : lg_ecommerce_be CheckoutServiceImpl Member Field generateDaftOrder
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/19/2024           63200502    first creation
     * <pre>
     * @param itemResponseList
     * @return String
     */
    private String generateDaftOrder(List<CheckoutItemResponse> itemResponseList, double totalPrice) {
        Order order = Order.builder()
                .orderId(UUIDUtil.generateId())
                .orderStatus(OrderStatus.DRAFT)
                .totalAmount(totalPrice)
                .build();
        orderRepository.save(order);
        generateOrderDetail(order, itemResponseList);
        return order.getOrderId();
    }

    /**
     * @ Description : lg_ecommerce_be CheckoutServiceImpl Member Field generateOrderDetail
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/19/2024           63200502    first creation
     * <pre>
     * @param order
     * @param itemResponseList
     */
    private void generateOrderDetail(Order order, List<CheckoutItemResponse> itemResponseList) {
        if (itemResponseList == null || itemResponseList.isEmpty()) return;

        orderDetailRepository.saveAll(itemResponseList
                .stream()
                .map(item -> OrderDetail.builder()
                        .orderDetailId(UUIDUtil.generateId())
                        .quantity(item.getQuantity())
                        .basePrice(item.getOriginalPrice())
                        .discountPrice(item.getSalePrice())
                        .finalPrice(item.getOriginalPrice() - item.getSalePrice())
                        .total(item.getTotalPrice())
                        .isReviewed(false)
                        .order(order)
                        .book(Book.builder().bookId(item.getId()).build())
                        .build())
                .toList());
    }

    /**
     * @ Description : lg_ecommerce_be CheckoutServiceImpl Member Field calculateTotalPrice
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/19/2024           63200502    first creation
     * <pre>
     * @param basePrice
     * @param discountPrice
     * @param quantity
     * @return Double
     */
    private Double calculateTotalPrice(double basePrice, double discountPrice, int quantity) {
        return (basePrice - discountPrice) * quantity;
    }

}
