package com.qcloud.cos.utils;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CollectionUtilsTest {

    @Test
    public void isNullOrEmpty() {
        assertTrue(CollectionUtils.isNullOrEmpty(new ArrayList<>()));
    }
}