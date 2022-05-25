package com.qcloud.cos.internal.cihandler;

import com.qcloud.cos.model.ciModel.image.ImageInfos;
import com.qcloud.cos.model.ciModel.image.ImageSearchResponse;
import org.xml.sax.Attributes;

import java.util.List;


public class SearchImageHandler extends CIAbstractHandler {
    public ImageSearchResponse response = new ImageSearchResponse();

    @Override
    protected void doStartElement(String uri, String name, String qName, Attributes attrs) {
        List<ImageInfos> imageInfos = response.getImageInfos();
        if (in("Response") && "ImageInfos".equals(name)) {
            imageInfos.add(new ImageInfos());
        }
    }

    @Override
    protected void doEndElement(String uri, String name, String qName) {
        if (in("Response")) {
            switch (name) {
                case "Count":
                    response.setCount(getText());
                    break;
                case "RequestId":
                    response.setRequestId(getText());
                    break;
                default:
                    break;
            }
        } else if (in("Response", "ImageInfos")) {
            List<ImageInfos> imageInfos = response.getImageInfos();
            ImageInfos imageInfo = imageInfos.get(imageInfos.size() - 1);
            switch (name) {
                case "EntityId":
                    imageInfo.setEntityId(getText());
                    break;
                case "PicName":
                    imageInfo.setPicName(getText());
                    break;
                case "Score":
                    imageInfo.setScore(getText());
                    break;
                case "CustomContent":
                    imageInfo.setCustomContent(getText());
                    break;
                case "Tags":
                    imageInfo.setTags(getText());
                    break;
                default:
                    break;
            }
        }
    }

    public ImageSearchResponse getResponse() {
        return response;
    }

    public void setResponse(ImageSearchResponse response) {
        this.response = response;
    }
}
