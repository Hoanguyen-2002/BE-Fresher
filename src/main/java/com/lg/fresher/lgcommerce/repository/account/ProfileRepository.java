package com.lg.fresher.lgcommerce.repository.account;

import com.lg.fresher.lgcommerce.entity.account.Profile;
import com.lg.fresher.lgcommerce.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Interface Name : ProfileRepository
 * @ Description : lg_ecommerce_be ProfileRepository
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/6/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/6/2024       63200502      first creation */
@Repository
public interface ProfileRepository extends BaseRepository<Profile, String> {
}
