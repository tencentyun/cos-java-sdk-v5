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
    public static Boolean objIsNotNull(Object obj) {
        //查询出对象所有的属性
        Field[] fields = obj.getClass().getDeclaredFields();
        //用于判断所有属性是否为空,如果参数为空则不查询
        for (Field field : fields) {
            //不检查 直接取值
            field.setAccessible(true);
            try {
                if (isNotNull(field.get(obj))) {
                    //不为空
                    return true;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static Boolean objIsNull(Object obj){
        return !objIsNotNull(obj);
    }

    public static boolean isNull(Object[] objs) {
        if (objs == null || objs.length == 0)
            return true;
        return false;
    }

    public static boolean isNull(Object obj) {
        if (obj == null || isNull(obj.toString())) {
            return true;
        }
        return false;
    }

    public static boolean isNull(Integer integer) {
        if (integer == null || integer == 0)
            return true;
        return false;
    }

    public static boolean isNull(Collection collection) {
        if (collection == null || collection.size() == 0)
            return true;
        return false;
    }

    public static boolean isNull(Map map) {
        if (map == null || map.size() == 0)
            return true;
        return false;
    }

    public static boolean isNull(String str) {
        return str == null || "".equals(str.trim())
                || "null".equals(str.toLowerCase());
    }

    public static boolean isNull(Long longs) {
        if (longs == null || longs == 0)
            return true;
        return false;
    }

    public static boolean isNotNull(Long longs) {
        return !isNull(longs);
    }

    public static boolean isNotNull(String str) {
        return !isNull(str);
    }

    public static boolean isNotNull(Collection collection) {
        return !isNull(collection);
    }

    public static boolean isNotNull(Map map) {
        return !isNull(map);
    }

    public static boolean isNotNull(Integer integer) {
        return !isNull(integer);
    }

    public static boolean isNotNull(Object[] objs) {
        return !isNull(objs);
    }

    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }
}
