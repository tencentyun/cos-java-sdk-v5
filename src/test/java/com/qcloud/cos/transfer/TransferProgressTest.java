package com.qcloud.cos.transfer;

import org.junit.Test;

import static org.junit.Assert.*;

public class TransferProgressTest {

    @Test
    public void getBytesTransferred() {
        TransferProgress p = new TransferProgress();
        assertEquals(0, p.getBytesTransferred());
        assertEquals(0, p.getBytesTransfered());

        assertEquals(-1, p.getTotalBytesToTransfer());
    }


    @Test
    public void getPercentTransferred_0() {
        TransferProgress p = new TransferProgress();
        p.updateProgress(-1);
        assertEquals(0.0, p.getPercentTransferred(), 0.1);
    }

    @Test
    public void getPercentTransferred_1() {
        TransferProgress p = new TransferProgress();
        p.setTotalBytesToTransfer(-1);
        assertEquals(-1.0, p.getPercentTransferred(), 0.1);
    }

    @Test
    public void getPercentTransferred_2() {
        TransferProgress p = new TransferProgress();
        p.setTotalBytesToTransfer(0);
        assertEquals(100.0, p.getPercentTransferred(), 0.1);
    }

    @Test
    public void getPercentTransferred_3() {
        TransferProgress p = new TransferProgress();
        p.updateProgress(50);
        p.setTotalBytesToTransfer(100);
        assertEquals(50.0, p.getPercentTransferred(), 0.1);
    }
}



