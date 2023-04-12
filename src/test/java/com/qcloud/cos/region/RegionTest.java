package com.qcloud.cos.region;

import org.junit.Test;

import static org.junit.Assert.*;

public class RegionTest {

    @Test
    public void getDisplayName() {
    }

    /**
     * 判定相等是按region name，而不是按display name
     */
    @Test
    public void testEquals() {
        assertEquals(new Region("region_name", "display1")
                , new Region("region_name", "display2"));
    }

    @Test
    public void testNotEquals() {
        assertNotEquals(new Region("region_name1", "display")
                , new Region("region_name2", "display"));
    }
}