package com.lg.fresher.lgcommerce.annotation.auth;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "Mật khẩu không hợp lệ";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int min() default 6;
}