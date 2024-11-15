package com.lg.fresher.lgcommerce.service.admin.account;

import com.lg.fresher.lgcommerce.entity.account.Account;
import com.lg.fresher.lgcommerce.entity.account.Profile;
import com.lg.fresher.lgcommerce.exception.InvalidRequestException;
import com.lg.fresher.lgcommerce.mapping.account.AccountMapper;
import com.lg.fresher.lgcommerce.model.request.account.SearchAccountRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.admin.SearchAccountResponse;
import com.lg.fresher.lgcommerce.model.response.common.MetaData;
import com.lg.fresher.lgcommerce.repository.account.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mapping.PropertyReferenceException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AdminAccountServiceImplTest {
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountMapper accountMapper;
    @InjectMocks
    private AdminAccountServiceImpl adminService;
    private List<Account> accountList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        accountList = new ArrayList<>();
    }

    @Test
    public void testSearchAccount() {
        String keyword = "Nguyen Van";
        String sortRequest = "+fullname";
        String filterRequest = null;
        int pageNo = 0;
        int pageSize = 10;

        SearchAccountRequest searchAccountRequest = new SearchAccountRequest();
        searchAccountRequest.setKeyword(keyword);
        searchAccountRequest.setSortRequest(sortRequest);
        searchAccountRequest.setFilterRequest(filterRequest);
        searchAccountRequest.setPageNo(pageNo);
        searchAccountRequest.setPageSize(pageSize);

        SearchAccountResponse searchAccountResponse = new SearchAccountResponse();
        searchAccountResponse.setFullname("Nguyen Van A");

        Account account = new Account();
        Profile profile = new Profile();
        profile.setFullname("Nguyen Van A");
        account.setProfile(profile);

        accountList.add(account);

        Page<Account> accountPage = new PageImpl<>(accountList);

        when(accountRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(accountPage);
        when(accountMapper.toSearchAccountResponse(any())).thenReturn(searchAccountResponse);

        CommonResponse<Map<String, Object>> response = adminService.searchAccount(searchAccountRequest);

        Map<String, Object> content = response.getData();
        assertNotNull(content);
        assertTrue(content.containsKey("content"));
        assertTrue(content.containsKey("metaData"));

        MetaData metaData = (MetaData) content.get("metaData");
        assertEquals(pageNo, metaData.getOffSet());
        assertEquals(accountPage.getTotalPages(), metaData.getTotalElements());
        assertEquals(pageSize, metaData.getLimit());
    }

    @Test
    public void testSearchAccountThrowsInvalidRequestException() {
        String keyword = "Nguyen Van";
        String sortRequest = "+auduasudasd";
        String filterRequest = null;
        int pageNo = 0;
        int pageSize = 10;

        SearchAccountRequest searchAccountRequest = new SearchAccountRequest();
        searchAccountRequest.setKeyword(keyword);
        searchAccountRequest.setSortRequest(sortRequest);
        searchAccountRequest.setFilterRequest(filterRequest);
        searchAccountRequest.setPageNo(pageNo);
        searchAccountRequest.setPageSize(pageSize);

        when(accountRepository.findAll(any(Specification.class), any(Pageable.class))).thenThrow(PropertyReferenceException.class);

        assertThrows(InvalidRequestException.class, () -> {
            adminService.searchAccount(searchAccountRequest);
        });
    }
}
