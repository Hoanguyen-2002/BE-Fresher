package com.lg.fresher.lgcommerce.service.admin.account;

import com.lg.fresher.lgcommerce.model.request.account.SearchAccountRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;

import java.util.Map;

public interface AdminAccountService {
    CommonResponse<Map<String, Object>> searchAccount(SearchAccountRequest searchAccountRequest);
}
