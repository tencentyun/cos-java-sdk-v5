package com.qcloud.cos.internal;

import org.junit.Test;

import static org.junit.Assert.*;

public class XmlWriterTest {

    @Test
    public void getByte() {
        XmlWriter w = new XmlWriter();
        w.start("name", "key", "\t\n\r&\"><");
        w.end();
        assertNotNull(w.getBytes());
    }

    @Test
    public void getnull() {
        XmlWriter w = new XmlWriter();
        w.start("name", "key", null);
        w.end();
        assertNotNull(w.getBytes());
    }
}