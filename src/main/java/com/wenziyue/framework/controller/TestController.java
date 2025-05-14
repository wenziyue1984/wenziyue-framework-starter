//package com.wenziyue.framework.starter.controller;
//
//import com.wenziyue.framework.starter.annotation.ResponseResult;
//import com.wenziyue.framework.starter.common.CommonCode;
//import com.wenziyue.framework.starter.exception.ApiException;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @author wenziyue
// */
//@ResponseResult
//@RestController
//@RequestMapping("/test")
//public class TestController {
//
//    @GetMapping("/success")
//    public String success() {
//        return "正常返回！";
//    }
//
//    @GetMapping("/error")
//    public String error() {
//        throw new ApiException(CommonCode.SERVER_INNER_ERROR);
//    }
//
//    @GetMapping("/business")
//    public String businessError() {
//        throw new ApiException("400", "业务逻辑异常！");
//    }
//}
