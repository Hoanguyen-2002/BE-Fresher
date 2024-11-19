package com.lg.fresher.lgcommerce.exception.auth;

import com.lg.fresher.lgcommerce.constant.Status;

import java.util.HashMap;
import java.util.Map;

/**
 * -------------------------------------------------------------------------
 * LG CNS Ecommerce
 * ------------------------------------------------------------------------
 *
 * @ Class Name : RefreshTokenException
 * @ Description : lg_ecommerce_be RefreshTokenException
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/18/2024
 * ------------------------------------------------------------------------
 * Modification Information
 * ------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/18/2024       63200502      first creation
 */
public class RefreshTokenException extends RuntimeException {
    private static final long serialVersionUID = 6947898826898130771L;

    private Integer code;


    private Status status;

    /**
     * @ Description : lg_ecommerce_be RefreshTokenException constructor
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/18/2024         63200502    first creation
     * <pre>
     *
     * field:
     *
     * @ Description : lg_ecommerce_be RefreshTokenException Member Field
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/18/2024          63200502    first creation
     * <pre>
     */
    public RefreshTokenException() {
        super(Status.FAIL_REFRESH_TOKEN_INVALID.label());
        this.status = Status.FAIL_REFRESH_TOKEN_INVALID;
    }

    /**
     * @ Description : lg_ecommerce_be RefreshTokenException constructor
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/18/2024         63200502    first creation
     * <pre>
     *
     * field:
     *
     * @ Description : lg_ecommerce_be RefreshTokenException Member Field
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/18/2024          63200502    first creation
     * <pre>
     */
    public RefreshTokenException(Status status) {
        super(status.label());
        this.status = status;
    }

    /**
     * @ Description : lg_ecommerce_be RefreshTokenException constructor
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/18/2024         63200502    first creation
     * <pre>
     *
     * field:
     *
     * @ Description : lg_ecommerce_be RefreshTokenException Member Field
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/18/2024          63200502    first creation
     * <pre>
     */
    public RefreshTokenException(String msg) {
        super(msg);
        this.status = Status.FAIL_REFRESH_TOKEN_INVALID;
    }

    /**
     * @ Description : lg_ecommerce_be RefreshTokenException constructor
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/18/2024         63200502    first creation
     * <pre>
     *
     * field:
     *
     * @ Description : lg_ecommerce_be RefreshTokenException Member Field
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/18/2024          63200502    first creation
     * <pre>
     */
    public RefreshTokenException(Status status, Throwable ex) {
        super(status.label(), ex);
        this.status = status;
    }


    /**
     * @ Description : lg_ecommerce_be RefreshTokenException Member Field toMap
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/18/2024           63200502    first creation
     * <pre>
     * @return Map<String, Object>
     */
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>(8);
        map.put("code", getCode());
        map.put("msg", getMessage());
        return map;
    }

    /**
     * @ Description : lg_ecommerce_be RefreshTokenException Member Field getStatus
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/18/2024           63200502    first creation
     * <pre>
     * @return Status
     */
    public Status getStatus() {
        return this.status;
    }

    /**
     * @ Description : lg_ecommerce_be RefreshTokenException Member Field getCode
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/18/2024           63200502    first creation
     * <pre>
     * @return Integer
     */
    private Integer getCode() {
        if (this.code == null && this.status != null) {
            this.code = this.status.code();
        }
        return this.code;
    }
}
