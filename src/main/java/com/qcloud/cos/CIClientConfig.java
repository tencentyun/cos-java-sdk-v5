/*
 * Copyright 2010-2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 
 * According to cos feature, we modify some class，comment, field name, etc.
 */

package com.qcloud.cos;

import com.qcloud.cos.endpoint.*;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.region.Region;

public class CIClientConfig extends ClientConfig {
    private HttpProtocol httpProtocol = HttpProtocol.https;

    public CIClientConfig() {
        super();
        super.setRegion(null) ;
        super.setHttpProtocol(httpProtocol);
        super.setEndpointBuilder(new CIRegionEndpointBuilder(null));
    }

    public CIClientConfig(Region region) {
        super();
        super.setRegion(region);
        super.setHttpProtocol(httpProtocol);
        super.setEndpointBuilder(new CIRegionEndpointBuilder(region));
    }

    public HttpProtocol getHttpProtocol() {
        return httpProtocol;
    }

    public void setHttpProtocol(HttpProtocol httpProtocol) {
        this.httpProtocol = httpProtocol;
    }
}
