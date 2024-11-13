package com.lg.fresher.lgcommerce.service.admin;

import com.lg.fresher.lgcommerce.constant.Status;
import com.lg.fresher.lgcommerce.entity.account.Account;
import com.lg.fresher.lgcommerce.exception.data.DataNotFoundException;
import com.lg.fresher.lgcommerce.mapping.account.AccountMapper;
import com.lg.fresher.lgcommerce.model.request.common.SortRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.common.MetaData;
import com.lg.fresher.lgcommerce.repository.account.AccountRepository;
import com.lg.fresher.lgcommerce.service.account.AccountSpecification;
import com.lg.fresher.lgcommerce.utils.SearchUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * -------------------------------------------------------------------------
 * LG CNS Ecommerce
 * ------------------------------------------------------------------------
 *
 * @ Class Name : AdminServiceImpl
 * @ Description : lg_ecommerce_be AdminServiceImpl
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/11/2024
 * ------------------------------------------------------------------------
 * Modification Information
 * ------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/11/2024       63200502      first creation
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AdminAccountServiceImpl implements AdminAccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    /**
     * @param keyword
     * @param sortRequest
     * @param filterRequest
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public CommonResponse<Map<String, Object>> searchAccount(String keyword,
                                                             String sortRequest,
                                                             String filterRequest,
                                                             int pageNo,
                                                             int pageSize) {
        Specification<Account> accountSpecification = Specification.where(AccountSpecification.hasFullNameLike(keyword));

        List<Sort.Order> orders = new ArrayList<>();
        if (sortRequest != null && !sortRequest.isEmpty()) {
            List<SortRequest> sortRequestList = SearchUtil.parseSortRequest(sortRequest);
            for (SortRequest request : sortRequestList) {
                Sort.Direction direction = request.getSortDirection();
                orders.add(new Sort.Order(direction, "profile." + request.getSortField()));
            }
        } else {
            orders.add(new Sort.Order(Sort.Direction.ASC, "createdAt"));
        }
        Sort sort = Sort.by(orders);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        MetaData metaData = new MetaData();
        Page<Account> accounts = Page.empty();

        accounts = accountRepository.findAll(accountSpecification, pageable);

        if(accounts.getContent().isEmpty()){
            throw new DataNotFoundException(Status.FAIL_USER_NOT_FOUND.label());
        }

        metaData.setOffSet(pageNo);
        metaData.setTotalElements(accounts.getTotalElements());
        metaData.setLimit(pageSize);

        Map<String, Object> res = new HashMap<>();
        res.put("content", accounts.getContent().stream().map(accountMapper::toSearchAccountResponse));
        res.put("metaData", metaData);
        return CommonResponse.success(res);

    }
}
