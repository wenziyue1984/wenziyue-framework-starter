package com.wenziyue.framework.exception;

import com.wenziyue.framework.common.ApiResult;
import com.wenziyue.framework.common.FeignConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wenziyue
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e, HttpServletRequest request) {
        log.error("系统异常：{}", e.getMessage(), e);
        if (isFeignCall(request)) {
            return ResponseEntity.status(500).body("Internal server error");
        }
        return ApiResult.error("Server error: " + e.getMessage());
    }

    // 处理自定义的业务异常
    @ExceptionHandler(ApiException.class)
    public Object handleBusinessException(ApiException e, HttpServletRequest request) {
        log.warn("业务异常：{} - {}", e.getCode(), e.getMessage(), e);
        if (isFeignCall(request)) {
            int status = toHttpStatus(e.getCode());
            return ResponseEntity.status(status).body(e.getMessage());
        }
        return ApiResult.error(e.getCode(), e.getMessage());
    }

    private boolean isFeignCall(HttpServletRequest request) {
        return request.getHeader(FeignConstants.FEIGN_HEADER) != null;
    }

    private int toHttpStatus(String code) {
        try {
            return Integer.parseInt(code);
        } catch (Exception ignore) {
            return 500;
        }
    }
}
