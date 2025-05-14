package com.wenziyue.framework.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wenziyue
 */
@AllArgsConstructor
@Getter
public enum CommonCode implements IResultCode {
    ACCESS_DENIED("403", "无访问权限"),
    TOKEN_EXPIRED("4030", "access_token is invalid."),
    TOKEN_VERIFY_FAILED("4031", "access_token verification failed"),
    TOKEN_MISSING("4032", "access_token missing."),
    LACK_TOKEN("4033", "找不到请求参数access_token 或 Authorization 认证凭证."),
    SYSTEM_EXCEPTION("5000", "系统异常"),
    CODE_ERROR("5001", "验证码错误"),
    NO_DATA("5003", "数据不存在"),
    PARAMETER_ERROR("5004", "参数错误"),

    /**
     * 响应成功
     */
    OK("2000", "success"),

    /**
     * 未经授权
     */
    UNAUTHORIZED("30101", "unauthorized"),

    /**
     * 调用端错误
     */
    FAIL_CLIENT("40000", "fail clinet"),

    /**
     * 异常参数
     */
    ILLEGAL_PARAMS("40101", "illegal params"),

    /**
     * 不合法的操作
     */
    ILLEGAL_OPERATION("40102", "illegal operation"),

    /**
     * 重复操作（requestId重复）
     */
    DUPLICATE_REQUEST("40103", "duplicate request"),

    /**
     * 无效的参数格式
     */
    INVALID_PARAMS_FORMAT("40104", "invalid param format"),

    /**
     * 接口已禁用
     */
    API_DISABLE("40105", "api disable"),

    /**
     * 该功能已被限制
     */
    FUNCTION_RESTRICT("40106", "function restrict"),

    /**
     * 找不到数据记录
     */
    RECORD_NOT_EXISTS("40201", "record not exists"),

    /**
     * 库存不足
     */
    NO_INVENTORY("40202", "no inventory"),

    /**
     * 服务端错误
     */
    FAIL_SERVER("50000", "fail server"),

    /**
     * 第三方服务返回失败
     */
    THIRD_PARTY_ERROR("50101", "third party error"),

    /**
     * 已达到最大限流
     */
    MAX_FLOW_LIMIT("50201", "max flow limit"),

    /**
     * sku数量在当前时间段内达到上限
     */
    MAX_HOT_SKU_LIMIT("50202", "max hot sku limit"),

    /**
     * 服务端内部异常
     */
    SERVER_INNER_ERROR("50301", "server inner error"),

    /**
     * 数据库访问异常
     */
    DB_ACCESS_ERROR("50302", "db access error");


    private final String code;
    private final String msg;
}
