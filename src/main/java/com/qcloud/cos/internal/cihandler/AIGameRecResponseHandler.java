package com.qcloud.cos.internal.cihandler;

import com.qcloud.cos.model.ciModel.image.FaceInfo;
import com.qcloud.cos.model.ciModel.persistence.AIGameRecResponse;
import org.xml.sax.Attributes;

import java.util.List;


public class AIGameRecResponseHandler extends CIAbstractHandler {
    public AIGameRecResponse response = new AIGameRecResponse();

    @Override
    protected void doStartElement(String uri, String name, String qName, Attributes attrs) {
    }

    @Override
    protected void doEndElement(String uri, String name, String qName) {
        if (in("RecognitionResult", "GameLabels")) {
            switch (name) {
                case "Confidence":
                    response.setConfidence(getText());
                    break;
                case "FirstCategory":
                    response.setFirstCategory(getText());
                    break;
                case "SecondCategory":
                    response.setSecondCategory(getText());
                    break;
                case "GameName":
                    response.setGameName(getText());
                    break;
                default:
                    break;
            }
        }
    }

    public AIGameRecResponse getResponse() {
        return response;
    }

}
