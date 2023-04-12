package com.qcloud.cos.transfer;

import org.junit.Test;

import static org.junit.Assert.*;

public class PersistableDownloadTest {

    @Test
    public void getRange() {
        PersistableDownload p = new PersistableDownload(null, null, null, new long[2], null, null);
        assertNotNull(p.getRange());
    }

    @Test
    public void getPauseType() {
        PersistableDownload p = new PersistableDownload();
        assertNotNull(p);
        assertEquals(p.getPauseType(), "download");
    }
}