package com.qcloud.cos.model.ciModel.job;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.xstream.annotations.XStreamAlias;

public class AigcMetadata {
    @XStreamAlias("Label")
    @JsonProperty("Label")
    private String label;

    @XStreamAlias("ContentProducer")
    @JsonProperty("ContentProducer")
    private String contentProducer;

    @XStreamAlias("ProduceID")
    @JsonProperty("ProduceID")
    private String produceId;

    @XStreamAlias("ReservedCode1")
    @JsonProperty("ReservedCode1")
    private String reservedCode1;

    @XStreamAlias("ContentPropagator")
    @JsonProperty("ContentPropagator")
    private String contentPropagator;

    @XStreamAlias("PropagateID")
    @JsonProperty("PropagateID")
    private String propagateId;

    @XStreamAlias("ReservedCode2")
    @JsonProperty("ReservedCode2")
    private String reservedCode2;

    public String getContentProducer() {
        return contentProducer;
    }

    public void setContentProducer(String contentProducer) {
        this.contentProducer = contentProducer;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getProduceId() {
        return produceId;
    }

    public void setProduceId(String produceId) {
        this.produceId = produceId;
    }

    public String getReservedCode1() {
        return reservedCode1;
    }

    public void setReservedCode1(String reservedCode1) {
        this.reservedCode1 = reservedCode1;
    }

    public String getContentPropagator() {
        return contentPropagator;
    }

    public void setContentPropagator(String contentPropagator) {
        this.contentPropagator = contentPropagator;
    }

    public String getPropagateId() {
        return propagateId;
    }

    public void setPropagateId(String propagateId) {
        this.propagateId = propagateId;
    }

    public String getReservedCode2() {
        return reservedCode2;
    }

    public void setReservedCode2(String reservedCode2) {
        this.reservedCode2 = reservedCode2;
    }

    @Override
    public String toString() {
        return "AigcMetadata{" +
                "label='" + label + '\'' +
                ", contentProducer='" + contentProducer + '\'' +
                ", produceId='" + produceId + '\'' +
                ", reservedCode1='" + reservedCode1 + '\'' +
                ", contentPropagator='" + contentPropagator + '\'' +
                ", propagateId='" + propagateId + '\'' +
                ", reservedCode2='" + reservedCode2 + '\'' +
                '}';
    }
}
