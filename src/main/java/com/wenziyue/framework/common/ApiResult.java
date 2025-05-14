package com.wenziyue.framework.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wenziyue
 */
@Data
public class ApiResult<T> implements Serializable {

    private static final long serialVersionUID = -3317982555318084588L;

    private String code;    // 状态码（200 成功，500 失败等）
    private String msg;  // 消息
    private T data;      // 数据

    public ApiResult(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>("200", "Success", data);
    }

    public static <T> ApiResult<T> error(String msg) {
        return new ApiResult<>("500", msg, null);
    }

    public static <T> ApiResult<T> error(String code, String msg) {
        return new ApiResult<>(code, msg, null);
    }


}
