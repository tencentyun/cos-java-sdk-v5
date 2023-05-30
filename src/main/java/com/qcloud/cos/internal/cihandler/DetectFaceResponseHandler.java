package com.qcloud.cos.internal.cihandler;

import com.qcloud.cos.model.ciModel.image.DetectFaceResponse;
import com.qcloud.cos.model.ciModel.image.FaceInfo;
import com.qcloud.cos.model.ciModel.persistence.CarLocation;
import com.qcloud.cos.model.ciModel.persistence.CarTag;
import com.qcloud.cos.model.ciModel.persistence.PlateContent;
import org.xml.sax.Attributes;

import java.util.List;


public class DetectFaceResponseHandler extends CIAbstractHandler {
    public DetectFaceResponse response = new DetectFaceResponse();

    @Override
    protected void doStartElement(String uri, String name, String qName, Attributes attrs) {
        List<FaceInfo> faceInfo = response.getFaceInfo();
        if (in("Response") && "FaceInfos".equals(name)) {
            faceInfo.add(new FaceInfo());
        }
    }

    @Override
    protected void doEndElement(String uri, String name, String qName) {
        if (in("Response")) {
            switch (name) {
                case "ImageWidth":
                    response.setImageWidth(getText());
                    break;
                case "ImageHeight":
                    response.setImageHeight(getText());
                    break;
                case "FaceModelVersion":
                    response.setFaceModelVersion(getText());
                    break;
                case "RequestId":
                    response.setRequestId(getText());
                    break;
                default:
                    break;
            }
        } else if (in("Response", "FaceInfos")) {
            List<FaceInfo> faceInfo = response.getFaceInfo();
            if (faceInfo.isEmpty()) {
                return;
            }
            FaceInfo info = faceInfo.get(faceInfo.size() - 1);
            switch (name) {
                case "X":
                    info.setX(getText());
                    break;
                case "Y":
                    info.setY(getText());
                    break;
                case "Width":
                    info.setWidth(getText());
                    break;
                case "Height":
                    info.setHeight(getText());
                    break;
                default:
                    break;
            }
        }
    }

    public DetectFaceResponse getResponse() {
        return response;
    }

}
