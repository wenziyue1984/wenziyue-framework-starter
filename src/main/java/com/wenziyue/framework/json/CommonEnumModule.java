package com.wenziyue.framework.json;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.wenziyue.framework.common.ICommonEnum;

/**
 * 注册 ICommonEnum 的统一序列化策略
 */
public class CommonEnumModule extends SimpleModule {
    public CommonEnumModule() {
        super("wenziyue-common-enum-module");
        addSerializer(ICommonEnum.class, new CommonEnumJsonSerializer());
    }
}