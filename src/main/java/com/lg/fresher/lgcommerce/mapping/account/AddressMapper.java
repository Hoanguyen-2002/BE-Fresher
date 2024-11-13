package com.lg.fresher.lgcommerce.mapping.account;

import com.lg.fresher.lgcommerce.entity.account.Address;
import com.lg.fresher.lgcommerce.model.response.address.AddressResponse;
import org.mapstruct.Mapper;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Interface Name : AddressMapper
 * @ Description : lg_ecommerce_be AddressMapper
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/8/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/8/2024       63200502      first creation */
@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressResponse toAddressResponse(Address address);
}
