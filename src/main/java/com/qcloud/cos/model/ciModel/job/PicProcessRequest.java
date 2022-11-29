package com.qcloud.cos.model.ciModel.job;


public class PicProcessRequest extends MediaJobsRequest {

    private PicProcessOperation processOperation;

    public void setProcessOperation(PicProcessOperation processOperation) {
        this.processOperation = processOperation;
    }

    public PicProcessOperation getPicProcessOperation() {
        if (processOperation == null) {
            processOperation = new PicProcessOperation();
        }
        return processOperation;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PicProcessRequest{");
        sb.append("operation=").append(processOperation);
        sb.append('}');
        return sb.toString();
    }
}
