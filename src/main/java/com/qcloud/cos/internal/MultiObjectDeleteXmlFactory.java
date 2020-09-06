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


package com.qcloud.cos.internal;

import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.DeleteObjectsRequest;
import com.qcloud.cos.model.DeleteObjectsRequest.KeyVersion;

/**
 * Factory for creating XML fragments from {@link DeleteObjectsRequest} objects
 * that can be sent to COS.
 */
public class MultiObjectDeleteXmlFactory {

    /**
     * Converts the specified {@link DeleteObjectsRequest} object to an XML fragment that
     * can be sent to Qcloud COS.
     *
     * @param rq
     *            The {@link DeleteObjectsRequest}
     */
    public byte[] convertToXmlByteArray(DeleteObjectsRequest rq) throws CosClientException {
        
        XmlWriter xml = new XmlWriter();
        xml.start("Delete");
        if ( rq.getQuiet() ) {
            xml.start("Quiet").value("true").end();
        }
        
        for (KeyVersion keyVersion : rq.getKeys()) {
            writeKeyVersion(xml, keyVersion);
        }

        xml.end();

        return xml.getBytes();
    }

    private void writeKeyVersion(XmlWriter xml, KeyVersion keyVersion) {
        xml.start("Object");
        xml.start("Key").value(keyVersion.getKey()).end();
        if (keyVersion.getVersion() != null) {
            xml.start("VersionId").value(keyVersion.getVersion()).end();
        }
        xml.end();
    }

}