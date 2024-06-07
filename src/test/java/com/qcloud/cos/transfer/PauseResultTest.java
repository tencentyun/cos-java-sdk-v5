package com.qcloud.cos.transfer;

import org.junit.Test;

import static org.junit.Assert.*;

public class PauseResultTest {

    @Test
    public void PauseResultException() {
        assertThrows(IllegalArgumentException.class, () -> new PauseResult<>( null, new Object()));
    }

    @Test
    public void PauseResult() {
        assertNotNull(new PauseResult(PauseStatus.SUCCESS));
    }
}