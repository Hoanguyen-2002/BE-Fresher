package com.lg.fresher.lgcommerce.exception.auth;

import com.lg.fresher.lgcommerce.constant.Status;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

public class RefreshTokenException extends RuntimeException{
    private static final long serialVersionUID = 6947898826898130771L;

    private Integer code;


    private Status status;


    public RefreshTokenException() {
        super(Status.FAIL_REFRESH_TOKEN_INVALID.label());
        this.status = Status.FAIL_REFRESH_TOKEN_INVALID;
    }


    public RefreshTokenException(Status status) {
        super(status.label());
        this.status = status;
    }

    public RefreshTokenException(String msg) {
        super(msg);
        this.status = Status.FAIL_REFRESH_TOKEN_INVALID;
    }

    public RefreshTokenException(Status status, Throwable ex) {
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
