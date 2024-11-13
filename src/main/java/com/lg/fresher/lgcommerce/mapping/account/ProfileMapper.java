package com.lg.fresher.lgcommerce.mapping.account;

import com.lg.fresher.lgcommerce.entity.account.Profile;
import com.lg.fresher.lgcommerce.model.request.auth.SignupRequest;
import com.lg.fresher.lgcommerce.utils.UUIDUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Interface Name : ProfileMapper
 * @ Description : lg_ecommerce_be ProfileMapper
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/6/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/6/2024       63200502      first creation */
@Mapper(componentModel = "spring")
public interface ProfileMapper {
    /**
     *
     * @ Description : lg_ecommerce_be ProfileMapper Member Field toEntity
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/6/2024           63200502    first creation
     *<pre>
     * @param signupRequest
     * @return  Profile
     */
    @Mapping(target = "profileId", expression = "java(generateId())")
    @Mapping(target = "avatar", constant = "url_placeholder")
    Profile toEntity(SignupRequest signupRequest);

    /**
     *
     * @ Description : lg_ecommerce_be ProfileMapper Member Field generateId
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
