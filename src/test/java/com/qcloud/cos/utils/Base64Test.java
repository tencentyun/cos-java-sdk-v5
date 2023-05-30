package com.qcloud.cos.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Base64Test {
    @Test
    public void testBase64() {
        String str = Base64.encodeAsString(null);
        assertEquals(null, str);

        assertEquals(null, Base64.encode(null));
        assertEquals(null, Base64.decode((String) null));
        assertEquals(null, Base64.decode((byte[]) null));

        str = "";
        byte[] bytes_decode = Base64.decode(str);
        assertEquals(0, bytes_decode.length);

        str = "hello world";
        byte[] bytes_encode = Base64.encode(str.getBytes());
        bytes_decode = Base64.decode(bytes_encode);
        String str_decode = new String(bytes_decode);
        assertEquals(str, str_decode);
    }
}
