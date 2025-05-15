package com.wenziyue.framework.trace;

import org.springframework.core.task.TaskDecorator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 工具类：用于创建自动透传 TraceId 的线程池
 *
 * @author wenziyue
 */
public class MdcExecutors {

    /**
     * 创建带有 MDC TraceId 透传能力的固定线程池
     * @param nThreads 核心线程数
     * @param decorator starter 中提供的 MdcTaskDecorator
     * @return 带装饰功能的 ExecutorService
     */
    public static ExecutorService newFixedThreadPoolWithMdc(int nThreads, TaskDecorator decorator) {
        return Executors.newFixedThreadPool(nThreads, runnable -> {
            Runnable wrapped = decorator.decorate(runnable);
            Thread thread = new Thread(wrapped);
            thread.setName("mdc-thread-" + thread.getId());
            return thread;
        });
    }

    // 如果需要，也可以扩展 cached/scheduled/singleThread 等其他线程池创建方法
}
