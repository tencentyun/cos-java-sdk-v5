package com.qcloud.cos.internal;

import java.io.Closeable;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SdkIOUtils {
    private static final Logger defaultLog = LoggerFactory.getLogger(SdkIOUtils.class);

    public static void closeQuietly(Closeable is) {
        closeQuietly(is, null);
    }

    /**
     * Closes the given Closeable quietly.
     * 
     * @param is the given closeable
     * @param log logger used to log any failure should the close fail
     */
    public static void closeQuietly(Closeable is, Logger log) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException ex) {
                Logger logger = log == null ? defaultLog : log;
                if (logger.isDebugEnabled())
                    logger.debug("Ignore failure in closing the Closeable", ex);
            }
        }
    }
}
