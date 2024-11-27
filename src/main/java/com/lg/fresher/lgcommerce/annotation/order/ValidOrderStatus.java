package com.lg.fresher.lgcommerce.annotation.order;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * -------------------------------------------------------------------------
 * LG CNS Ecommerce
 * ------------------------------------------------------------------------
 *
 * @ Interface Name : ValidOrderStatus
 * @ Description : lg_ecommerce_be ValidOrderStatus
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/25/2024
 * ------------------------------------------------------------------------
 * Modification Information
 * ------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/25/2024       63200502      first creation
 */
@Documented
@Constraint(validatedBy = OrderStatusValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidOrderStatus {
    String message() default "Trạng thái gửi lên không hợp lệ, vui lòng kiểm tra lại";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
