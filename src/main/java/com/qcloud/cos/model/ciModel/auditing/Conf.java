package com.qcloud.cos.model.ciModel.auditing;


/**
 * 审核接口 操作规则参数实体类
 */
public class Conf {

    /**
     * 审核的场景类型，有效值：Porn（涉黄）、Ads（广告）等，可以传入多种类型，不同类型以,分隔，例如：Porn,Ads。，
     * 文本审核类型额外支持 Illegal（违法）、Abuse（谩骂）
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

    /**
     * 审核策略，不带审核策略时使用默认策略
     */
    private String bizType;

    /**
     * 审核内容开关, 0: 只审截图, 1: 审核截图和音频, 默认为0
     */
    private String detectContent;

    /**
     * 回调内容的结构，有效值：Simple（回调内容包含基本信息）、Detail（回调内容包含详细信息）。默认为 Simple。
     */
    private CallbackVersion callbackVersion;

    /**
     * 指定是否需要高亮展示网页内的违规文本，并返回高亮展示的 html 链接。取值为 true 和 false，默认为 false。
     */
    private String returnHighlightHtml;

    /**
     * 是否进行异步处理审核 0：同步返回结果  1：异步处理。 默认值为 0
     */
    private String async;

    private String callbackType;

    private Freeze freeze;

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getDetectContent() {
        return detectContent;
    }

    public void setDetectContent(String detectContent) {
        this.detectContent = detectContent;
    }

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

    public CallbackVersion getCallbackVersion() {
        return callbackVersion;
    }

    public void setCallbackVersion(CallbackVersion callbackVersion) {
        this.callbackVersion = callbackVersion;
    }


    public String getAsync() {
        return async;
    }

    public void setAsync(String async) {
        this.async = async;
    }

    public String getCallbackType() {
        return callbackType;
    }

    public void setCallbackType(String callbackType) {
        this.callbackType = callbackType;
    }

    public String getReturnHighlightHtml() {
        return returnHighlightHtml;
    }

    public void setReturnHighlightHtml(String returnHighlightHtml) {
        this.returnHighlightHtml = returnHighlightHtml;
    }

    public Freeze getFreeze() {
        if (freeze == null) {
            freeze = new Freeze();
        }
        return freeze;
    }

    public void setFreeze(Freeze freeze) {
        this.freeze = freeze;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Conf{");
        sb.append("detectType='").append(detectType).append('\'');
        sb.append(", snapshot=").append(snapshot);
        sb.append(", callback='").append(callback).append('\'');
        sb.append(", bizType='").append(bizType).append('\'');
        sb.append(", detectContent='").append(detectContent).append('\'');
        sb.append(", callbackVersion=").append(callbackVersion);
        sb.append(", returnHighlightHtml=").append(returnHighlightHtml);
        sb.append(", async='").append(async).append('\'');
        sb.append(", callbackType='").append(callbackType).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
