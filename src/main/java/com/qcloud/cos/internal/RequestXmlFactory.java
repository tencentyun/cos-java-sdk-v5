package com.qcloud.cos.internal;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.CASJobParameters;
import com.qcloud.cos.model.PartETag;
import com.qcloud.cos.model.RestoreObjectRequest;

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
}
