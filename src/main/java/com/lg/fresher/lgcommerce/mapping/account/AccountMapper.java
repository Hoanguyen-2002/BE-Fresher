package com.lg.fresher.lgcommerce.mapping.account;


import com.lg.fresher.lgcommerce.entity.account.Account;
import com.lg.fresher.lgcommerce.model.request.auth.SignupRequest;
import com.lg.fresher.lgcommerce.model.response.account.AccountInfoResponse;
import com.lg.fresher.lgcommerce.model.response.admin.SearchAccountResponse;
import com.lg.fresher.lgcommerce.utils.UUIDUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Interface Name : AccountMapper
 * @ Description : lg_ecommerce_be AccountMapper
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/6/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/6/2024       63200502      first creation
 * 11/8/2024       63200502      add mapper for info response
 * */
@Mapper(componentModel = "spring", uses = {ProfileMapper.class, AddressMapper.class})
public interface AccountMapper {
    /**
     *
     * @ Description : lg_ecommerce_be AccountMapper Member Field toEntity
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/6/2024           63200502    first creation
     *<pre>
     * @param signupRequest
     * @return  Account
     */
    @Mapping(target = "accountId", expression = "java(generateId())")
    Account toEntity(SignupRequest signupRequest);

    /**
     *
     * @ Description : lg_ecommerce_be AccountMapper Member Field toAccountInfoResponse
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/8/2024           63200502    first creation
     * 11/11/2024          63200502    add mapper for search account response
     *<pre>
     * @param account
     * @return  AccountInfoResponse
     */
    @Mapping(target = "fullname", source = "profile.fullname")
    @Mapping(target = "phone", source = "profile.phone")
    @Mapping(target = "avatar", source = "profile.avatar")
    @Mapping(target = "listAddress", source = "address")
    AccountInfoResponse toAccountInfoResponse(Account account);
    @Mapping(target = "fullname", source = "profile.fullname")
    @Mapping(target = "avatar", source = "profile.avatar")
    @Mapping(target = "jointDate", source = "createdAt")
    SearchAccountResponse toSearchAccountResponse(Account account);

    /**
     *
     * @ Description : lg_ecommerce_be AccountMapper Member Field generateId
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/6/2024           63200502    first creation
     *<pre>
     * @return  String
     */
    default String generateId(){
        return UUIDUtil.generateId();
    }
}
