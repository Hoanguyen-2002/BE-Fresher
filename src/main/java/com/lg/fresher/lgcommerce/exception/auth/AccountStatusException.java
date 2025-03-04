package com.lg.fresher.lgcommerce.exception.auth;

import com.lg.fresher.lgcommerce.constant.Status;
import lombok.Getter;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : AccountStatusException
 * @ Description : lg_ecommerce_be AccountStatusException
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/18/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/18/2024       63200502      first creation */
public class AccountStatusException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 6942898826898130771L;

    private Integer code;


    /**
     * -- GETTER --
     *  @ Description : lg_ecommerce_be AccountStatusException Member Field getStatus
     * <pre>
     *  Date of Revision Modifier Revision
     *  ---------------  ---------   -----------------------------------------------
     *  11/18/2024           63200502    first creation
     * <pre>
     *
     * @return  Status
     */
    @Getter
    private final Status status;

    /**
     *
     * @ Description : lg_ecommerce_be AccountStatusException constructor
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/18/2024         63200502    first creation
     *<pre>

     field:

     * @ Description : lg_ecommerce_be AccountStatusException Member Field
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/18/2024          63200502    first creation
     *<pre>
     */
    public AccountStatusException(Status status) {
        super(status.label());
        this.status = status;
    }

    /**
     *
     * @ Description : lg_ecommerce_be AccountStatusException constructor
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/18/2024         63200502    first creation
     *<pre>

     field:

     * @ Description : lg_ecommerce_be AccountStatusException Member Field
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/18/2024          63200502    first creation
     *<pre>
     */
    public AccountStatusException(Status status, Throwable ex) {
        super(status.label(), ex);
        this.status = status;
    }

    /**
     *
     * @ Description : lg_ecommerce_be AccountStatusException Member Field toMap
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/18/2024           63200502    first creation
     *<pre>
     * @return  Map<String, Object>
     */
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>(8);
        map.put("code", getCode());
        map.put("msg", getMessage());
        return map;
    }

    /**
     *
     * @ Description : lg_ecommerce_be AccountStatusException Member Field getCode
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/18/2024           63200502    first creation
     *<pre>
     * @return  Integer
     */
    private Integer getCode() {
        if (this.code == null && this.status != null) {
            this.code = this.status.code();
        }
        return this.code;
    }
}
