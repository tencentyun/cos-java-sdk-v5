package com.qcloud.cos.model.ciModel.auditing;


/**
 * 审核接口 操作规则参数实体类
 */
public class Conf {

    /**
     * 审核类型，拥有 porn（涉黄识别）、terrorist（涉暴恐识别）、politics（涉政识别）、ads（广告识别）四种。用户可选择多种识别类型，
     * 例如 detectType=porn,ads 表示对图片进行涉黄及广告审核
     */
    private String detectType;

    /**
     * 截帧配置
     */
    private AuditingSnapshotObject snapshot;

    /**
     * 回调地址，以http://或者https://开头的地址
     */
    private String callback;

    public String getDetectType() {
        return detectType;
    }

    public void setDetectType(String detectType) {
        this.detectType = detectType;
    }

    public AuditingSnapshotObject getSnapshot() {
        if (snapshot == null) {
            snapshot = new AuditingSnapshotObject();
        }
        return snapshot;
    }

    public void setSnapshot(AuditingSnapshotObject snapshot) {
        this.snapshot = snapshot;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Conf{");
        sb.append("detectType='").append(detectType).append('\'');
        sb.append(", snapshot=").append(snapshot);
        sb.append(", callback='").append(callback).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
