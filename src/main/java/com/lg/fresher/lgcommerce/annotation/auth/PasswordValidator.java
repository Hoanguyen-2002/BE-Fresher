package com.lg.fresher.lgcommerce.annotation.auth;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
    private int min;
    private String message;
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&]).*$";

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.message = constraintAnnotation.message();
    }

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
