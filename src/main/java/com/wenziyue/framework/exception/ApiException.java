package com.wenziyue.framework.exception;

import com.wenziyue.framework.common.CommonCode;
import com.wenziyue.framework.common.IResultCode;
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
    public ApiException(IResultCode code) {
        super(code.getMsg());
        this.code = code.getCode();
    }
    public ApiException(IResultCode code, String message) {
        super(message);
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
