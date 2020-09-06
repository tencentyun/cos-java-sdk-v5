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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.CASJobParameters;
import com.qcloud.cos.model.PartETag;
import com.qcloud.cos.model.RestoreObjectRequest;
import com.qcloud.cos.model.SelectObjectContentRequest;
import com.qcloud.cos.model.RequestProgress;
import com.qcloud.cos.model.SelectParameters;
import com.qcloud.cos.model.CSVInput;
import com.qcloud.cos.model.JSONInput;
import com.qcloud.cos.model.CSVOutput;
import com.qcloud.cos.model.JSONOutput;
import com.qcloud.cos.model.InputSerialization;
import com.qcloud.cos.model.OutputSerialization;
import com.qcloud.cos.model.ScanRange;


public class RequestXmlFactory {

    /**
     * Converts the specified list of PartETags to an XML fragment that can be sent to the
     * CompleteMultipartUpload operation of Qcloud COS.
     *
     * @param partETags The list of part ETags containing the data to include in the new XML
     *        fragment.
     *
     * @return A byte array containing the data
     */
    public static byte[] convertToXmlByteArray(List<PartETag> partETags) {
        XmlWriter xml = new XmlWriter();
        xml.start("CompleteMultipartUpload");
        if (partETags != null) {
            Collections.sort(partETags, new Comparator<PartETag>() {
                public int compare(PartETag tag1, PartETag tag2) {
                    if (tag1.getPartNumber() < tag2.getPartNumber())
                        return -1;
                    if (tag1.getPartNumber() > tag2.getPartNumber())
                        return 1;
                    return 0;
                }
            });

            for (PartETag partEtag : partETags) {
                xml.start("Part");
                xml.start("PartNumber").value(Integer.toString(partEtag.getPartNumber())).end();
                xml.start("ETag").value(partEtag.getETag()).end();
                xml.end();
            }
        }
        xml.end();

        return xml.getBytes();
    }

    /**
     * Converts the SelectObjectContentRequest to an XML fragment that can be sent to
     * the SelectObjectContent operation of COS.
     */
    public static byte[] convertToXmlByteArray(SelectObjectContentRequest selectRequest) {
        XmlWriter xml = new XmlWriter();
        xml.start("SelectObjectContentRequest");

        addIfNotNull(xml, "Expression", selectRequest.getExpression());
        addIfNotNull(xml, "ExpressionType", selectRequest.getExpressionType());

        addScanRangeIfNotNull(xml,  selectRequest.getScanRange());
        addRequestProgressIfNotNull(xml, selectRequest.getRequestProgress());
        addInputSerializationIfNotNull(xml, selectRequest.getInputSerialization());
        addOutputSerializationIfNotNull(xml, selectRequest.getOutputSerialization());

        xml.end();
        return xml.getBytes();
    }


    private static void addRequestProgressIfNotNull(XmlWriter xml, RequestProgress requestProgress) {
        if (requestProgress == null) {
            return;
        }

        xml.start("RequestProgress");

        addIfNotNull(xml, "Enabled", requestProgress.getEnabled());

        xml.end();
    }

    private static void addSelectParametersIfNotNull(XmlWriter xml, SelectParameters selectParameters) {
        if (selectParameters == null) {
            return;
        }

        xml.start("SelectParameters");

        addInputSerializationIfNotNull(xml, selectParameters.getInputSerialization());

        addIfNotNull(xml, "ExpressionType", selectParameters.getExpressionType());
        addIfNotNull(xml, "Expression", selectParameters.getExpression());

        addOutputSerializationIfNotNull(xml, selectParameters.getOutputSerialization());

        xml.end();
    }

    private static void addScanRangeIfNotNull(XmlWriter xml, ScanRange scanRange) {
        if(scanRange != null) {
            xml.start("ScanRange");

            addIfNotNull(xml, "Start", scanRange.getStart());
            addIfNotNull(xml, "End", scanRange.getEnd());

            xml.end();
        }
    }

    private static void addInputSerializationIfNotNull(XmlWriter xml, InputSerialization inputSerialization) {
        if (inputSerialization != null) {
            xml.start("InputSerialization");

            if (inputSerialization.getCsv() != null) {
                xml.start("CSV");
                CSVInput csvInput = inputSerialization.getCsv();
                addIfNotNull(xml, "FileHeaderInfo", csvInput.getFileHeaderInfo());
                addIfNotNull(xml, "Comments", csvInput.getCommentsAsString());
                addIfNotNull(xml, "QuoteEscapeCharacter", csvInput.getQuoteEscapeCharacterAsString());
                addIfNotNull(xml, "RecordDelimiter", csvInput.getRecordDelimiterAsString());
                addIfNotNull(xml, "FieldDelimiter", csvInput.getFieldDelimiterAsString());
                addIfNotNull(xml, "QuoteCharacter", csvInput.getQuoteCharacterAsString());
                addIfNotNull(xml, "AllowQuotedRecordDelimiter", csvInput.getAllowQuotedRecordDelimiter());
                xml.end();
            }

            if (inputSerialization.getJson() != null) {
                xml.start("JSON");
                JSONInput jsonInput = inputSerialization.getJson();
                addIfNotNull(xml, "Type", jsonInput.getType());
                xml.end();
            }

            if (inputSerialization.getParquet() != null) {
                xml.start("Parquet");
                xml.end();
            }

            addIfNotNull(xml, "CompressionType", inputSerialization.getCompressionType());

            xml.end();
        }
    }

    private static void addOutputSerializationIfNotNull(XmlWriter xml, OutputSerialization outputSerialization) {
        if (outputSerialization != null) {
            xml.start("OutputSerialization");

            if (outputSerialization.getCsv() != null) {
                xml.start("CSV");
                CSVOutput csvOutput = outputSerialization.getCsv();
                addIfNotNull(xml, "QuoteFields", csvOutput.getQuoteFields());
                addIfNotNull(xml, "QuoteEscapeCharacter", csvOutput.getQuoteEscapeCharacterAsString());
                addIfNotNull(xml, "RecordDelimiter", csvOutput.getRecordDelimiterAsString());
                addIfNotNull(xml, "FieldDelimiter", csvOutput.getFieldDelimiterAsString());
                addIfNotNull(xml, "QuoteCharacter", csvOutput.getQuoteCharacterAsString());
                xml.end();
            }

            if (outputSerialization.getJson() != null) {
                xml.start("JSON");
                JSONOutput jsonOutput = outputSerialization.getJson();
                addIfNotNull(xml, "RecordDelimiter", jsonOutput.getRecordDelimiterAsString());
                xml.end();
            }

            xml.end();
        }
    }

    /**
     * Converts the RestoreObjectRequest to an XML fragment that can be sent to the RestoreObject
     * operation of COS.
     *
     * @param restoreObjectRequest The container which provides options for restoring an object,
     *        which was transitioned to the CAS from COS when it was expired, into COS again.
     *
     * @return A byte array containing the data
     *
     * @throws CosClientException
     */
    public static byte[] convertToXmlByteArray(RestoreObjectRequest restoreObjectRequest)
            throws CosClientException {
        XmlWriter xml = new XmlWriter();

        xml.start("RestoreRequest");
        xml.start("Days").value(Integer.toString(restoreObjectRequest.getExpirationInDays())).end();
        final CASJobParameters casJobParameters = restoreObjectRequest.getCasJobParameters();
        if (casJobParameters != null) {
            xml.start("CASJobParameters");
            addIfNotNull(xml, "Tier", casJobParameters.getTier());
            xml.end();
        }
        xml.end();

        return xml.getBytes();
    }

    private static void addIfNotNull(XmlWriter xml, String xmlTag, String value) {
        if (value != null) {
            xml.start(xmlTag).value(value).end();
        }
    }

    private static void addIfNotNull(XmlWriter xml, String xmlTag, Object value) {
        if (value != null && value.toString() != null) {
            xml.start(xmlTag).value(value.toString()).end();
        }
    }
}
