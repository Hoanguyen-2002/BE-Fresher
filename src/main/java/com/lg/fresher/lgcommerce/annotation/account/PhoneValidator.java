package com.lg.fresher.lgcommerce.annotation.account;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.apache.commons.lang3.concurrent.ConcurrentInitializer;

public class PhoneValidator implements ConstraintValidator<ValidPhone, String> {
    private static final String PHONE_PATTERN = "^\\d{10}$";

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.matches(PHONE_PATTERN);
    }
}
