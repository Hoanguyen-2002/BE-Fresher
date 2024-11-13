package com.lg.fresher.lgcommerce.config.security;

import com.lg.fresher.lgcommerce.constant.AccountStatus;
import com.lg.fresher.lgcommerce.constant.Status;
import com.lg.fresher.lgcommerce.entity.account.Account;
import com.lg.fresher.lgcommerce.exception.InvalidRequestException;
import com.lg.fresher.lgcommerce.repository.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : UserDetailsServiceImpl
 * @ Description : lg_ecommerce_be UserDetailsServiceImpl
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/5/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/5/2024       63200502      first creation */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AccountRepository accountRepository;

    /**
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (username == null) {
            throw new UsernameNotFoundException("username must be provided");
        }
        Account account = accountRepository.findAccountByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User Not Found with username: " + username));
        if(account.getStatus() == AccountStatus.BANNED){
            throw new InvalidRequestException(Status.FAIL_USER_IS_BANNED);
        }else if(account.getStatus() == AccountStatus.PENDING){
            throw new InvalidRequestException(Status.FAIL_USER_IS_NOT_ACTIVE);
        }
        return UserDetailsImpl.build(account);
    }

}
