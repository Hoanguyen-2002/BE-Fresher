package com.lg.fresher.lgcommerce.repository.account;

import com.lg.fresher.lgcommerce.entity.account.Account;
import com.lg.fresher.lgcommerce.repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends BaseRepository<Account, String>, JpaSpecificationExecutor<Account> {
    Optional<Account> findUserByAccountId(String userId);
    Optional<Account>  findAccountByUsername(String userName);
    Optional<Account> findAccountByEmail(String email);
    Boolean existsByUsername(String userName);
    Boolean existsByEmail(String userEmail);
}
