package com.qcloud.cos.utils;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.Assert.assertEquals;

public class IOUtilsTest {
    @Test
    public void testIOUtils() throws Exception{
        int inputStreamLength = 100;
        byte data[] = new byte[inputStreamLength];
        InputStream inputStream = new ByteArrayInputStream(data);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        long count = IOUtils.copy(inputStream, baos);
        assertEquals(inputStreamLength, count);
    }
}
