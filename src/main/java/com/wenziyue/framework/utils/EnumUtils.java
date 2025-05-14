package com.wenziyue.framework.utils;

import com.wenziyue.framework.common.ICommonEnum;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 通用枚举工具类
 *
 * @author wenziyue
 */
public class EnumUtils {

    // 缓存所有枚举的 code -> Enum 实例 映射，按类型缓存
    private static final Map<Class<? extends ICommonEnum>, Map<Object, ? extends ICommonEnum>> CODE_CACHE = new ConcurrentHashMap<>();
    private static final Map<Class<? extends ICommonEnum>, Map<Object, ? extends ICommonEnum>> DESC_CACHE = new ConcurrentHashMap<>();


    /**
     * 通过 code 获取对应的枚举对象（支持缓存）
     * <p>
     * CODE_CACHE为懒加载模式，会在第一次调用的时候初始化，如：EnumUtils.fromCode(UserRoleEnum.class, 1)，然后缓存到CODE_CACHE中，下次调用的时候直接从缓存中获取。
     *
     * @param enumClass 枚举类 class
     * @param code      枚举的 code
     * @param <T>       枚举类型
     * @return 匹配的枚举实例
     */
    @SuppressWarnings("unchecked")
    public static <T extends ICommonEnum> T fromCode(Class<T> enumClass, Object code) {
        // 先从缓存获取 code -> 枚举对象 map
        Map<Object, ? extends ICommonEnum> map = CODE_CACHE.computeIfAbsent(enumClass, cls ->
                Arrays.stream(cls.getEnumConstants())
                        .collect(Collectors.toMap(ICommonEnum::getCode, e -> e))
        );
        T result = (T) map.get(code);
        if (result == null) {
            throw new IllegalArgumentException("无效的枚举 code：" + code + " for enum: " + enumClass.getSimpleName());
        }
        return result;
    }

    /**
     * 通过 code 获取对应的枚举对象（支持缓存）
     *
     * @param enumClass 枚举类 class
     * @param desc      枚举的 desc
     * @param <T>       枚举类型
     * @return 匹配的枚举实例
     */
    @SuppressWarnings("unchecked")
    public static <T extends ICommonEnum> T fromDesc(Class<T> enumClass, Object desc) {
        // 先从缓存获取 code -> 枚举对象 map
        Map<Object, ? extends ICommonEnum> map = DESC_CACHE.computeIfAbsent(enumClass, cls ->
                Arrays.stream(cls.getEnumConstants())
                        .collect(Collectors.toMap(ICommonEnum::getDesc, e -> e, (e1, e2) -> {
                            throw new IllegalStateException("Duplicate desc '" + e1.getDesc() + "' in enum: " + cls.getSimpleName());
                        }))
        );
        T result = (T) map.get(desc);
        if (result == null) {
            throw new IllegalArgumentException("无效的枚举 desc：" + desc + " for enum: " + enumClass.getSimpleName());
        }
        return result;
    }
}

