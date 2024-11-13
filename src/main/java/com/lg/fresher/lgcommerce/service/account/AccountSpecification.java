package com.lg.fresher.lgcommerce.service.account;

import com.lg.fresher.lgcommerce.entity.account.Account;
import org.springframework.data.jpa.domain.Specification;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : AccountSpecification
 * @ Description : lg_ecommerce_be AccountSpecification
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/11/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/11/2024       63200502      first creation */
public class AccountSpecification {
    public static Specification<Account> hasFullNameLike(String fullname){
        return ((root, query, criteriaBuilder) -> {
            if(fullname == null || fullname.isEmpty()){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("profile").get("fullname")), "%" + fullname.toLowerCase() + "%");
        });
    }
}
