package com.lg.fresher.lgcommerce.annotation.auth;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Interface Name : ValidUsername
 * @ Description : lg_ecommerce_be ValidUsername
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/14/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/14/2024       63200502      first creation */
@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Target({ElementType.METHOD,
        ElementType.FIELD,
        ElementType.ANNOTATION_TYPE,
        ElementType.CONSTRUCTOR,
        ElementType.PARAMETER,
        ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUsername {
    String message() default "Tên người dùng chỉ có thể chứa các ký tự chữ và số và dấu gạch dưới (_) và " +
            "ký tự đầu tiên của tên người dùng phải là ký tự chữ cái và";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min() default 6;
}
