package com.lg.fresher.lgcommerce.entity.order;

import com.lg.fresher.lgcommerce.entity.core.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

/**
 * -------------------------------------------------------------------------
 * LG CNS Ecommerce
 * ------------------------------------------------------------------------
 *
 * @ Class Name : ShippingMethod
 * @ Description : lg_ecommerce_be ShippingMethod
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/18/2024
 * ------------------------------------------------------------------------
 * Modification Information
 * ------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/18/2024       63200502      first creation
 */
@Getter
@Setter
@Entity
@Table(name = "shipping_method", schema = "lgecommerce")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShippingMethod extends BaseEntity {
    @Id
    @Column(name = "shipping_method_id", nullable = false, length = 50)
    private String shippingMethodId;

    @Column(name = "shipping_name")
    private String shippingName;

    @Column(name = "shipping_fee")
    private Double shippingFee;

}