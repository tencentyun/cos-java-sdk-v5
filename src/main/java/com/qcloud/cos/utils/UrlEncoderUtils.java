package com.qcloud.cos.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UrlEncoderUtils {

    private static final Logger log = LoggerFactory.getLogger(UrlEncoderUtils.class);

    public static String encode(String originUrl) {
        try {
            return URLEncoder.encode(originUrl, "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            log.error("URLEncoder error, encode utf8, exception: {}", e);
        }
        return null;
    }
}
