package com.wenziyue.framework.utils;

import java.util.UUID;

/**
 * @author wenziyue
 */
public class TraceIdUtils {

    public static String getTraceId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
