package com.lg.fresher.lgcommerce.service.order;

import com.lg.fresher.lgcommerce.config.security.UserDetailsImpl;
import com.lg.fresher.lgcommerce.constant.OrderStatus;
import com.lg.fresher.lgcommerce.constant.Status;
import com.lg.fresher.lgcommerce.entity.account.Account;
import com.lg.fresher.lgcommerce.entity.order.Order;
import com.lg.fresher.lgcommerce.entity.order.OrderDetail;
import com.lg.fresher.lgcommerce.entity.order.PaymentMethod;
import com.lg.fresher.lgcommerce.entity.order.ShippingMethod;
import com.lg.fresher.lgcommerce.exception.InvalidRequestException;
import com.lg.fresher.lgcommerce.exception.data.DataNotFoundException;
import com.lg.fresher.lgcommerce.mapping.order.OrderDetailMapper;
import com.lg.fresher.lgcommerce.model.request.order.ConfirmOrderRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.checkout.CheckoutItemResponse;
import com.lg.fresher.lgcommerce.model.response.order.ConfirmOrderResponse;
import com.lg.fresher.lgcommerce.repository.checkout.PaymentMethodRepository;
import com.lg.fresher.lgcommerce.repository.checkout.ShippingMethodRepository;
import com.lg.fresher.lgcommerce.repository.order.OrderDetailRepository;
import com.lg.fresher.lgcommerce.repository.order.OrderRepository;
import com.lg.fresher.lgcommerce.service.email.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
 * @ Class Name : OrderServiceImpl
 * @ Description : lg_ecommerce_be OrderServiceImpl
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/20/2024
 * ------------------------------------------------------------------------
 * Modification Information
 * ------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/20/2024       63200502      add confirm order method
 * 11/21/2024       63200502      add get order detail
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ShippingMethodRepository shippingMethodRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final EmailService emailService;
    private final OrderDetailMapper orderDetailMapper;

    /**
     * @param confirmOrderRequest
     * @return
     */
    @Override
    @Transactional
    public CommonResponse<Map<String, Object>> confirmOrder(ConfirmOrderRequest confirmOrderRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Order order = orderRepository.findById(confirmOrderRequest.getOrderId()).orElseThrow(() ->
                new DataNotFoundException(Status.ORDER_FAIL_ORDER_NOT_FOUND.label()));

        if (order.getOrderStatus() != OrderStatus.DAFT) {
            throw new InvalidRequestException(Status.ORDER_FAIL_ORDER_HAVE_PLACED);
        }

        ShippingMethod shippingMethod = shippingMethodRepository.findById(confirmOrderRequest.getShippingMethodId())
                .orElseThrow(() -> new DataNotFoundException(Status.ORDER_FAIL_SHIPPING_METHOD_NOT_FOUND.label()));

        PaymentMethod paymentMethod = paymentMethodRepository.findById(confirmOrderRequest.getPaymentMethodId())
                .orElseThrow(() -> new DataNotFoundException(Status.ORDER_FAIL_PAYMENT_METHOD_NOT_FOUND.label()));

        appendShipping(order, shippingMethod);
        appendPayment(order, paymentMethod);
        appendRecipentDetail(order, confirmOrderRequest);

        if (!authentication.getPrincipal().equals("anonymousUser")) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            order.setAccount(Account.builder().accountId(userDetails.getUserId()).build());
            order.setIsGuestCheckout(false);
        } else {
            order.setIsGuestCheckout(true);
        }

        order.setOrderStatus(OrderStatus.PENDING);
        order.getOrderDetails();
        sendNotifyOrder(order);

        ConfirmOrderResponse confirmOrderResponse = new ConfirmOrderResponse();
        confirmOrderResponse.setOrderId(order.getOrderId());
        confirmOrderResponse.setTimeOrdered(order.getCreatedAt());

        Map<String, Object> response = new HashMap<>();
        response.put("content", confirmOrderResponse);

        return CommonResponse.success(response);
    }

    /**
     * @param orderId
     * @return
     */
    @Override
    public CommonResponse<Map<String, Object>> getOrderDetail(String orderId) {
        List<CheckoutItemResponse> orderDetailList = orderDetailRepository.getAllOrderDetailByOrderId(orderId);

        Map<String, Object> response = new HashMap<>();
        response.put("content", orderDetailList);
        return CommonResponse.success(response);
    }

    /**
     * @ Description : lg_ecommerce_be OrderServiceImpl Member Field appendShipping
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/20/2024           63200502    first creation
     * <pre>
     * @param order
     * @param shippingMethod
     */
    private void appendShipping(Order order, ShippingMethod shippingMethod) {
        order.setShippingMethod(shippingMethod);
        order.setShippingFee(shippingMethod.getShippingFee());
        updateTotalPrice(order, shippingMethod.getShippingFee());
    }

    /**
     * @ Description : lg_ecommerce_be OrderServiceImpl Member Field appendPayment
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/20/2024           63200502    first creation
     * <pre>
     * @param order
     * @param paymentMethod
     */
    private void appendPayment(Order order, PaymentMethod paymentMethod) {
        order.setPaymentMethod(paymentMethod);
    }

    /**
     * @ Description : lg_ecommerce_be OrderServiceImpl Member Field appendRecipentDetail
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/20/2024           63200502    first creation
     * <pre>
     * @param order
     * @param confirmOrderRequest
     */
    private void appendRecipentDetail(Order order, ConfirmOrderRequest confirmOrderRequest) {
        order.setPhone(confirmOrderRequest.getPhone());
        order.setRecipient(confirmOrderRequest.getRecipient());
        order.setEmail(confirmOrderRequest.getEmail());
        order.setDetailAddress(confirmOrderRequest.getDetailAddress());
    }

    /**
     * @ Description : lg_ecommerce_be OrderServiceImpl Member Field updateTotalPrice
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/20/2024           63200502    first creation
     * <pre>
     * @param order
     * @param shippingFee
     */
    private void updateTotalPrice(Order order, double shippingFee) {
        order.setTotalAmount(order.getTotalAmount() + shippingFee);
    }

    /**
     * @ Description : lg_ecommerce_be OrderServiceImpl Member Field sendNotifyOrder
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/20/2024           63200502    first creation
     * <pre>
     * @param order
     */
    private void sendNotifyOrder(Order order) {
        try {
            emailService.sendNotifyPlaceOrderSuccess(order.getEmail(), order);
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }

}
