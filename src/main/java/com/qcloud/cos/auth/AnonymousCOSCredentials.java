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


package com.qcloud.cos.auth;

public class AnonymousCOSCredentials implements COSCredentials {
    private String appId;

    /**
     * 
     * @param appId           the appid which your resource belong to.
     * @deprecated appid should be included in bucket name. for example if your appid
     * is 125123123, previous bucket is ott. you should set bucket as ott-125123123.
     * 
     * use {@link AnonymousCOSCredentials#AnonymousCOSCredentials()}
     */
    @Deprecated
    public AnonymousCOSCredentials(String appId) {
        super();
        if (appId == null) {
            throw new IllegalArgumentException("Appid cannot be null.");
        }
        try {
            Long.valueOf(appId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Appid is invalid num str.");
        }
        this.appId = appId;
    }
    
    public AnonymousCOSCredentials() {
        super();
        this.appId = null;
    }

    @Override
    public String getCOSAppId() {
        return appId;
    }

    @Override
    public String getCOSAccessKeyId() {
        return null;
    }

    @Override
    public String getCOSSecretKey() {
        return null;
    }

}
