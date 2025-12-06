package com.wenziyue.framework.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.wenziyue.framework.common.ICommonEnum;

import java.io.IOException;

/**
 * Jackson 的枚举序列化器：
 * 用于将实现 ICommonEnum 的枚举序列化为 {code, desc}
 */
public class CommonEnumJsonSerializer extends JsonSerializer<ICommonEnum> {

    @Override
    public void serialize(ICommonEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }
        gen.writeStartObject();
        gen.writeObjectField("code", value.getCode());
        gen.writeStringField("desc", value.getDesc().toString());
        gen.writeEndObject();
    }
}