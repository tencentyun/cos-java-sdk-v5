package com.qcloud.cos.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UrlEncoderUtils {

    private static final String PATH_DELIMITER = "/";
    private static final Logger log = LoggerFactory.getLogger(UrlEncoderUtils.class);

    public static String encode(String originUrl) {
        try {
            return URLEncoder.encode(originUrl, "UTF-8").replace("+", "%20").replace("*", "%2A")
                    .replace("%7E", "~");
        } catch (UnsupportedEncodingException e) {
            log.error("URLEncoder error, encode utf8, exception: {}", e);
        }
        return null;
    }

    // encode路径, 不包括分隔符
    public static String encodeEscapeDelimiter(String urlPath) {
        StringBuilder pathBuilder = new StringBuilder();
        String[] pathSegmentsArr = urlPath.split(PATH_DELIMITER);

        boolean isFirstSegMent = true;
        for (String pathSegment : pathSegmentsArr) {
            if (isFirstSegMent) {
                pathBuilder.append(encode(pathSegment));
                isFirstSegMent = false;
            } else {
                pathBuilder.append(PATH_DELIMITER).append(encode(pathSegment));
            }
        }
        if (urlPath.endsWith(PATH_DELIMITER)) {
            pathBuilder.append(PATH_DELIMITER);
        }
        return pathBuilder.toString();
    }

}
