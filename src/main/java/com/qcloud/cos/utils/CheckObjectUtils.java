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
                if (isNotBlank(field.get(obj))) {
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

    public static boolean isBlank(Object[] objs) {
        if (objs == null || objs.length == 0)
            return true;
        return false;
    }

    public static boolean isBlank(Object obj) {
        if (obj == null || isBlank(obj.toString())) {
            return true;
        }
        return false;
    }

    public static boolean isBlank(Integer integer) {
        if (integer == null || integer == 0)
            return true;
        return false;
    }

    public static boolean isBlank(Collection collection) {
        if (collection == null || collection.size() == 0)
            return true;
        return false;
    }

    public static boolean isBlank(Map map) {
        if (map == null || map.size() == 0)
            return true;
        return false;
    }

    public static boolean isBlank(String str) {
        return str == null || "".equals(str.trim())
                || "null".equals(str.toLowerCase());
    }

    public static boolean isBlank(Long longs) {
        if (longs == null || longs == 0)
            return true;
        return false;
    }

    public static boolean isNotBlank(Long longs) {
        return !isBlank(longs);
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isNotBlank(Collection collection) {
        return !isBlank(collection);
    }

    public static boolean isNotBlank(Map map) {
        return !isBlank(map);
    }

    public static boolean isNotBlank(Integer integer) {
        return !isBlank(integer);
    }

    public static boolean isNotBlank(Object[] objs) {
        return !isBlank(objs);
    }

    public static boolean isNotBlank(Object obj) {
        return !isBlank(obj);
    }
}
