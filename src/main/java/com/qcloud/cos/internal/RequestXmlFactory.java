package com.qcloud.cos.internal;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.qcloud.cos.model.PartETag;

public class RequestXmlFactory {

    /**
     * Converts the specified list of PartETags to an XML fragment that can be
     * sent to the CompleteMultipartUpload operation of Qcloud COS.
     *
     * @param partETags
     *            The list of part ETags containing the data to include in the
     *            new XML fragment.
     *
     * @return A byte array containing the data
     */
    public static byte[] convertToXmlByteArray(List<PartETag> partETags) {
        XmlWriter xml = new XmlWriter();
        xml.start("CompleteMultipartUpload");
        if (partETags != null) {
            Collections.sort(partETags, new Comparator<PartETag>() {
                public int compare(PartETag tag1, PartETag tag2) {
                    if (tag1.getPartNumber() < tag2.getPartNumber()) return -1;
                    if (tag1.getPartNumber() > tag2.getPartNumber()) return 1;
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
}