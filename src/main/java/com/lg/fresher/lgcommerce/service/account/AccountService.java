package com.lg.fresher.lgcommerce.service.account;

import com.lg.fresher.lgcommerce.model.request.account.UpdateAccountRequest;
import com.lg.fresher.lgcommerce.model.request.order.SearchOrderRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.StringResponse;

import java.util.Map;

public interface AccountService {
    CommonResponse<Map<String, Object>> getAccountInfo(String id);

    CommonResponse<StringResponse> updateAccountInfo(UpdateAccountRequest updateAccountRequest);

    CommonResponse<Map<String, Object>> getMyInfo();

    CommonResponse<Map<String, Object>> getMyOrders(SearchOrderRequest searchOrderRequest);

}
