package com.qcloud.cos.model.ciModel.workflow;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Node")
public class MediaWorkflowNode {
    @XStreamAlias("Type")
    private String type;

    @XStreamAlias("Operation")
    private MediaOperation operation;

    @XStreamAlias("Input")
    private MediaWorkflowInput input;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MediaOperation getOperation() {
        if (operation == null) {
            operation = new MediaOperation();
        }
        return operation;
    }

    public void setOperation(MediaOperation operation) {

        this.operation = operation;
    }

    public MediaWorkflowInput getInput() {
        if (input == null) {
            input = new MediaWorkflowInput();
        }
        return input;
    }

    public void setInput(MediaWorkflowInput input) {
        this.input = input;
    }

    @Override
    public String toString() {
        return "MediaWorkflowNode{" +
                "type='" + type + '\'' +
                ", operation=" + operation +
                ", input=" + input +
                '}';
    }
}
