package com.qcloud.cos.endpoint;

import com.qcloud.cos.region.Region;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CIPicRegionEndpointBuilderTest {

    private Region mockRegion = new Region("ap-chongqing");

    private CIPicRegionEndpointBuilder ciPicRegionEndpointBuilderUnderTest;

    @Before
    public void setUp() throws Exception {
        ciPicRegionEndpointBuilderUnderTest = new CIPicRegionEndpointBuilder(mockRegion);
    }

    @Test
    public void testBuildGeneralApiEndpoint() {
        assertEquals("test-123456789.pic.ap-chongqing.myqcloud.com", ciPicRegionEndpointBuilderUnderTest.buildGeneralApiEndpoint("test-123456789"));
    }

    @Test
    public void testBuildGetServiceApiEndpoint() {
        assertEquals("service.pic.myqcloud.com", ciPicRegionEndpointBuilderUnderTest.buildGetServiceApiEndpoint());
    }
}
