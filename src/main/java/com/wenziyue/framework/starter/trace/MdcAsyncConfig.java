package com.wenziyue.framework.starter.trace;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author wenziyue
 */
@Slf4j
@Configuration
@ConditionalOnClass(name = "org.springframework.scheduling.annotation.AsyncConfigurer")
public class MdcAsyncConfig implements AsyncConfigurer {

    @Bean(name = "asyncExecutor")
    @Override
    public Executor getAsyncExecutor() {
        log.info("初始化带 TraceId 的线程池 asyncExecutor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);       // 核心线程数
        executor.setMaxPoolSize(20);       // 最大线程数
        executor.setQueueCapacity(200);    // 队列容量
        executor.setThreadNamePrefix("async-exec-");
        executor.setTaskDecorator(new MdcTaskDecorator()); // 🌟 关键配置
        executor.initialize();
        return executor;
    }
}