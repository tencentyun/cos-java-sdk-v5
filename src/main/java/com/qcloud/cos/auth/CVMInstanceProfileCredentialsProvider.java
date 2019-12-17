package com.qcloud.cos.auth;

import com.qcloud.cos.exception.CosClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class CVMInstanceProfileCredentialsProvider implements COSCredentialsProvider, Closeable {
    private static final Logger LOG = LoggerFactory.getLogger(CVMInstanceProfileCredentialsProvider.class);
    private static final int ASYNC_REFRESH_INTERVAL_TIME_MINUTES = 1;

    private static final CVMInstanceProfileCredentialsProvider INSTANCE = new CVMInstanceProfileCredentialsProvider();

    public static CVMInstanceProfileCredentialsProvider getInstance() {
        return INSTANCE;
    }

    private final CVMCredentialsFetcher cvmCredentialsFetcher;

    private volatile ScheduledExecutorService executorService;
    private volatile boolean shouldRefresh = false;

    private CVMInstanceProfileCredentialsProvider() {
        this(false);
    }

    private CVMInstanceProfileCredentialsProvider(boolean refreshCredentialsAsync) {
        this.cvmCredentialsFetcher =
                new CVMCredentialsFetcher(new CVMInstanceProfileCredentialsProvider.InstanceMetadataCredentialsEndpointProvider());

        if (refreshCredentialsAsync) {
            this.executorService = Executors.newScheduledThreadPool(1, new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread t = Executors.defaultThreadFactory().newThread(r);
                    t.setName("cvm-instance-profile-credentials-refresh");
                    t.setDaemon(true);
                    return t;
                }
            });

            this.executorService.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (shouldRefresh) {
                            cvmCredentialsFetcher.getCredentials();
                        }
                    } catch (CosClientException e) {
                        handleError(e);
                    }
                }
            }, 0, CVMInstanceProfileCredentialsProvider.ASYNC_REFRESH_INTERVAL_TIME_MINUTES, TimeUnit.MINUTES);
        }
    }

    public static CVMInstanceProfileCredentialsProvider createAsyncRefreshingProvider() {
        return new CVMInstanceProfileCredentialsProvider(true);
    }

    @Override
    public COSCredentials getCredentials() {
        COSCredentials cosCredentials = this.cvmCredentialsFetcher.getCredentials();
        this.shouldRefresh = true;
        return cosCredentials;
    }

    @Override
    public void refresh() {
        if (null != this.cvmCredentialsFetcher) {
            this.cvmCredentialsFetcher.refresh();
        }
    }

    @Override
    public void close() throws IOException {
        if (null != this.executorService) {
            this.executorService.shutdownNow();
            this.executorService = null;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (null != executorService) {
            executorService.shutdownNow();
        }
    }

    private void handleError(Throwable t) {
        this.refresh();
        LOG.error(t.getMessage(), t);
    }

    private static class InstanceMetadataCredentialsEndpointProvider extends CredentialsEndpointProvider {
        @Override
        public URI getCredentialsEndpoint() throws URISyntaxException, IOException {
            String host = CVMMetadataUtils.getHostAddressForCVMMetadataService();

            String securityCredentialsList =
                    InstanceCredentialsUtils.getInstance().readResource(new URI(host
                            + CVMMetadataUtils.CVM_METADATA_ROOT + CVMMetadataUtils.SECURITY_CREDENTIALS_RESOURCE));
            String[] securityCredentials = securityCredentialsList.trim().split("\n");
            if (securityCredentials.length == 0) {
                throw new CosClientException("Unable to load the credentials path. No invalid security credentials " +
                        "were found");
            }

            return new URI(host
                    + CVMMetadataUtils.CVM_METADATA_ROOT + CVMMetadataUtils.SECURITY_CREDENTIALS_RESOURCE + "/" + securityCredentials[0]);
        }
    }
}
