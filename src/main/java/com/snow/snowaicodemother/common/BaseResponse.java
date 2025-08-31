package com.snow.snowaicodemother.common;

import com.snow.snowaicodemother.exception.ErrorCode;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xueruohang
 * @date 2025/8/31 19:13
 */
@Data
public class BaseResponse<T> implements Serializable {

    private int code;

    private T data;

    private String message;

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BaseResponse(int code, T data) {
        this(code, data, "");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());
    }
}
