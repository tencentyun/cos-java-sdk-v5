package com.qcloud.cos.utils;

import java.io.StringWriter;
import org.junit.Test;

import static org.junit.Assert.*;

public class JacksonTest {


    @Test
    public void jsonNodeOf() {
        assertNotNull(Jackson.jsonNodeOf("  \"err\":\n" +
                "    {\n" +
                "        \"msg\": \"\",\n" +
                "        \"rt\": 0\n" +
                "    }"));
    }

    @Test
    public void jsonGeneratorOf() throws Exception{
        assertNotNull(Jackson.jsonGeneratorOf(new StringWriter()));
    }
}