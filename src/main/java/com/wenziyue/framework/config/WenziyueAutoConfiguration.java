package com.wenziyue.framework.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wenziyue.framework.advice.ResponseResultAdvice;
import com.wenziyue.framework.exception.GlobalExceptionHandler;
import com.wenziyue.framework.json.CommonEnumModule;
import com.wenziyue.framework.trace.TraceIdFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;

import java.nio.charset.StandardCharsets;

/**
 * @author wenziyue
 */
@Configuration
@ConditionalOnWebApplication // 仅在 Web 环境下生效
@Import(WebEncodingAutoConfiguration.class)
public class WenziyueAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(ResponseResultAdvice.class)
    public ResponseResultAdvice responseResultAdvice(ObjectMapper objectMapper) {
        return new ResponseResultAdvice(objectMapper);
    }


    @Bean
    @ConditionalOnMissingBean(GlobalExceptionHandler.class)
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    /**
     * jackson 枚举序列化，方式一：直接提供 Module
     * Spring Boot 会自动把所有 Module 注入到 ObjectMapper
     */
    @Bean
    @ConditionalOnMissingBean(name = "commonEnumModule")
    public Module commonEnumModule() {
        return new CommonEnumModule();
    }

    /**
     * Jackson的dateFormat 设置，也可在这里做jackson 枚举序列化，效果等价于上面的commonEnumModule()方法
     */
    @Bean
    @ConditionalOnMissingBean(name = "wenziyueJacksonCustomizer")
    public Jackson2ObjectMapperBuilderCustomizer wenziyueJacksonCustomizer() {
        return builder -> {
            builder.simpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 如果你更偏好在这里绑定序列化器，也可以这样做：
            // builder.serializerByType(ICommonEnum.class, new CommonEnumJsonSerializer());
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public FilterRegistrationBean<TraceIdFilter> traceIdFilter() {
        FilterRegistrationBean<TraceIdFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TraceIdFilter());
        registrationBean.setOrder(Integer.MIN_VALUE); // 越小越靠前
        return registrationBean;
    }

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        return new StringHttpMessageConverter(StandardCharsets.UTF_8);
    }

}
