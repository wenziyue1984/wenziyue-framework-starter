package com.wenziyue.framework.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wenziyue.framework.advice.ResponseResultAdvice;
import com.wenziyue.framework.exception.GlobalExceptionHandler;
import com.wenziyue.framework.json.CommonEnumModule;
import com.wenziyue.framework.trace.TraceIdFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

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
     * 方式一：直接提供 Module
     * Spring Boot 会自动把所有 Module 注入到 ObjectMapper
     */
    @Bean
    @ConditionalOnMissingBean(name = "commonEnumModule")
    public Module commonEnumModule() {
        return new CommonEnumModule();
    }

    /**
     * 方式二：用 BuilderCustomizer 做轻量配置
     * 等价替代 FastJsonConfig 的 dateFormat 等设置
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

//    @Bean
//    @ConditionalOnMissingBean
//    public HttpMessageConverters fastJsonHttpMessageConverters() {
//        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
//        FastJsonConfig config = new FastJsonConfig();
//        config.setSerializerFeatures(
//                SerializerFeature.WriteMapNullValue,
//                SerializerFeature.WriteNullStringAsEmpty
//        );
//        config.setDateFormat("yyyy-MM-dd HH:mm:ss");
//        config.setCharset(StandardCharsets.UTF_8);
//
//        // 加上枚举序列化处理
//        config.setSerializeFilters(new CommonEnumValueFilter());
//        // 显式设置支持的媒体类型
//        converter.setSupportedMediaTypes(Collections.singletonList(
//                new MediaType("application", "json", StandardCharsets.UTF_8)
//        ));
//        converter.setFastJsonConfig(config);
//
//        return new HttpMessageConverters(converter);
//    }

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
