package com.wenziyue.framework.trace;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import java.io.IOException;
import java.util.UUID;

/**
 * 用于生成traceId
 *
 * @author wenziyue
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TraceIdFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            String traceId = UUID.randomUUID().toString().replace("-", "");
            MDC.put(TraceIdConstants.TRACE_ID, traceId);
            chain.doFilter(request, response);
        } finally {
            MDC.remove(TraceIdConstants.TRACE_ID);
        }
    }
}