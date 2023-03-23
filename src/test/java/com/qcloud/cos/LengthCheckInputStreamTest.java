package com.qcloud.cos;

import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.internal.LengthCheckInputStream;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.fail;

public class LengthCheckInputStreamTest {
    @Test
    public void testLengthCheckInputStream() {
        int inputStreamLength = 1 * 1024 * 1024;
        byte data[] = new byte[inputStreamLength];
        InputStream inputStream = new ByteArrayInputStream(data);

        try {
            LengthCheckInputStream lcis = new LengthCheckInputStream(inputStream, -1, false);
        } catch (Exception e) {
            if (!(e instanceof IllegalArgumentException)) {
                fail(e.getMessage());
            }
        }

        try {
            LengthCheckInputStream lcis = new LengthCheckInputStream(inputStream, inputStreamLength, true);
            int c = lcis.read();
            lcis.reset();
            lcis.skip(100);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            LengthCheckInputStream lcis = new LengthCheckInputStream(inputStream, 0, true);
            int c = lcis.read();
        } catch (CosClientException cce) {
            if (!cce.getMessage().startsWith("More data read than expected:")){
                fail(cce.getMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
