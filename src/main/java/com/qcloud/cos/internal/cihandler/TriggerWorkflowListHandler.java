package com.qcloud.cos.internal.cihandler;

import com.qcloud.cos.internal.ParserMediaInfoUtils;
import com.qcloud.cos.model.ciModel.template.MediaWaterMarkImage;
import com.qcloud.cos.model.ciModel.template.MediaWaterMarkText;
import com.qcloud.cos.model.ciModel.template.MediaWatermark;
import com.qcloud.cos.model.ciModel.workflow.AttachParam;
import com.qcloud.cos.model.ciModel.workflow.MediaWorkflowListResponse;
import org.xml.sax.Attributes;


public class TriggerWorkflowListHandler extends CIAbstractHandler {
    public MediaWorkflowListResponse response = new MediaWorkflowListResponse();

    @Override
    protected void doStartElement(String uri, String name, String qName, Attributes attrs) {

    }

    @Override
    protected void doEndElement(String uri, String name, String qName) {
        AttachParam attachParam = response.getAttachParam();
        if (attachParam == null) {
           response.setAttachParam(new AttachParam());
        }

        if (in("Response")) {
            switch (name) {
                case "InstanceId":
                    response.setInstanceId(getText());
                    break;
                case "RequestId":
                    response.setRequestId(getText());
                    break;
                default:
                    break;
            }
        } else if (in("Response", "AttachParam")) {
            if ("WatermarkTemplateId".equalsIgnoreCase(name)) {
                attachParam.setWatermarkTemplateId(getText());
                response.setAttachParam(attachParam);
            }
        } else if (in("Response", "AttachParam", "Watermark")) {
            MediaWatermark watermark = response.getAttachParam().getWatermark();
            ParserMediaInfoUtils.ParsingWatermark(watermark, name, getText());
        } else if (in("Response", "AttachParam", "Watermark", "Text")) {
            MediaWaterMarkText text = response.getAttachParam().getWatermark().getText();
            ParserMediaInfoUtils.ParsingWatermarkText(text, name, getText());
        } else if (in("Response", "AttachParam", "Watermark", "Image")) {
            MediaWaterMarkImage image = response.getAttachParam().getWatermark().getImage();
            ParserMediaInfoUtils.ParsingWatermarkImage(image, name, getText());
        }
    }

    public MediaWorkflowListResponse getResponse() {
        return response;
    }

    public void setResponse(MediaWorkflowListResponse response) {
        this.response = response;
    }
}
