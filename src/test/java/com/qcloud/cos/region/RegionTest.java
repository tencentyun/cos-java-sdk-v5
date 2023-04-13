package com.qcloud.cos.region;

import org.junit.Test;

import static org.junit.Assert.*;

public class RegionTest {
    /**
     * 判定相等是按region name，而不是按display name
     */
    @Test
    public void testEquals() {
        Region region  = new Region("only", "only");
        assertEquals(region, region);

        assertEquals(new Region("region_name", "display1")
                , new Region("region_name", "display2"));
    }

    @Test
    public void testNotEquals() {
        assertNotEquals(new Region("region_name1", "display")
                , new Region("region_name2", "display"));

        assertNotEquals(new Region("region_name1", "display")
                , new Object());
    }

    @Test
    public void testDispaly() {
        Region region  = new Region("cn-east", "display");
        assertEquals("cn-east", Region.formatRegion(region));
    }


    @Test
    public void testFormatRegion() {
        Region region  = new Region("only", "display");
        assertEquals("display", region.getDisplayName());
    }
}