package com.qcloud.cos.transfer;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class PersistableResumeDownloadTest {

    @Test
    public void getDownloadedBlocks() {
        PersistableResumeDownload p = new PersistableResumeDownload(0, null, "etag", null, new HashMap<String, Integer>());
        assertEquals("etag", p.getEtag());
        assertNotNull(p.getDownloadedBlocks());

        p.reset();
        assertFalse(p.hasDownloadedBlocks("any"));
    }


    @Test
    public void getLastModified() {
        PersistableResumeDownload p = new PersistableResumeDownload(0, null, null, null, null);
        assertEquals(0L, p.getLastModified());
    }
}