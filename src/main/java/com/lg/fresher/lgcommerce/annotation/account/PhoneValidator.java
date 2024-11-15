package com.lg.fresher.lgcommerce.annotation.account;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : PhoneValidator
 * @ Description : lg_ecommerce_be PhoneValidator
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/14/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/14/2024       63200502      first creation */
public class PhoneValidator implements ConstraintValidator<ValidPhone, String> {
    private static final String PHONE_PATTERN = "^\\d{10}$";

    /**
     *
     * @param phoneNumber
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        if (phoneNumber == null || !phoneNumber.matches(PHONE_PATTERN)) {
            return false;
        }
        return true;
    }
}
