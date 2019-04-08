package com.imooc.utils;

import com.imooc.enums.CodeEnum;

public class EnumUtil {

    /**
     * 根据code来获取枚举对象
     * @param code
     * @param enumClass
     * @param <T>
     * @return
     */
    public static<T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T each : enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }

        return null;
    }
}
