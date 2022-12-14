package com.qcloud.cos.model.ciModel.auditing;

import com.qcloud.cos.model.CosServiceResult;

/**
 * 图片审核响应实体 参数详情参考：https://cloud.tencent.com/document/product/460/37318
 */
public class ImageAuditingResponse extends CosServiceResult {
    private String jobId;
    private String object;
    private String compressionResult;
    private String result;
    private String label;
    private String category;
    private String subLabel;
    private String score;
    private String text;
    private String creationTime;
    private String code;
    private String message;
    private String state;
    private String dataId;
    private String url;
    private String forbidState;
    private PornInfo pornInfo;
    private TerroristInfo terroristInfo;
    private PoliticsInfo politicsInfo;
    private AdsInfo adsInfo;
    private TeenagerInfo teenagerInfo = new TeenagerInfo();
    private UserInfo userInfo = new UserInfo();
    private ListInfo listInfo = new ListInfo();

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getCompressionResult() {
        return compressionResult;
    }

    public void setCompressionResult(String compressionResult) {
        this.compressionResult = compressionResult;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubLabel() {
        return subLabel;
    }

    public void setSubLabel(String subLabel) {
        this.subLabel = subLabel;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public PornInfo getPornInfo() {
        if (pornInfo == null) {
            pornInfo = new PornInfo();
        }
        return pornInfo;
    }

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public void setPornInfo(PornInfo pornInfo) {
        this.pornInfo = pornInfo;
    }

    public TerroristInfo getTerroristInfo() {
        if (terroristInfo == null) {
            terroristInfo = new TerroristInfo();
        }
        return terroristInfo;
    }

    public void setTerroristInfo(TerroristInfo terroristInfo) {
        this.terroristInfo = terroristInfo;
    }

    public PoliticsInfo getPoliticsInfo() {
        if (politicsInfo == null) {
            politicsInfo = new PoliticsInfo();
        }
        return politicsInfo;
    }

    public void setPoliticsInfo(PoliticsInfo politicsInfo) {
        this.politicsInfo = politicsInfo;
    }

    public AdsInfo getAdsInfo() {
        if (adsInfo == null) {
            adsInfo = new AdsInfo();
        }
        return adsInfo;
    }

    public void setAdsInfo(AdsInfo adsInfo) {
        this.adsInfo = adsInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public TeenagerInfo getTeenagerInfo() {
        return teenagerInfo;
    }

    public void setTeenagerInfo(TeenagerInfo teenagerInfo) {
        this.teenagerInfo = teenagerInfo;
    }

    public ListInfo getListInfo() {
        return listInfo;
    }

    public void setListInfo(ListInfo listInfo) {
        this.listInfo = listInfo;
    }

    public String getForbidState() {
        return forbidState;
    }

    public void setForbidState(String forbidState) {
        this.forbidState = forbidState;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ImageAuditingResponse{");
        sb.append("jobId='").append(jobId).append('\'');
        sb.append(", object='").append(object).append('\'');
        sb.append(", compressionResult='").append(compressionResult).append('\'');
        sb.append(", result='").append(result).append('\'');
        sb.append(", label='").append(label).append('\'');
        sb.append(", category='").append(category).append('\'');
        sb.append(", subLabel='").append(subLabel).append('\'');
        sb.append(", score='").append(score).append('\'');
        sb.append(", text='").append(text).append('\'');
        sb.append(", creationTime='").append(creationTime).append('\'');
        sb.append(", code='").append(code).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append(", state='").append(state).append('\'');
        sb.append(", dataId='").append(dataId).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", forbidState='").append(forbidState).append('\'');
        sb.append(", pornInfo=").append(pornInfo);
        sb.append(", terroristInfo=").append(terroristInfo);
        sb.append(", politicsInfo=").append(politicsInfo);
        sb.append(", adsInfo=").append(adsInfo);
        sb.append(", teenagerInfo=").append(teenagerInfo);
        sb.append(", userInfo=").append(userInfo);
        sb.append(", listInfo=").append(listInfo);
        sb.append('}');
        return sb.toString();
    }
}
