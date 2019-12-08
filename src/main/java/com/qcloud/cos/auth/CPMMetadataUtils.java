package com.qcloud.cos.auth;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class CPMMetadataUtils {
    private static final Logger LOG = LoggerFactory.getLogger(CPMMetadataUtils.class);

    public static final String CPM_METADATA_SERVICE_URL = "http://bm.metadata.tencentyun.com";
    public static final String CPM_METADATA_ROOT = "/meta-data";
    public static final String SECURITY_CREDENTIALS_RESOURCE = "/cam/security-credentials";

    private static final int DEFAULT_QUERY_RETRIES = 3;
    private static final int MINIMUM_WAIT_TIME_MILLISECONDS = 250;

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.PASCAL_CASE_TO_CAMEL_CASE);
    }

    public static class CAMSecurityCredential {
        public String tmpSecretId;
        public String tmpSecretKey;
        public String token;
        public long expiredTime;
        public String expiration;
        public String code;
    }

    public static Map<String, CAMSecurityCredential> getCAMSecurityCredentials() {
        Map<String, CAMSecurityCredential> credentialMap = new HashMap<>();

        List<String> credentials =
                CPMMetadataUtils.getItems(CPMMetadataUtils.CPM_METADATA_ROOT + CPMMetadataUtils.SECURITY_CREDENTIALS_RESOURCE);
        if (null != credentials) {
            for (String credential : credentials) {
                String json =
                        CPMMetadataUtils.getData(CPMMetadataUtils.CPM_METADATA_ROOT + CPMMetadataUtils.SECURITY_CREDENTIALS_RESOURCE + "/" + credential);
                if (null == json) {
                    continue;
                }
                try {
                    CAMSecurityCredential camSecurityCredential = mapper.readValue(json, CAMSecurityCredential.class);
                    credentialMap.put(credential, camSecurityCredential);
                } catch (Exception e) {
                    LOG.warn("Unable to process the credential (" + credential + "). " + e.getMessage(), e);
                }
            }
        }

        return credentialMap;
    }

    public static String getData(String path) {
        return getData(path, CPMMetadataUtils.DEFAULT_QUERY_RETRIES);
    }

    public static String getData(String path, int retries) {
        List<String> items = CPMMetadataUtils.getItems(path, retries, true);
        if (null != items && items.size() > 0) {
            return items.get(0);
        }

        return null;
    }

    public static List<String> getItems(String path) {
        return getItems(path, CPMMetadataUtils.DEFAULT_QUERY_RETRIES, false);
    }

    public static List<String> getItems(String path, int retries) {
        return getItems(path, retries, false);
    }

    public static List<String> getItems(String path, int retries, boolean slurp) {
        List<String> items = null;

        for (int i = 0; i <= retries; i++) {
            try {
                String response =
                        InstanceCredentialsUtils.getInstance().readResource(new URI(CPMMetadataUtils.getHostAddressForCPMMetadataService() + path));
                if (slurp) {
                    items = Collections.singletonList(response);
                } else {
                    items = Arrays.asList(response.split("\n"));
                }
                break;
            } catch (URISyntaxException e) {
                LOG.error("Unable to retrieve the requested metadata (" + path + "). " + e.getMessage(), e);
            } catch (IOException e) {
                try {
                    CPMMetadataUtils.retryWait(i + 1);
                } catch (InterruptedException ex) {
                    LOG.error("Waiting for retry to be interrupted.", ex);
                    break;
                }
            }
        }
        return items;
    }

    private static void retryWait(int retries) throws InterruptedException {
        long pause = (long) (Math.pow(2, DEFAULT_QUERY_RETRIES - retries) * MINIMUM_WAIT_TIME_MILLISECONDS);
        Thread.sleep(pause < MINIMUM_WAIT_TIME_MILLISECONDS ? MINIMUM_WAIT_TIME_MILLISECONDS : pause);
    }

    public static String getHostAddressForCPMMetadataService() {
        // XXX 这里后面需要考虑增加从环境变量中获取HostAddress的方式来提高程序的灵活性
        return CPMMetadataUtils.CPM_METADATA_SERVICE_URL;
    }
}
