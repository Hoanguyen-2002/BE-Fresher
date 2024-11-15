package com.lg.fresher.lgcommerce.service.account;

import com.lg.fresher.lgcommerce.config.security.UserDetailsImpl;
import com.lg.fresher.lgcommerce.constant.AccountStatus;
import com.lg.fresher.lgcommerce.constant.Status;
import com.lg.fresher.lgcommerce.entity.account.Account;
import com.lg.fresher.lgcommerce.entity.account.Address;
import com.lg.fresher.lgcommerce.entity.account.Profile;
import com.lg.fresher.lgcommerce.exception.InvalidRequestException;
import com.lg.fresher.lgcommerce.mapping.account.AccountMapper;
import com.lg.fresher.lgcommerce.model.request.account.UpdateAccountRequest;
import com.lg.fresher.lgcommerce.model.request.address.UpdateAddressRequest;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import com.lg.fresher.lgcommerce.model.response.StringResponse;
import com.lg.fresher.lgcommerce.model.response.account.AccountInfoResponse;
import com.lg.fresher.lgcommerce.repository.account.AccountRepository;
import com.lg.fresher.lgcommerce.utils.UUIDUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
 * @ Class Name : AccountServiceImpl
 * @ Description : lg_ecommerce_be AccountServiceImpl
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/6/2024
 * ------------------------------------------------------------------------
 * Modification Information
 * ------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/6/2024       63200502      first creation
 * 11/8/2024       63200502      add update account profile
 * 11/12/2024      63200502      add method to get my info
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private static final int MAX_ADDRESS = 5;
    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public CommonResponse<Map<String, Object>> getAccountInfo(String id) {
        Account account = accountRepository.findById(id).orElseThrow(() ->
                new InvalidRequestException(Status.FAIL_USER_NOT_FOUND));
        AccountInfoResponse accountInfoResponse = accountMapper.toAccountInfoResponse(account);
        Map<String, Object> res = new HashMap<>();
        res.put("content", accountInfoResponse);
        return CommonResponse.success(res);
    }

    @Override
    @Transactional
    public CommonResponse<StringResponse> updateAccountInfo(UpdateAccountRequest updateAccountRequest) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountRepository.findById(userDetails.getUserId()).orElseThrow(
                () -> new InvalidRequestException(Status.FAIL_USER_NOT_FOUND)
        );

        updateUserProfile(account.getProfile(), updateAccountRequest);
        updateUserAddress(account, updateAccountRequest);

        return CommonResponse.success(Status.UPDATE_ACCOUNT_SUCCESS.label());
    }

    @Override
    public CommonResponse<Map<String, Object>> getMyInfo() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountRepository.findById(userDetails.getUserId()).orElseThrow(
                () -> new InvalidRequestException(Status.FAIL_USER_NOT_FOUND)
        );
        if(account.getStatus() == AccountStatus.BANNED){
            throw  new InvalidRequestException(Status.FAIL_USER_IS_BANNED);
        }
        AccountInfoResponse accountInfoResponse = accountMapper.toAccountInfoResponse(account);
        Map<String, Object> res = new HashMap<>();
        res.put("content", accountInfoResponse);
        return CommonResponse.success(res);
    }

    //TODO: find the smarter way
    private void updateUserAddress(Account account, UpdateAccountRequest request) {
        if (request.getListAddress() != null) {
            List<Address> existingAddresses = account.getAddress();
            for (UpdateAddressRequest updateRequest : request.getListAddress()) {
                if (updateRequest.getAddresId() != null) {
                    Address existingAddress = existingAddresses.stream()
                            .filter(address -> address.getAddressId().equals(updateRequest.getAddresId()))
                            .findFirst()
                            .orElseThrow(() -> new InvalidRequestException(Status.UPDATE_ACCOUNT_FAIL_ADDRESS_NOT_FOUND));
                    existingAddress.setCity(updateRequest.getCity());
                    existingAddress.setDistrict(updateRequest.getDistrict());
                    existingAddress.setProvince(updateRequest.getProvince());
                    existingAddress.setDetailAddress(updateRequest.getDetailAddress());
                } else if (existingAddresses.size() < MAX_ADDRESS) {
                    Address newAddress = new Address();
                    newAddress.setAddressId(UUIDUtil.generateId());
                    newAddress.setCity(updateRequest.getCity());
                    newAddress.setDistrict(updateRequest.getDistrict());
                    newAddress.setProvince(updateRequest.getProvince());
                    newAddress.setDetailAddress(updateRequest.getDetailAddress());
                    newAddress.setAccount(account);
                    existingAddresses.add(newAddress);
                }
            }
        }
    }

    private void updateUserProfile(Profile profile, UpdateAccountRequest updateAccountRequest) {
        if (updateAccountRequest.getAvatar() != null) {
            profile.setAvatar(updateAccountRequest.getAvatar());
        }
        if (updateAccountRequest.getFullname() != null) {
            profile.setFullname(updateAccountRequest.getFullname());
        }
        if (updateAccountRequest.getPhone() != null) {
            profile.setPhone(updateAccountRequest.getPhone());
        }
    }
}
