package com.qcloud.cos.internal.cihandler;

import com.qcloud.cos.model.ciModel.ai.CreateAIObjectDetectJobResponse;
import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.List;

/**
 * 图像主体检测响应处理器
 */
public class AIObjectDetectHandler extends CIAbstractHandler {
    public CreateAIObjectDetectJobResponse response = new CreateAIObjectDetectJobResponse();

    @Override
    protected void doStartElement(String uri, String name, String qName, Attributes attrs) {
        if (in("RecognitionResult") && "DetectMultiObj".equals(name)) {
            List<CreateAIObjectDetectJobResponse.DetectMultiObj> detectMultiObj = response.getDetectMultiObj();
            if (detectMultiObj == null) {
                detectMultiObj = new ArrayList<>();
                response.setDetectMultiObj(detectMultiObj);
            }
            detectMultiObj.add(response.new DetectMultiObj());
        } else if (in("RecognitionResult", "DetectMultiObj") && "Location".equals(name)) {
            List<CreateAIObjectDetectJobResponse.DetectMultiObj> detectMultiObj = response.getDetectMultiObj();
            if (detectMultiObj != null && !detectMultiObj.isEmpty()) {
                CreateAIObjectDetectJobResponse.DetectMultiObj obj = detectMultiObj.get(detectMultiObj.size() - 1);
                obj.setLocation(response.new Location());
            }
        }
    }

    @Override
    protected void doEndElement(String uri, String name, String qName) {
        if (in("RecognitionResult")) {
            if ("Status".equals(name)) {
                response.setStatus(Integer.parseInt(getText()));
            }
        }

        List<CreateAIObjectDetectJobResponse.DetectMultiObj> detectMultiObj = response.getDetectMultiObj();
        if (detectMultiObj == null || detectMultiObj.isEmpty()) {
            return;
        }
        CreateAIObjectDetectJobResponse.DetectMultiObj obj = detectMultiObj.get(detectMultiObj.size() - 1);

        if (in("RecognitionResult", "DetectMultiObj")) {
            switch (name) {
                case "Name":
                    obj.setName(getText());
                    break;
                case "Sorce":
                    obj.setConfidence(getText());
                    break;
                case "Confidence":
                    obj.setConfidence(getText());
                    break;
                default:
                    break;
            }
        } else if (in("RecognitionResult", "DetectMultiObj", "Location")) {
            CreateAIObjectDetectJobResponse.Location location = obj.getLocation();
            if (location != null) {
                switch (name) {
                    case "X":
                        location.setX(Integer.parseInt(getText()));
                        break;
                    case "Y":
                        location.setY(Integer.parseInt(getText()));
                        break;
                    case "Width":
                        location.setWidth(Integer.parseInt(getText()));
                        break;
                    case "Height":
                        location.setHeight(Integer.parseInt(getText()));
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public CreateAIObjectDetectJobResponse getResponse() {
        return response;
    }

    public void setResponse(CreateAIObjectDetectJobResponse response) {
        this.response = response;
    }
}