package com.qcloud.cos.model.ciModel.auditing;

import java.util.ArrayList;
import java.util.List;

public class AuditingJobsDetail {
    /**
     * 新创建任务的 ID
     */
    private String jobId;
    /**
     * 任务的状态，为 Submitted、Snapshoting、Success、Failed、Auditing 其中一个
     */
    private String state;
    /**
     * 任务的创建时间
     */
    private String creationTime;

    /**
     * 错误码，只有 State 为 Failed 时有意义
     */
    private String code;
    /**
     * 错误描述，只有 State 为 Failed 时有意义
     */
    private String message;

    /**
     * 任务对象
     */
    private String object;

    /**
     * 截图数量
     */
    private String snapshotCount;

    /**
     * 文本分片数量
     */
    private String sectionCount;

    /**
     * 供参考的识别结果，0表示确认正常，1表示确认敏感，2表示疑似敏感
     */
    private String result;

    /**
     * 错误描述，只有 State 为 Failed 时有意义
     */
    private PornInfo pornInfo;

    /**
     * 错误描述，只有 State 为 Failed 时有意义
     */
    private TerroristInfo terroristInfo;

    /**
     * 错误描述，只有 State 为 Failed 时有意义
     */
    private PoliticsInfo politicsInfo;

    /**
     * 错误描述，只有 State 为 Failed 时有意义
     */
    private AdsInfo adsInfo;

    private List<SnapshotInfo> snapshotList;

    /**
     * 具体文本分片的审核结果信息，只返回带有违规结果的分片
     */
    private List<SectionInfo> sectionList;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
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

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getSnapshotCount() {
        return snapshotCount;
    }

    public void setSnapshotCount(String snapshotCount) {
        this.snapshotCount = snapshotCount;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public PornInfo getPornInfo() {
        if (pornInfo == null) {
            pornInfo = new PornInfo();
        }
        return pornInfo;
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

    public List<SnapshotInfo> getSnapshotList() {
        if (snapshotList == null) {
            snapshotList = new ArrayList<>();
        }
        return snapshotList;
    }

    public void setSnapshotList(List<SnapshotInfo> snapshotList) {
        this.snapshotList = snapshotList;
    }

    public List<SectionInfo> getSectionList() {
        if (sectionList == null) {
            sectionList = new ArrayList<>();
        }
        return sectionList;
    }

    public void setSectionList(List<SectionInfo> sectionList) {
        this.sectionList = sectionList;
    }

    public String getSectionCount() {
        return sectionCount;
    }

    public void setSectionCount(String sectionCount) {
        this.sectionCount = sectionCount;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AuditingJobsDetail{");
        sb.append("jobId='").append(jobId).append('\'');
        sb.append(", state='").append(state).append('\'');
        sb.append(", creationTime='").append(creationTime).append('\'');
        sb.append(", code='").append(code).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append(", object='").append(object).append('\'');
        sb.append(", snapshotCount='").append(snapshotCount).append('\'');
        sb.append(", sectionCount='").append(sectionCount).append('\'');
        sb.append(", result='").append(result).append('\'');
        sb.append(", pornInfo=").append(pornInfo);
        sb.append(", terroristInfo=").append(terroristInfo);
        sb.append(", politicsInfo=").append(politicsInfo);
        sb.append(", adsInfo=").append(adsInfo);
        sb.append(", snapshotList=").append(snapshotList);
        sb.append(", sectionList=").append(sectionList);
        sb.append('}');
        return sb.toString();
    }
}
