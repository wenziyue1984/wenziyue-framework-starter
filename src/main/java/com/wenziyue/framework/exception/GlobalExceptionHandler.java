package com.wenziyue.framework.exception;

import com.wenziyue.framework.common.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author wenziyue
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ApiResult<String> handleException(Exception e) {
        log.error("系统异常：{}", e.getMessage(), e);
        return ApiResult.error("Server error: " + e.getMessage());
    }

    // 处理自定义的业务异常
    @ExceptionHandler(ApiException.class)
    public ApiResult<String> handleBusinessException(ApiException e) {
        log.warn("业务异常：{} - {}", e.getCode(), e.getMessage(), e);
        return ApiResult.error(e.getCode(), e.getMessage());
    }
}
