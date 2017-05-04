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
            return URLEncoder.encode(originUrl, "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            log.error("URLEncoder error, encode utf8, exception: {}", e);
        }
        return null;
    }

    // encode路径, 不包括分隔符
    public static String encodeEscapeDelimiter(String urlPath) {
        StringBuilder pathBuilder = new StringBuilder();
        String[] pathSegmentsArr = urlPath.split(PATH_DELIMITER);

        for (String pathSegment : pathSegmentsArr) {
            if (!pathSegment.isEmpty()) {
                try {
                    pathBuilder.append(PATH_DELIMITER)
                            .append(URLEncoder.encode(pathSegment, "UTF-8").replace("+", "%20"));
                } catch (UnsupportedEncodingException e) {
                    log.error("URLEncoder error, encode utf8, exception: {}", e);
                }
            }
        }
        if (urlPath.endsWith(PATH_DELIMITER)) {
            pathBuilder.append(PATH_DELIMITER);
        }
        return pathBuilder.toString();
    }

}
