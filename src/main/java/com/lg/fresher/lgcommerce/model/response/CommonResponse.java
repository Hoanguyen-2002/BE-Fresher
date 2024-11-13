package com.lg.fresher.lgcommerce.model.response;

import com.lg.fresher.lgcommerce.constant.Status;
import lombok.Data;

import java.io.Serializable;


@Data
public class CommonResponse<T> extends ResponseData implements Serializable {

    private int code;
    private String msg;
    private T data;


    public CommonResponse(T data) {
        this.code = Status.OK.code();
        this.msg = Status.OK.label();
        this.data = data;
    }
    public CommonResponse(String  message) {
        this.code = Status.OK.code();
        this.msg = message;
        this.data = null;
    }


    public CommonResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<>(data);
    }
    public static <T> CommonResponse<T> success(String msg) {
        return new CommonResponse<>(msg);
    }


    public static <T> CommonResponse<T> fail(String msg, int code, T data) {
        return new CommonResponse<>(code, msg, data);
    }


}
