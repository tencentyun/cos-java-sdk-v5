package com.qcloud.cos.utils;

import org.junit.Test;

import java.util.Date;

public class DateUtilsTest {
    @Test
    public void testParseISO8601Date() {
        String time_str = "292278994-03-29T10:05:45.001+0000";
        Date date = DateUtils.parseISO8601Date(time_str);
    }
}
