package com.lg.fresher.lgcommerce.exception.book;

import com.lg.fresher.lgcommerce.constant.Status;

import java.util.HashMap;
import java.util.Map;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : BookOutOfStockException
 * @ Description : lg_ecommerce_be BookOutOfStockException
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/25/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/25/2024       63200502      first creation */
public class BookOutOfStockException extends RuntimeException {
    private static final long serialVersionUID = 7047618826898130771L;

    private Integer code;


    private Status status;

    /**
     *
     * @ Description : lg_ecommerce_be BookOutOfStockException constructor
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/25/2024         63200502    first creation
     *<pre>

     field:

     * @ Description : lg_ecommerce_be BookOutOfStockException Member Field
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/25/2024          63200502    first creation
     *<pre>
     */
    public BookOutOfStockException() {
        super(Status.ACCEPT_ORDER_FAIL_DETAIL_ITEM_OUT_OF_STOCK.label());
        this.status = Status.ACCEPT_ORDER_FAIL_DETAIL_ITEM_OUT_OF_STOCK;
    }

    /**
     *
     * @ Description : lg_ecommerce_be BookOutOfStockException constructor
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/25/2024         63200502    first creation
     *<pre>

     field:

     * @ Description : lg_ecommerce_be BookOutOfStockException Member Field
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/25/2024          63200502    first creation
     *<pre>
     */
    public BookOutOfStockException(Status status) {
        super(status.label());
        this.status = status;
    }

    /**
     *
     * @ Description : lg_ecommerce_be BookOutOfStockException constructor
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/25/2024         63200502    first creation
     *<pre>

     field:

     * @ Description : lg_ecommerce_be BookOutOfStockException Member Field
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/25/2024          63200502    first creation
     *<pre>
     */
    public BookOutOfStockException(String msg) {
        super(msg);
        this.status = Status.ACCEPT_ORDER_FAIL_DETAIL_ITEM_OUT_OF_STOCK;
    }

    /**
     *
     * @ Description : lg_ecommerce_be BookOutOfStockException constructor
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/25/2024         63200502    first creation
     *<pre>

     field:

     * @ Description : lg_ecommerce_be BookOutOfStockException Member Field
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/25/2024          63200502    first creation
     *<pre>
     */
    public BookOutOfStockException(Status status, Throwable ex) {
        super(status.label(), ex);
        this.status = status;
    }

    /**
     *
     * @ Description : lg_ecommerce_be BookOutOfStockException Member Field toMap
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/25/2024           63200502    first creation
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
     * @ Description : lg_ecommerce_be BookOutOfStockException Member Field getStatus
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/25/2024           63200502    first creation
     *<pre>
     * @return  Status
     */
    public Status getStatus() {
        return this.status;
    }

    /**
     *
     * @ Description : lg_ecommerce_be BookOutOfStockException Member Field getCode
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/25/2024           63200502    first creation
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
