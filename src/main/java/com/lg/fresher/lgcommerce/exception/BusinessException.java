package com.lg.fresher.lgcommerce.exception;

import com.lg.fresher.lgcommerce.constant.Status;

import java.util.HashMap;
import java.util.Map;


public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 6947618826898130771L;

    private Integer code;


    private Status status;


    public BusinessException() {
        super(Status.FAIL_OPERATION.label());
        this.status = Status.FAIL_OPERATION;
    }


    public BusinessException(Status status) {
        super(status.label());
        this.status = status;
    }

    public BusinessException(String msg) {
        super(msg);
        this.status = Status.FAIL_OPERATION;
    }

    public BusinessException(Status status, Throwable ex) {
        super(status.label(), ex);
        this.status = status;
    }



    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>(8);
        map.put("code", getCode());
        map.put("msg", getMessage());
        return map;
    }


    public Status getStatus() {
        return this.status;
    }

    private Integer getCode() {
        if (this.code == null && this.status != null) {
            this.code = this.status.code();
        }
        return this.code;
    }

}
