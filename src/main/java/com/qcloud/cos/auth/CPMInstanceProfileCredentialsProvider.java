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

public class CPMInstanceProfileCredentialsProvider implements COSCredentialsProvider, Closeable {
    private static final Logger LOG = LoggerFactory.getLogger(CPMInstanceProfileCredentialsProvider.class);
    private static final int ASYNC_REFRESH_INTERVAL_TIME_MINUTES = 1;

    private static final CPMInstanceProfileCredentialsProvider INSTANCE = new CPMInstanceProfileCredentialsProvider();

    public static CPMInstanceProfileCredentialsProvider getInstance() {
        return INSTANCE;
    }

    private final CPMCredentialsFetcher cpmCredentialsFetcher;

    private volatile ScheduledExecutorService executorService;
    private volatile boolean shouldRefresh = false;

    private CPMInstanceProfileCredentialsProvider() {
        this(false);
    }

    private CPMInstanceProfileCredentialsProvider(boolean refreshCredentialsAsync) {
        this.cpmCredentialsFetcher =
                new CPMCredentialsFetcher(new CPMInstanceProfileCredentialsProvider.InstanceMetadataCredentialsEndpointProvider());

        if (refreshCredentialsAsync) {
            this.executorService = Executors.newScheduledThreadPool(1, new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread t = Executors.defaultThreadFactory().newThread(r);
                    t.setName("cpm-instance-profile-credentials-refresh");
                    t.setDaemon(true);
                    return t;
                }
            });

            this.executorService.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (shouldRefresh) {
                            cpmCredentialsFetcher.getCredentials();
                        }
                    } catch (CosClientException e) {
                        handleError(e);
                    }
                }
            }, 0, CPMInstanceProfileCredentialsProvider.ASYNC_REFRESH_INTERVAL_TIME_MINUTES, TimeUnit.MINUTES);
        }
    }

    public static CPMInstanceProfileCredentialsProvider createAsyncRefreshingProvider() {
        return new CPMInstanceProfileCredentialsProvider(true);
    }

    @Override
    public COSCredentials getCredentials() {
        COSCredentials cosCredentials = this.cpmCredentialsFetcher.getCredentials();
        this.shouldRefresh = true;
        return cosCredentials;
    }

    @Override
    public void refresh() {
        if (null != this.cpmCredentialsFetcher) {
            this.cpmCredentialsFetcher.refresh();
        }
    }

    @Override
    public void close() throws IOException {
        if (null != this.executorService) {
            this.executorService.shutdownNow();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (null != this.executorService) {
            this.executorService.shutdownNow();
        }
    }

    private void handleError(Throwable t) {
        this.refresh();
        LOG.error(t.getMessage(), t);
    }

    private static class InstanceMetadataCredentialsEndpointProvider extends CredentialsEndpointProvider {
        @Override
        public URI getCredentialsEndpoint() throws URISyntaxException, IOException {
            String host = CPMMetadataUtils.getHostAddressForCPMMetadataService();

            String securityCredentialsList =
                    InstanceCredentialsUtils.getInstance().readResource(new URI(host + CPMMetadataUtils.SECURITY_CREDENTIALS_RESOURCE));
            String[] securityCredentials = securityCredentialsList.trim().split("\n");
            if (0 == securityCredentials.length) {
                throw new CosClientException("Unable to load the credentials path. No invalid security credentials " +
                        "were found.");
            }

            return new URI(host + CPMMetadataUtils.SECURITY_CREDENTIALS_RESOURCE + "/" + securityCredentials[0]);
        }
    }
}
