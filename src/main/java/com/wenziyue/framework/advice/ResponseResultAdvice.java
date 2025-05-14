package com.wenziyue.framework.advice;

import java.lang.reflect.Method;

import com.wenziyue.framework.annotation.ResponseResult;
import com.wenziyue.framework.common.ApiResult;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * 统一封装返回值，只对标注 @ResponseResult 的 Controller 生效
 * @author wenziyue
 */
@RestControllerAdvice(annotations = {ResponseResult.class})
@Slf4j
public class ResponseResultAdvice implements ResponseBodyAdvice<Object> {

    /**
     * 判断是否需要对当前 Controller 方法的返回值进行处理。
     *
     * @param returnType 方法返回值类型的信息
     * @param converterType 用于将返回值转换为 HTTP 响应体的转换器（比如 JSON 转换器）
     * @return true表示需要对返回值进行处理，false表示不需要进行处理
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 类或者方法上有@ResponseResult注解，并且返回值不是ApiResult类型才进行包装
        return (returnType.getContainingClass().isAnnotationPresent(ResponseResult.class) ||
                Objects.requireNonNull(returnType.getMethod()).isAnnotationPresent(ResponseResult.class))
                        && !returnType.getParameterType().equals(ApiResult.class);

    }

    /**
     * 在返回值写入 HTTP 响应体之前，对返回值进行处理，包装为 ApiResult 格式
     *
     * @param body Controller 方法的原始返回值（比如 String、Boolean、对象）
     * @param returnType 返回值类型信息
     * @param selectedContentType 响应的媒体类型（比如 application/json）
     * @param selectedConverterType HTTP 消息转换器
     * @param serverHttpRequest HTTP请求对象
     * @param serverHttpResponse HTTP响应对象
     * @return 处理后的返回值，通常为 ApiResult 对象
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (serverHttpRequest.getHeaders().containsKey("Feign")) {
            return body;
        }
        Object obj;
        if (body instanceof ApiResult) {
            obj = body;
        } else if (body instanceof Exception) {
            obj = ApiResult.error(((Exception) body).getMessage());
        } else if (body instanceof Boolean) {
            obj = ApiResult.success(((Boolean)body).toString());
        } else if (body instanceof String) {
            obj = JSON.toJSONString(ApiResult.success(body));
        } else {
            obj = ApiResult.success(body);
        }

        if (log.isDebugEnabled()) {
            String methodName = ((Method)Objects.requireNonNull(returnType.getMethod())).getName();
            log.debug("{} return = {}", methodName, obj instanceof String ? obj : JSON.toJSONString(obj));
        }

        return obj;
    }
}
