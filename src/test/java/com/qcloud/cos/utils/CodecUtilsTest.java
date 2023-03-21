package com.qcloud.cos.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CodecUtilsTest {
    @Test
    public void testCodecUtils() throws Exception {
        String str = "hello world";
        byte[] str_bytes = new byte[str.length()];
        int limit = CodecUtils.sanitize(str, str_bytes);
        assertEquals(str.length() - 1, limit);

        byte[] str_bytes2 = CodecUtils.toBytesDirect(str);
        String str2 = CodecUtils.toStringDirect(str_bytes2);
        assertEquals(str, str2);

        assertEquals(null, CodecUtils.convertFromUtf8ToIso88591(null));
        assertEquals(null, CodecUtils.convertFromIso88591ToUtf8(null));
    }
}
