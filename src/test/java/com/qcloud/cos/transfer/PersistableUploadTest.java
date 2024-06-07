package com.qcloud.cos.transfer;

import org.junit.Test;

import static org.junit.Assert.*;

public class PersistableUploadTest {

    @Test
    public void getPauseType() {
        PersistableUpload p = new PersistableUpload();
        assertEquals("upload", p.getPauseType());
    }
}