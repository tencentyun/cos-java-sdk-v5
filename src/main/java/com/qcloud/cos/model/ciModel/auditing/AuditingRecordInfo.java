package com.qcloud.cos.model.ciModel.auditing;

public class AuditingRecordInfo {
    private String code;
    private String message;
    private AuditingCosOutput output;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AuditingCosOutput getOutput() {
        if (output == null) {
            output = new AuditingCosOutput();
        }
        return output;
    }

    public void setOutput(AuditingCosOutput output) {
        this.output = output;
    }
}
