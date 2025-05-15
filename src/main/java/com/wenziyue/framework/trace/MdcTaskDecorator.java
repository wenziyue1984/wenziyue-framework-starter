package com.wenziyue.framework.trace;

import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;
import java.util.UUID;

/**
 * 将当前线程的 MDC 内容传递给异步线程
 *
 * @author wenziyue
 */
@Configuration
public class MdcTaskDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable runnable) {
        // 获取当前上下文
        Map<String, String> contextMap = MDC.getCopyOfContextMap();
        return () -> {
            try {
                // 如果没有 traceId，我们自动生成一个（解决定时任务没有 traceId 的问题）
                if (contextMap == null || !contextMap.containsKey("traceId")) {
                    String traceId = UUID.randomUUID().toString().replace("-", "");
                    MDC.put("traceId", traceId);
                } else {
                    // 拿到当前线程的 MDC 内容（副本）
                    MDC.setContextMap(contextMap);
                }

                runnable.run();
            } finally {
                MDC.clear(); // 避免线程复用导致脏数据
            }
        };
    }
}
