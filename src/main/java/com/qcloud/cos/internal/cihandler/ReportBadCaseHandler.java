package com.qcloud.cos.internal.cihandler;

import org.xml.sax.Attributes;


public class ReportBadCaseHandler extends CIAbstractHandler {
    public String requestId = "";

    @Override
    protected void doStartElement(String uri, String name, String qName, Attributes attrs) {

    }

    @Override
    protected void doEndElement(String uri, String name, String qName) {
        if (in("Response")) {
            switch (name) {
                case "RequestId":
                    requestId = getText();
                    break;
                default:
                    break;
            }
        }
    }

    public String getResponse() {
        return requestId;
    }

}
