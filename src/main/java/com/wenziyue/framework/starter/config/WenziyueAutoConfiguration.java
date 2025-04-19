package com.wenziyue.framework.starter.config;

import com.wenziyue.framework.starter.advice.ResponseResultAdvice;
import com.wenziyue.framework.starter.exception.GlobalExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wenziyue
 */
@Configuration
@ConditionalOnWebApplication // 仅在 Web 环境下生效
public class WenziyueAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(ResponseResultAdvice.class)
    public ResponseResultAdvice responseResultAdvice() {
        return new ResponseResultAdvice();
    }

    @Bean
    @ConditionalOnMissingBean(GlobalExceptionHandler.class)
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

}
