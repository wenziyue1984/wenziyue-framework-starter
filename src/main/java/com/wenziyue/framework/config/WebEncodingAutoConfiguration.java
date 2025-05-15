package com.wenziyue.framework.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author wenziyue
 */
@Configuration
public class WebEncodingAutoConfiguration implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 删除默认的 ISO-8859-1 的字符串转换器
        converters.removeIf(converter -> converter instanceof StringHttpMessageConverter);
        // 添加我们自己的 UTF-8 字符串转换器
        converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }
}
