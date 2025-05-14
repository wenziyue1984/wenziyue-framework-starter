package com.wenziyue.framework.starter.trace;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.annotation.SchedulingConfigurer;

import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * 全面支持异步场景下的 TraceId 透传能力，包括：
 * - @Async
 * - Executor
 * - CompletableFuture
 * - 定时任务
 *
 * @author wenziyue
 */
@Slf4j
@Configuration
@ConditionalOnClass(name = "org.springframework.scheduling.annotation.AsyncConfigurer")
public class MdcAsyncSupport implements AsyncConfigurer, SchedulingConfigurer {

    @Bean
    public TaskDecorator mdcTaskDecorator() {
        return new MdcTaskDecorator();
    }

    @Bean(name = "asyncExecutor")
    @Override
    public Executor getAsyncExecutor() {
        log.info("初始化带 TraceId 的线程池 asyncExecutor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(200);
        executor.setThreadNamePrefix("async-exec-");
        executor.setTaskDecorator(mdcTaskDecorator());
        executor.initialize();
        return executor;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar registrar) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setThreadNamePrefix("schedule-");
        executor.setTaskDecorator(mdcTaskDecorator());
        executor.initialize();
        registrar.setScheduler(executor);
    }

    /**
     * 封装 CompletableFuture 异步执行时保留 TraceId
     */
    public static class Mdc {
        public static Runnable wrap(Runnable task) {
            Map<String, String> context = MDC.getCopyOfContextMap();
            return () -> {
                try {
                    if (context != null) {
                        MDC.setContextMap(context);
                    }
                    task.run();
                } finally {
                    MDC.clear();
                }
            };
        }

        public static <T> Supplier<T> wrap(Supplier<T> supplier) {
            Map<String, String> context = MDC.getCopyOfContextMap();
            return () -> {
                try {
                    if (context != null) {
                        MDC.setContextMap(context);
                    }
                    return supplier.get();
                } finally {
                    MDC.clear();
                }
            };
        }
    }
}

