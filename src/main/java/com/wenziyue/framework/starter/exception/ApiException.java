package com.wenziyue.framework.starter.exception;

import com.wenziyue.framework.starter.common.CommonCode;
import lombok.Getter;

/**
 * @author wenziyue
 */
@Getter
public class ApiException extends RuntimeException{

    private final String code;

    public ApiException(String code, String message) {
        super(message);
        this.code = code;
    }
    public ApiException(CommonCode code) {
        super(code.getMsg());
        this.code = code.getCode();
    }

    @Override
    public String toString() {
        return "ApiException{" +
                "code=" + code +
                ", message='" + getMessage() + '\'' +
                '}';
    }
}
