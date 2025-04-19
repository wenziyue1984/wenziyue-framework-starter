//package com.wenziyue.framework.starter.aop;
//
//import com.wenziyue.framework.starter.common.ApiResult;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.springframework.stereotype.Component;
//import org.aspectj.lang.annotation.Aspect;
//
///**
// * 弃用，使用切面处理ResponseResult注解，统一封装返回值，但是现在使用ResponseResultAdvice处理了，所以这个类暂时不用了
// *
// * @author wenziyue
// */
//@Aspect
//@Component
//public class ResponseResultAspect {
//
//    /**
//     * 处理ResponseResult注解
//     * @param point 切点
//     * @return object
//     * @throws Throwable e
//     */
//    @Around("@within(com.wenziyue.framework.starter.annotation.ResponseResult) || @annotation(com.wenziyue.framework.starter.annotation.ResponseResult)")
//    public Object handleResponse(ProceedingJoinPoint point) throws Throwable {
//        Object result = point.proceed();
//        if (result == null) {
//            return ApiResult.success(null);
//        }
//        return ApiResult.success(result);
//    }
//}
