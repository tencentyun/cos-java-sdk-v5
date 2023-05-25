package com.qcloud.cos.internal.cihandler;

import com.qcloud.cos.model.ciModel.image.AutoTranslationBlockResponse;
import org.xml.sax.Attributes;


public class AutoTranslationBlockResponseHandler extends CIAbstractHandler {
    public AutoTranslationBlockResponse response = new AutoTranslationBlockResponse();

    @Override
    protected void doStartElement(String uri, String name, String qName, Attributes attrs) {

    }

    @Override
    protected void doEndElement(String uri, String name, String qName) {
        if ("TranslationResult".equalsIgnoreCase(name)) {
            response.setTranslationResult(getText());
        }
    }

    public AutoTranslationBlockResponse getResponse() {
        return response;
    }

}
