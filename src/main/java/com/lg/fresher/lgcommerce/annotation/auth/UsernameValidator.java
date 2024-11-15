package com.lg.fresher.lgcommerce.annotation.auth;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : UsernameValidator
 * @ Description : lg_ecommerce_be UsernameValidator
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/14/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/14/2024       63200502      first creation */
public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {
    private String message;
    private int min;
    private static final String USERNAME_PATTERN = "^[A-Za-z]\\w{5,19}$";

    /**
     *
     * @param constraintAnnotation
     */
    @Override
    public void initialize(ValidUsername constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.message = constraintAnnotation.message();
    }

    /**
     *
     * @param username
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (username == null || username.length() < min) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    "Tên tài khoản phải từ " + min + " ký tự"
            ).addConstraintViolation();
            return false;
        }

        boolean matches = username.matches(USERNAME_PATTERN);
        if (!matches) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();

        }
        return matches;
    }
}
