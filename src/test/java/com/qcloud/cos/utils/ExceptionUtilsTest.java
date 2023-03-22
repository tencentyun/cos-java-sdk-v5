package com.qcloud.cos.utils;

import com.qcloud.cos.exception.ClientExceptionConstants;
import com.qcloud.cos.exception.CosClientException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.ConnectTimeoutException;
import org.junit.Test;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import static org.junit.Assert.assertEquals;

public class ExceptionUtilsTest {
    @Test
    public void  testExceptionUtils() {
        ConnectTimeoutException cte = new ConnectTimeoutException("test cte");
        CosClientException cce = ExceptionUtils.createClientException(cte);
        assertEquals(ClientExceptionConstants.CONNECTION_TIMEOUT, cce.getErrorCode());

        UnknownHostException unhe = new UnknownHostException();
        cce = ExceptionUtils.createClientException(unhe);
        assertEquals(ClientExceptionConstants.UNKNOWN_HOST, cce.getErrorCode());

        SocketTimeoutException ste = new SocketTimeoutException("socket timeout");
        cce = ExceptionUtils.createClientException(ste);
        assertEquals(ClientExceptionConstants.SOCKET_TIMEOUT, cce.getErrorCode());

        ClientProtocolException cpe = new ClientProtocolException("proto error");
        cce = ExceptionUtils.createClientException(cpe);
        assertEquals(ClientExceptionConstants.CLIENT_PROTOCAL_EXCEPTION, cce.getErrorCode());
    }
}
