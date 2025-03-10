package com.lg.fresher.lgcommerce.annotation.auth;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : PasswordValidator
 * @ Description : lg_ecommerce_be PasswordValidator
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/14/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/14/2024       63200502      first creation */
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
    private int min;
    private String message;
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&]).*$";

    /**
     *
     * @param constraintAnnotation
     */
    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.message = constraintAnnotation.message();
    }

    /**
     *
     * @param password
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if (password == null || password.length() < min) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    "Mật khẩu phải từ " + min + " ký tự và phải có ít nhất 1 chữ số và ký tự đặc biệt"
            ).addConstraintViolation();
            return false;
        }

        boolean matches = password.matches(PASSWORD_PATTERN);
        if (!matches) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();

        }
        return matches;
    }
}
