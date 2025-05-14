package com.wenziyue.framework.starter.trace;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;

/**
 * 将当前线程的 MDC 内容传递给异步线程
 *
 * @author wenziyue
 */
public class MdcTaskDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable runnable) {
        // 拿到当前线程的 MDC 内容（副本）
        Map<String, String> contextMap = MDC.getCopyOfContextMap();
        return () -> {
            try {
                if (contextMap != null) {
                    MDC.setContextMap(contextMap);
                }
                runnable.run();
            } finally {
                MDC.clear(); // 避免线程复用导致脏数据
            }
        };
    }
}
