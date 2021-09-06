package com.qcloud.cos.model.ciModel.auditing;

import com.qcloud.cos.internal.CIServiceRequest;

public class DocumentAuditingRequest extends CIServiceRequest {
    private AuditingInputObject input;
    private Conf conf;

    public AuditingInputObject getInput() {
        if (input == null) {
            input = new AuditingInputObject();
        }
        return input;
    }

    public void setInput(AuditingInputObject input) {
        this.input = input;
    }

    public Conf getConf() {
        if (conf == null) {
            conf = new Conf();
        }
        return conf;
    }

    public void setConf(Conf conf) {
        this.conf = conf;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DocumentAuditingRequest{");
        sb.append("input=").append(input);
        sb.append(", conf=").append(conf);
        sb.append('}');
        return sb.toString();
    }
}
