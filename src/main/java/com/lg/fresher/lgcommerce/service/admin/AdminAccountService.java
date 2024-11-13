package com.lg.fresher.lgcommerce.service.admin;

import com.lg.fresher.lgcommerce.model.response.CommonResponse;

import java.util.Map;

public interface AdminAccountService {
    CommonResponse<Map<String, Object>> searchAccount(String keyword,
                                                      String sortRequest,
                                                      String filter,
                                                      int pageNo,
                                                      int pageSize);
}
