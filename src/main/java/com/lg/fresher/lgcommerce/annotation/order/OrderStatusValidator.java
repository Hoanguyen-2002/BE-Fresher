package com.lg.fresher.lgcommerce.annotation.order;

import com.lg.fresher.lgcommerce.constant.OrderStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * -------------------------------------------------------------------------
 * LG CNS Ecommerce
 * ------------------------------------------------------------------------
 *
 * @ Class Name : OrderStatusValidator
 * @ Description : lg_ecommerce_be OrderStatusValidator
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/25/2024
 * ------------------------------------------------------------------------
 * Modification Information
 * ------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/25/2024       63200502      first creation
 */
public class OrderStatusValidator implements ConstraintValidator<ValidOrderStatus, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        try {
            OrderStatus.valueOf(value.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
