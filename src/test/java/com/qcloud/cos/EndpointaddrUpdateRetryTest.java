package com.qcloud.cos;

import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.auth.COSSigner;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.DefaultCosHttpClient;
import com.qcloud.cos.internal.CosErrorResponseHandler;
import com.qcloud.cos.region.Region;
import org.apache.http.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.TextUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(PowerMockRunner.class)
@PrepareForTest({COSClient.class, DefaultCosHttpClient.class, ClientConfig.class, HttpClients.class})
@PowerMockIgnore({"javax.net.ssl.*"})
public class EndpointaddrUpdateRetryTest {
    private String appid_ = System.getenv("appid");
    private String secretId_ = System.getenv("secretId");
    private String secretKey_ = System.getenv("secretKey");
    private String region_ = System.getenv("region");
    private String bucket_ = System.getenv("bucket") + (int) (Math.random() * 10000) + "-" + appid_;

    @Test
    public void testHandlerErrorMessage() throws Exception {
        COSSigner signer = PowerMockito.mock(COSSigner.class);
        PowerMockito.whenNew(COSSigner.class).withNoArguments().thenReturn(signer);
        PowerMockito.when(signer, "sign", any(), any(), any()).thenAnswer((m)->{return null;});
        ClientConfig clientConfig = new ClientConfig(new Region(region_));
        clientConfig.setUseBasicAuth(true);
        clientConfig.turnOnRefreshEndpointAddrSwitch();

        PoolingHttpClientConnectionManager manager = PowerMockito.mock(PoolingHttpClientConnectionManager.class);
        PowerMockito.whenNew(PoolingHttpClientConnectionManager.class).withNoArguments().thenReturn(manager);

        CosErrorResponseHandler cosErrorResponseHandler = PowerMockito.mock(CosErrorResponseHandler.class);
        PowerMockito.whenNew(CosErrorResponseHandler.class).withNoArguments().thenReturn(cosErrorResponseHandler);
        PowerMockito.when(cosErrorResponseHandler.handle(any())).thenAnswer((m)->{
            XMLStreamException xmlStreamException = new XMLStreamException("test error");
            throw  xmlStreamException;
        });

        HttpClientBuilder httpClientBuilder = PowerMockito.mock(HttpClientBuilder.class);
        CloseableHttpClient httpClient = PowerMockito.mock(CloseableHttpClient.class);
        PowerMockito.mockStatic(HttpClients.class);
        PowerMockito.when(HttpClients.custom()).thenReturn(httpClientBuilder);
        PowerMockito.when(httpClientBuilder.build()).thenReturn(httpClient);

        ProtocolVersion protocolVersion = new ProtocolVersion("HTTP", 1,1);

        COSCredentials cred = new BasicCOSCredentials(secretId_, secretKey_);
        COSClient cosClient = new COSClient(cred, clientConfig);

        StatusLine statusLine = new BasicStatusLine(protocolVersion, 503, "Service Unavailable");
        HttpResponse response = new MockBasicHttpResponse(statusLine);
        PowerMockito.when(httpClient.execute((HttpUriRequest) any(), (HttpContext) any())).thenReturn((CloseableHttpResponse) response);

        try {
            cosClient.getObject(bucket_, "test_obj");
        } catch (CosServiceException cse) {
            assertEquals(503, cse.getStatusCode());
        }
    }

    private class MockBasicHttpResponse extends AbstractHttpMessage implements CloseableHttpResponse {

        private StatusLine statusline;
        private ProtocolVersion ver;
        private int                 code;
        private String              reasonPhrase;
        private HttpEntity entity;
        private final ReasonPhraseCatalog reasonCatalog;
        private Locale locale;

        /**
         * Creates a new response.
         * This is the constructor to which all others map.
         *
         * @param statusline        the status line
         * @param catalog           the reason phrase catalog, or
         *                          {@code null} to disable automatic
         *                          reason phrase lookup
         * @param locale            the locale for looking up reason phrases, or
         *                          {@code null} for the system locale
         */
        public MockBasicHttpResponse(final StatusLine statusline,
                                     final ReasonPhraseCatalog catalog,
                                     final Locale locale) {
            super();
            this.statusline = Args.notNull(statusline, "Status line");
            this.ver = statusline.getProtocolVersion();
            this.code = statusline.getStatusCode();
            this.reasonPhrase = statusline.getReasonPhrase();
            this.reasonCatalog = catalog;
            this.locale = locale;
        }

        /**
         * Creates a response from a status line.
         * The response will not have a reason phrase catalog and
         * use the system default locale.
         *
         * @param statusline        the status line
         */
        public MockBasicHttpResponse(final StatusLine statusline) {
            super();
            this.statusline = Args.notNull(statusline, "Status line");
            this.ver = statusline.getProtocolVersion();
            this.code = statusline.getStatusCode();
            this.reasonPhrase = statusline.getReasonPhrase();
            this.reasonCatalog = null;
            this.locale = null;
        }

        /**
         * Creates a response from elements of a status line.
         * The response will not have a reason phrase catalog and
         * use the system default locale.
         *
         * @param ver       the protocol version of the response
         * @param code      the status code of the response
         * @param reason    the reason phrase to the status code, or
         *                  {@code null}
         */
        public MockBasicHttpResponse(final ProtocolVersion ver,
                                     final int code,
                                     final String reason) {
            super();
            Args.notNegative(code, "Status code");
            this.statusline = null;
            this.ver = ver;
            this.code = code;
            this.reasonPhrase = reason;
            this.reasonCatalog = null;
            this.locale = null;
        }


        // non-javadoc, see interface HttpMessage
        @Override
        public ProtocolVersion getProtocolVersion() {
            return this.ver;
        }

        // non-javadoc, see interface HttpResponse
        @Override
        public StatusLine getStatusLine() {
            if (this.statusline == null) {
                this.statusline = new BasicStatusLine(
                        this.ver != null ? this.ver : HttpVersion.HTTP_1_1,
                        this.code,
                        this.reasonPhrase != null ? this.reasonPhrase : getReason(this.code));
            }
            return this.statusline;
        }

        // non-javadoc, see interface HttpResponse
        @Override
        public HttpEntity getEntity() {
            return this.entity;
        }

        @Override
        public Locale getLocale() {
            return this.locale;
        }

        // non-javadoc, see interface HttpResponse
        @Override
        public void setStatusLine(final StatusLine statusline) {
            this.statusline = Args.notNull(statusline, "Status line");
            this.ver = statusline.getProtocolVersion();
            this.code = statusline.getStatusCode();
            this.reasonPhrase = statusline.getReasonPhrase();
        }

        // non-javadoc, see interface HttpResponse
        @Override
        public void setStatusLine(final ProtocolVersion ver, final int code) {
            Args.notNegative(code, "Status code");
            this.statusline = null;
            this.ver = ver;
            this.code = code;
            this.reasonPhrase = null;
        }

        // non-javadoc, see interface HttpResponse
        @Override
        public void setStatusLine(
                final ProtocolVersion ver, final int code, final String reason) {
            Args.notNegative(code, "Status code");
            this.statusline = null;
            this.ver = ver;
            this.code = code;
            this.reasonPhrase = reason;
        }

        // non-javadoc, see interface HttpResponse
        @Override
        public void setStatusCode(final int code) {
            Args.notNegative(code, "Status code");
            this.statusline = null;
            this.code = code;
            this.reasonPhrase = null;
        }

        // non-javadoc, see interface HttpResponse
        @Override
        public void setReasonPhrase(final String reason) {
            this.statusline = null;
            this.reasonPhrase = TextUtils.isBlank(reason) ? null : reason;
        }

        // non-javadoc, see interface HttpResponse
        @Override
        public void setEntity(final HttpEntity entity) {
            this.entity = entity;
        }

        @Override
        public void setLocale(final Locale locale) {
            this.locale = Args.notNull(locale, "Locale");
            this.statusline = null;
        }

        /**
         * Looks up a reason phrase.
         * This method evaluates the currently set catalog and locale.
         * It also handles a missing catalog.
         *
         * @param code      the status code for which to look up the reason
         *
         * @return  the reason phrase, or {@code null} if there is none
         */
        protected String getReason(final int code) {
            return this.reasonCatalog != null ? this.reasonCatalog.getReason(code,
                    this.locale != null ? this.locale : Locale.getDefault()) : null;
        }

        @Override
        public void close() throws IOException {

        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append(getStatusLine());
            sb.append(' ');
            sb.append(this.headergroup);
            if (this.entity != null) {
                sb.append(' ');
                sb.append(this.entity);
            }
            return sb.toString();
        }
    }
}