package com.qcloud.cos.utils;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

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

        assertEquals(null, StringUtils.trim(null));
        assertEquals("hello", StringUtils.trim(str2));
        assertEquals(true, StringUtils.isNullOrEmpty(null));
        assertEquals(false, StringUtils.isNullOrEmpty(str2));
        assertEquals(null, StringUtils.removeQuotes(null));
    }
}
