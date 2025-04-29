package com.wenziyue.framework.starter.json;

import com.alibaba.fastjson.serializer.ValueFilter;
import com.wenziyue.framework.starter.common.ICommonEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Fastjson 的枚举序列化器：用于将 ICommonEnum 接口的枚举序列化为 {code, desc}
 */
public class CommonEnumValueFilter implements ValueFilter {
    @Override
    public Object process(Object object, String name, Object value) {
        if (value instanceof ICommonEnum) {
            ICommonEnum commonEnum = (ICommonEnum) value;
            Map<String, Object> map = new HashMap<>();
            map.put("code", commonEnum.getCode());
            map.put("desc", commonEnum.getDesc());
            return map;
        }
        return value;
    }
}
