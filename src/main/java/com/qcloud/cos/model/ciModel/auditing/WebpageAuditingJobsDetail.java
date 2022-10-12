package com.qcloud.cos.model.ciModel.auditing;


import java.util.ArrayList;
import java.util.List;

public class WebpageAuditingJobsDetail {
    private List<ResultsImageAuditingDetail> imageResults = new ArrayList<>();
    private List<ResultsTextAuditingDetail> textResults = new ArrayList<>();
    private String jobId;
    private String label;
    private String pageCount;
    private String state;
    private String suggestion;
    private String url;
    private String creationTime;
    private String code;
    private String message;
    private String dataId;
    private AuditingLabels labels;
    private String highlightHtml;
    private UserInfo userInfo = new UserInfo();
    private ListInfo listInfo = new ListInfo();

    public ListInfo getListInfo() {
        return listInfo;
    }

    public void setListInfo(ListInfo listInfo) {
        this.listInfo = listInfo;
    }

    public List<ResultsImageAuditingDetail> getImageResults() {
        return imageResults;
    }

    public void setImageResults(List<ResultsImageAuditingDetail> imageResults) {
        this.imageResults = imageResults;
    }

    public List<ResultsTextAuditingDetail> getTextResults() {
        return textResults;
    }

    public void setTextResults(List<ResultsTextAuditingDetail> textResults) {
        this.textResults = textResults;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
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

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }


    public String getHighlightHtml() {
        return highlightHtml;
    }

    public void setHighlightHtml(String highlightHtml) {
        this.highlightHtml = highlightHtml;
    }

    public AuditingLabels getLabels() {
        if (labels == null) {
            labels = new AuditingLabels();
        }
        return labels;
    }

    public void setLabels(AuditingLabels labels) {
        this.labels = labels;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WebpageAuditingJobsDetail{");
        sb.append("imageResults=").append(imageResults);
        sb.append(", textResults=").append(textResults);
        sb.append(", jobId='").append(jobId).append('\'');
        sb.append(", label='").append(label).append('\'');
        sb.append(", pageCount='").append(pageCount).append('\'');
        sb.append(", state='").append(state).append('\'');
        sb.append(", suggestion='").append(suggestion).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", creationTime='").append(creationTime).append('\'');
        sb.append(", code='").append(code).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append(", dataId='").append(dataId).append('\'');
        sb.append(", labels='").append(labels).append('\'');
        sb.append(", highlightHtml='").append(highlightHtml).append('\'');
        sb.append(", userInfo=").append(userInfo);
        sb.append(", listInfo=").append(listInfo);
        sb.append('}');
        return sb.toString();
    }
}
