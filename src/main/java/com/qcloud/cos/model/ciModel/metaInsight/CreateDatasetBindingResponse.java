package com.qcloud.cos.model.ciModel.metaInsight;

import com.qcloud.cos.model.CiServiceResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;


public class CreateDatasetBindingResponse extends CiServiceResult {

    /**
     *请求ID
     */
    private String requestId;

    /**
     *绑定信息
     */
    private Binding binding;

    public String getRequestId() { return requestId; }

    public void setRequestId(String requestId) { this.requestId = requestId; }

    public Binding getBinding() { return binding; }

    public void setBinding(Binding binding) { this.binding = binding; }

    
}
