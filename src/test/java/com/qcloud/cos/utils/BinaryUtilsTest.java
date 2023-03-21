package com.qcloud.cos.utils;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;

public class BinaryUtilsTest {
    @Test
    public void testBinaryUtils() throws Exception{
        String str1 = "hello ";

        String str1_hex = BinaryUtils.toHex(str1.getBytes());
        assertEquals(str1_hex, "68656c6c6f20");

        String str1_base64 = BinaryUtils.toBase64(str1.getBytes());
        String str1_from_base64 = new String(BinaryUtils.fromBase64(str1_base64));
        assertEquals(str1, str1_from_base64);
        assertEquals(null, BinaryUtils.fromBase64(null));

        ByteBuffer tmpBuf = ByteBuffer.allocateDirect(1024 * 1024);
        tmpBuf.put(str1.getBytes());
        tmpBuf.flip();
        String str1_from_buf = new String(BinaryUtils.copyAllBytesFrom(tmpBuf));
        assertEquals(str1, str1_from_buf);

        ByteBuffer tmpBuf2 = ByteBuffer.allocateDirect(1024 * 1024);
        tmpBuf2.put(str1.getBytes());
        tmpBuf2.flip();
        String str = new String(BinaryUtils.copyBytesFrom(tmpBuf));
        assertEquals(str, str1);

        ByteBuffer tmpBuf3 = ByteBuffer.allocateDirect(1024 * 1024);
        tmpBuf3.put(str1.getBytes());
        tmpBuf3.flip();
        ByteArrayInputStream in = BinaryUtils.toStream(null);
        assertEquals(0, in.available());
        in.close();

        ByteArrayInputStream in2 = BinaryUtils.toStream(tmpBuf3);
        assertEquals(str1.length(), in2.available());

        assertEquals(null, BinaryUtils.copyAllBytesFrom(null));


    }
}
