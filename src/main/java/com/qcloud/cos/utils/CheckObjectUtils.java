package com.qcloud.cos.utils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

/**
 * 对象校验工具类
 */
public class CheckObjectUtils {

    /**
     * 校验对象中是否包含非空字段
     * @param obj
     * @return 包含 true 所有字段都为空 false
     */
    public static Boolean objIsNotBlank(Object obj) {
        //查询出对象所有的属性
        Field[] fields = obj.getClass().getDeclaredFields();
        //用于判断所有属性是否为空,如果参数为空则不查询
        for (Field field : fields) {
            //不检查 直接取值
            field.setAccessible(true);
            try {
                if (!isBlank(field.get(obj))) {
                    //不为空
                    return true;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static Boolean objIsBlank(Object obj){
        return !objIsNotBlank(obj);
    }

    public static boolean isBlank(Object obj) {
        if (obj == null || isBlank(obj.toString())) {
            return true;
        }
        return false;
    }

    public static boolean isBlank(String str) {
        return str == null || "".equals(str.trim())
                || "null".equalsIgnoreCase(str);
    }
}
