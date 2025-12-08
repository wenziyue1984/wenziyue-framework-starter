package com.wenziyue.framework.trace;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

import static com.wenziyue.auth.core.constants.AuthConstants.TRACE_ID_HEADER;

/**
 * 用于生成traceId
 *
 * @author wenziyue
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TraceIdFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        String traceId = req.getHeader(TRACE_ID_HEADER);
        if (!StringUtils.hasText(traceId)) {
            traceId = UUID.randomUUID().toString().replace("-", "");
        }

        try {
            MDC.put(TraceIdConstants.TRACE_ID, traceId);
            chain.doFilter(request, response);
        } finally {
            MDC.remove(TraceIdConstants.TRACE_ID);
        }
    }
}