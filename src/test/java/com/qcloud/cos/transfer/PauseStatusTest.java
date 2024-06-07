package com.qcloud.cos.transfer;

import org.junit.Test;

import static org.junit.Assert.*;

public class PauseStatusTest {

    @Test
    public void isPaused() {
        PauseStatus pauseStatus = PauseStatus.SUCCESS;
        assertTrue(pauseStatus.isPaused());

        pauseStatus = PauseStatus.CANCELLED;
        assertFalse(pauseStatus.isPaused());
    }

    @Test
    public void isCancelled() {
        PauseStatus pauseStatus = PauseStatus.CANCELLED;
        assertTrue(pauseStatus.isCancelled());

        pauseStatus = PauseStatus.CANCELLED_BEFORE_START;
        assertTrue(pauseStatus.isCancelled());

        pauseStatus = PauseStatus.SUCCESS;
        assertFalse(pauseStatus.isCancelled());
    }

    @Test
    public void unchanged() {
        PauseStatus pauseStatus = PauseStatus.NO_EFFECT;
        assertTrue(pauseStatus.unchanged());

        pauseStatus = PauseStatus.NOT_STARTED;
        assertTrue(pauseStatus.unchanged());

        pauseStatus = PauseStatus.SUCCESS;
        assertFalse(pauseStatus.unchanged());
    }
}