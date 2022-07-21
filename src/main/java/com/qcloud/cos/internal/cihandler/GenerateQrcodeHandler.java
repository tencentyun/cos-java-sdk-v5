package com.qcloud.cos.internal.cihandler;

import org.xml.sax.Attributes;


public class GenerateQrcodeHandler extends CIAbstractHandler {
    public String imageBase64 = "";

    @Override
    protected void doStartElement(String uri, String name, String qName, Attributes attrs) {

    }

    @Override
    protected void doEndElement(String uri, String name, String qName) {
        if (in("Response")) {
            switch (name) {
                case "ResultImage":
                    imageBase64 = getText();
                    break;
                default:
                    break;
            }
        }
    }

    public String getResponse() {
        return imageBase64;
    }

}
