package com.qcloud.cos.internal.cihandler;

import com.qcloud.cos.model.ciModel.workflow.MediaWorkflowListResponse;
import org.xml.sax.Attributes;


public class TriggerWorkflowListHandler extends CIAbstractHandler {
    public MediaWorkflowListResponse response = new MediaWorkflowListResponse();

    @Override
    protected void doStartElement(String uri, String name, String qName, Attributes attrs) {

    }

    @Override
    protected void doEndElement(String uri, String name, String qName) {
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
        }
    }

    public MediaWorkflowListResponse getResponse() {
        return response;
    }

    public void setResponse(MediaWorkflowListResponse response) {
        this.response = response;
    }
}
