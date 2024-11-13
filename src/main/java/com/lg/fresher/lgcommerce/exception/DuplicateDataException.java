package com.lg.fresher.lgcommerce.exception;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : DuplicateDataException
 * @ Description : lg_ecommerce_be DuplicateDataException
 * @ author lg_ecommerce_be Dev Team 63200485
 * @ since 11/6/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/6/2024       63200485      first creation */
public class DuplicateDataException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = -1203618387183809985L;

    public DuplicateDataException(String message) {
        super(message);
    }

    public DuplicateDataException(Throwable ex, String msg) {
        super((msg), ex);
    }


    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>(8);
        map.put("code", getCode());
        map.put("msg", getMessage());
        return map;
    }

    private int getCode() {
        return 400;
    }
}
