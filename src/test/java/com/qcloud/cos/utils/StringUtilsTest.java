package com.qcloud.cos.utils;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import static org.junit.Assert.*;

public class StringUtilsTest {
    @Test
    public void testStringUtils() {
        String str1 = "10";
        String str2 = "hello ";
        String str3 = "world!";
        int int_from_str = StringUtils.toInteger(new StringBuilder(str1));
        assertEquals(int_from_str, 10);
        assertEquals(StringUtils.fromInteger(int_from_str), str1);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str2);
        stringBuilder.append(str3);
        String str_combine = StringUtils.toString(stringBuilder);
        assertEquals( "hello world!", str_combine);
        assertEquals("hello world!", StringUtils.fromString(str_combine));

        String str_after_replace = StringUtils.replace(str_combine, str3, "");
        assertEquals(str2, str_after_replace);

        String str_trim = StringUtils.trim(null);
        assertNull(str_trim);
        str_trim = StringUtils.trim(str2);
        assertEquals("hello", str_trim);

        Boolean is_null_or_empty = StringUtils.isNullOrEmpty(null);
        assertTrue(is_null_or_empty);
        is_null_or_empty = StringUtils.isNullOrEmpty(str2);
        assertFalse(is_null_or_empty);

        String str_remove_quotes = StringUtils.removeQuotes(null);
        assertNull(str_remove_quotes);

        String str4 = "true";
        assertFalse(StringUtils.toBoolean(new StringBuilder(str4)));
        String str_from_bool = StringUtils.fromBoolean(true);
        assertEquals(str4, str_from_bool);

        long l1 = 1024;
        assertEquals("1024", StringUtils.fromLong(l1));

        Double d = 3.14;
        String str_from_double = StringUtils.fromDouble(d);
        assertEquals("3.14", str_from_double);

        Float f = 3.14f;
        String str_from_float = StringUtils.fromFloat(f);
        assertEquals("3.14", str_from_float);

        String time_str = "2023-03-29T10:05:45.000Z";
        Date date = DateUtils.parseISO8601Date(time_str);
        String str_from_date = StringUtils.fromDate(date);
        assertEquals(time_str, str_from_date);

        BigInteger bigInteger = StringUtils.toBigInteger("1024");
        String str_from_bigInteger = StringUtils.fromBigInteger(bigInteger);
        assertEquals("1024", str_from_bigInteger);

        BigDecimal bigDecimal = StringUtils.toBigDecimal("1024");
        String str_from_bigDecimal = StringUtils.fromBigDecimal(bigDecimal);
        assertEquals("1024", str_from_bigDecimal);

        String key = "/.../a/../b/c/../d/./";
        assertEquals(false, StringUtils.isRequestPathInvalid(key));

        key = "../";
        assertEquals(true, StringUtils.isRequestPathInvalid(key));
    }
}
