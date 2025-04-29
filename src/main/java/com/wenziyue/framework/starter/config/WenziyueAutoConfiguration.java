package com.wenziyue.framework.starter.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.wenziyue.framework.starter.advice.ResponseResultAdvice;
import com.wenziyue.framework.starter.exception.GlobalExceptionHandler;
import com.wenziyue.framework.starter.json.CommonEnumValueFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;

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

    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty
        );
        config.setDateFormat("yyyy-MM-dd HH:mm:ss");
        config.setCharset(StandardCharsets.UTF_8);

        // 加上枚举序列化处理
        config.setSerializeFilters(new CommonEnumValueFilter());
        converter.setFastJsonConfig(config);

        return new HttpMessageConverters(converter);
    }

}
