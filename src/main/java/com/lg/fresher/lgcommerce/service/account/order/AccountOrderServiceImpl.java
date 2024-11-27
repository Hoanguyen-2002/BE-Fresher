package com.lg.fresher.lgcommerce.service.account.order;

import com.lg.fresher.lgcommerce.constant.OrderStatus;
import com.lg.fresher.lgcommerce.constant.Status;
import com.lg.fresher.lgcommerce.entity.account.Account;
import com.lg.fresher.lgcommerce.entity.order.Order;
import com.lg.fresher.lgcommerce.exception.InvalidRequestException;
import com.lg.fresher.lgcommerce.exception.data.DataNotFoundException;
import com.lg.fresher.lgcommerce.model.request.account.CancelOrderRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.StringResponse;
import com.lg.fresher.lgcommerce.repository.order.OrderRepository;
import com.lg.fresher.lgcommerce.service.account.AccountService;
import com.lg.fresher.lgcommerce.utils.OrderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * -------------------------------------------------------------------------
 * LG CNS Ecommerce
 * ------------------------------------------------------------------------
 *
 * @ Class Name : AccountOrderServiceImpl
 * @ Description : lg_ecommerce_be AccountOrderServiceImpl
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/26/2024
 * ------------------------------------------------------------------------
 * Modification Information
 * ------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/26/2024       63200502      first creation
 */
@Service
@RequiredArgsConstructor
public class AccountOrderServiceImpl implements AccountOrderService {
    private final AccountService accountService;
    private final OrderRepository orderRepository;

    /**
     * @param orderId
     * @param cancelOrderRequest
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CommonResponse<StringResponse> cancelOrder(String orderId, CancelOrderRequest cancelOrderRequest) {
        Account account = accountService.getAccountFromContext();
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new DataNotFoundException(Status.CANCEL_ORDER_FAIL_ORDER_NOT_FOUND.label())
        );

        checkingOrderValidForCancel(account.getAccountId(), order);

        order.setOrderStatus(OrderStatus.CANCEL);
        String message = cancelOrderRequest.getMessage();
        if (message == null || message.isEmpty()) {
            message = OrderUtil.DEFAULT_USER_REASON_CANCEL_ORDER;
        }
        order.setNote(message);
        return CommonResponse.success(Status.CANCEL_ORDER_SUCCESS.label());
    }

    /**
     * @ Description : lg_ecommerce_be AccountOrderServiceImpl Member Field checkingOrderValidForCancel
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/26/2024           63200502    first creation
     * <pre>
     * @param accountId
     * @param order
     */
    private void checkingOrderValidForCancel(String accountId, Order order) {
        if (order.getOrderStatus() != OrderStatus.PENDING) {
            throw new InvalidRequestException(Status.CANCEL_ORDER_FAIL_ORDER_IS_PROCESSING);
        }
        if (order.getAccount() == null || !order.getAccount().getAccountId().equals(accountId)) {
            throw new InvalidRequestException(Status.CANCEL_ORDER_FAIL_USER_IS_NOT_OWNER);
        }
    }
}
