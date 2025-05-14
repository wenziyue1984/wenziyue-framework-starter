package com.wenziyue.framework.common;

/**
 * 通用枚举接口，用于配合 FastJSON 序列化
 * @author wenziyue
 */
public interface ICommonEnum {

    /**
     * 得到枚举的值
     * @return 枚举的值
     */
    Object getCode();
    /**
     * 得到枚举的描述
     * @return 枚举的描述
     */
    Object getDesc();
}
