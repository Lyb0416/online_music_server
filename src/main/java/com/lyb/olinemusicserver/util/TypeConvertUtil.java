package com.lyb.olinemusicserver.util;

public class TypeConvertUtil {

    /**
     * 将 Object 安全转换为 long 类型
     * 支持 Integer, Long, Short, Byte 等
     */
    public static long toLong(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Cannot convert null to long");
        }
        if (obj instanceof Number) {
            return ((Number) obj).longValue(); // 安全转换
        }
        throw new IllegalArgumentException("Cannot convert " + obj.getClass() + " to long");
    }

    /**
     * 将 Object 安全转换为 int 类型
     * 支持 Integer, Long, Short, Byte 等
     */
    public static int toInt(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Cannot convert null to int");
        }
        if (obj instanceof Number) {
            return ((Number) obj).intValue(); // 安全转换
        }
        throw new IllegalArgumentException("Cannot convert " + obj.getClass() + " to int");
    }
}
