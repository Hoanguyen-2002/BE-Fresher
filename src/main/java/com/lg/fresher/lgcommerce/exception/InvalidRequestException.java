package com.lg.fresher.lgcommerce.exception;

import com.lg.fresher.lgcommerce.constant.Status;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : InvalidRequestException
 * @ Description : lg_ecommerce_be InvalidRequestException
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/6/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/6/2024       63200502      first creation */

public class InvalidRequestException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -1203618387183809985L;
    private Integer code;


    private Status status;

    /**
     *
     * @ Description : lg_ecommerce_be InvalidRequestException constructor
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/6/2024         63200502    first creation
     *<pre>

     field:

     * @ Description : lg_ecommerce_be InvalidRequestException Member Field
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/6/2024          63200502    first creation
     *<pre>
     */
    public InvalidRequestException() {
        super(Status.FAIL_OPERATION.label());
        this.status = Status.FAIL_OPERATION;
    }

    /**
     *
     * @ Description : lg_ecommerce_be InvalidRequestException constructor
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/6/2024         63200502    first creation
     *<pre>

     field:

     * @ Description : lg_ecommerce_be InvalidRequestException Member Field
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/6/2024          63200502    first creation
     *<pre>
     */
    public InvalidRequestException(String msg) {
        super(msg);
        this.status = Status.FAIL_OPERATION;
    }

    /**
     *
     * @ Description : lg_ecommerce_be InvalidRequestException constructor
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/6/2024         63200502    first creation
     *<pre>

     field:

     * @ Description : lg_ecommerce_be InvalidRequestException Member Field
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/6/2024          63200502    first creation
     *<pre>
     */
    public InvalidRequestException(Status status) {
        super(status.label());
        this.status = status;
    }

    /**
     *
     * @ Description : lg_ecommerce_be InvalidRequestException constructor
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/6/2024         63200502    first creation
     *<pre>

     field:

     * @ Description : lg_ecommerce_be InvalidRequestException Member Field
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/6/2024          63200502    first creation
     *<pre>
     */
    public InvalidRequestException(Throwable ex, Status status) {
        super((status.label()), ex);
        this.status = status;
    }

    /**
     *
     * @ Description : lg_ecommerce_be InvalidRequestException Member Field toMap
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/6/2024           63200502    first creation
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
     * @ Description : lg_ecommerce_be InvalidRequestException Member Field getStatus
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/6/2024           63200502    first creation
     *<pre>
     * @return  Status
     */
    public Status getStatus() {
        return this.status;
    }

    /**
     *
     * @ Description : lg_ecommerce_be InvalidRequestException Member Field getCode
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/6/2024           63200502    first creation
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
