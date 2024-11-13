package com.lg.fresher.lgcommerce.service.admin;

import com.lg.fresher.lgcommerce.entity.account.Account;
import com.lg.fresher.lgcommerce.exception.InvalidRequestException;
import com.lg.fresher.lgcommerce.mapping.account.AccountMapper;
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

        Page<Account> accountPage = new PageImpl<>(accountList);

        when(accountRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(accountPage);
        when(accountMapper.toSearchAccountResponse(any())).thenReturn(new SearchAccountResponse());

        CommonResponse<Map<String, Object>> response = adminService.searchAccount(keyword,
                sortRequest,
                filterRequest,
                pageNo,
                pageSize);

        Map<String, Object> content = response.getData();
        assertNotNull(content);
        assertTrue(content.containsKey("content"));
        assertTrue(content.containsKey("metaData"));

        MetaData metaData = (MetaData) content.get("metaData");
        assertEquals(pageNo, metaData.getOffSet());
        assertEquals(accountPage.getTotalPages(), metaData.getTotalElement());
        assertEquals(pageSize, metaData.getLimit());
    }

    @Test
    public void testSearchAccountThrowsInvalidRequestException() {
        String keyword = "Nguyen Van";
        String sortRequest = "+auduasudasd";
        String filterRequest = null;
        int pageNo = 0;
        int pageSize = 10;

        when(accountRepository.findAll(any(Specification.class), any(Pageable.class))).thenThrow(new RuntimeException());

        assertThrows(InvalidRequestException.class, () -> {
            adminService.searchAccount(keyword, sortRequest, filterRequest, pageNo, pageSize);
        });
    }
}
