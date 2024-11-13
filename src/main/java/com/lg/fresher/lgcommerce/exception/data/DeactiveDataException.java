package com.lg.fresher.lgcommerce.exception.data;

import com.lg.fresher.lgcommerce.constant.Status;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : DeactiveDataException
 * @ Description : lg_ecommerce_be DeactiveDataException
 * @ author lg_ecommerce_be Dev Team 63200504
 * @ since 11/6/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/6/2024       63200504      first creation */
public class DeactiveDataException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -1203618387183809985L;

    public DeactiveDataException(String msg) {
        super(msg);
    }


    public DeactiveDataException(Throwable ex, String msg) {
        super((msg), ex);
    }


    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>(8);
        map.put("code", getCode());
        map.put("msg", getMessage());
        return map;
    }

    private int getCode() {
        return Status.DATA_IS_DEACTIVE.code();
    }
}
