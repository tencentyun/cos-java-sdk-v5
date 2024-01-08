package com.qcloud.cos.model.ciModel.auditing;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

public class AuditingJobsDetail {
    /**
     * 新创建任务的 ID
     */
    @XStreamAlias("JobId")
    private String jobId;

    /**
     * 任务的状态，为 Submitted、Snapshoting、Success、Failed、Auditing 其中一个
     */
    @XStreamAlias("State")
    private String state;

    /**
     * 任务的创建时间
     */
    @XStreamAlias("CreationTime")
    private String creationTime;

    /**
     * 错误码，只有 State 为 Failed 时有意义
     */
    @XStreamAlias("Code")
    private String code;

    /**
     * 错误描述，只有 State 为 Failed 时有意义
     */
    @XStreamAlias("Message")
    private String message;

    /**
     * 任务对象
     */
    @XStreamAlias("Object")
    private String object;

    /**
     * 任务内容
     */
    @XStreamAlias("Content")
    private String content;

    /**
     * 截图数量
     */
    @XStreamAlias("SnapshotCount")
    private String snapshotCount;

    /**
     * 文本分片数量
     */
    @XStreamAlias("SectionCount")
    private String sectionCount;

    /**
     * 供参考的识别结果，0表示确认正常，1表示确认敏感，2表示疑似敏感
     */
    @XStreamAlias("Result")
    private String result;

    /**
     * 审核文本
     */
    @XStreamAlias("AudioText")
    private String audioText;

    /**
     * 内容url地址
     */
    @XStreamAlias("Url")
    private String url;

    /**
     * 数据id
     */
    @XStreamAlias("DataId")
    private String dataId;

    /**
     * 标签
     */
    @XStreamAlias("Label")
    private String label;

    /**
     * 二级标签
     */
    @XStreamAlias("SubLabel")
    private String subLabel;

    /**
     * 二级标签
     */
    @XStreamAlias("ForbidState")
    private String forbidState;

    /**
     * 审核结果 鉴黄信息
     */
    @XStreamAlias("PornInfo")
    private PornInfo pornInfo;

    /**
     * 审核结果 暴恐信息
     */
    @XStreamAlias("TerrorismInfo")
    private TerroristInfo terroristInfo;

    /**
     * 审核结果 政治敏感信息
     */
    @XStreamAlias("PoliticsInfo")
    private PoliticsInfo politicsInfo;

    /**
     * 审核结果 广告信息
     */
    @XStreamAlias("AdsInfo")
    private AdsInfo adsInfo;

    /**
     * 审核结果 谩骂信息
     */
    @XStreamAlias("AbuseInfo")
    private AbuseInfo abuseInfo;

    /**
     * 审核结果 违法信息
     */
    @XStreamAlias("IllegalInfo")
    private IllegalInfo illegalInfo;

    /**
     * 审核结果 青少年信息
     */
    @XStreamAlias("TeenagerInfo")
    private TeenagerInfo teenagerInfo;

    @XStreamAlias("MeaninglessInfo")
    private MeaninglessInfo meaninglessInfo;

    @XStreamImplicit(itemFieldName = "SnapshotInfo")
    private List<SnapshotInfo> snapshotList;

    @XStreamImplicit(itemFieldName = "AudioSectionInfo")
    private List<AudioSectionInfo> audioSectionList = new ArrayList<>();

    /**
     * 具体文本分片的审核结果信息，只返回带有违规结果的分片
     */
    @XStreamImplicit(itemFieldName = "Section")
    private List<SectionInfo> sectionList;

    @XStreamAlias("AudioSection")
    private SectionInfo audioSection;

    /**
     * 用户自定义信息
     */
    @XStreamAlias("UserInfo")
    private UserInfo userInfo = new UserInfo();

    /**
     * 黑白名单信息
     */
    @XStreamAlias("ListInfo")
    private ListInfo listInfo = new ListInfo();

    @XStreamAlias("Type")
    private String type;

    @XStreamAlias("MaskInfo")
    private MaskInfo maskInfo;

    public MeaninglessInfo getMeaninglessInfo() {
        if (meaninglessInfo == null) {
            meaninglessInfo = new MeaninglessInfo();
        }
        return meaninglessInfo;
    }

    public void setMeaninglessInfo(MeaninglessInfo meaninglessInfo) {
        this.meaninglessInfo = meaninglessInfo;
    }

    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public AbuseInfo getAbuseInfo() {
        if (abuseInfo == null) {
            abuseInfo = new AbuseInfo();
        }
        return abuseInfo;
    }

    public void setAbuseInfo(AbuseInfo abuseInfo) {
        this.abuseInfo = abuseInfo;
    }

    public IllegalInfo getIllegalInfo() {
        if (illegalInfo == null) {
            illegalInfo = new IllegalInfo();
        }
        return illegalInfo;
    }

    public void setIllegalInfo(IllegalInfo illegalInfo) {
        this.illegalInfo = illegalInfo;
    }

    public String getAudioText() {
        return audioText;
    }

    public void setAudioText(String audioText) {
        this.audioText = audioText;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public TeenagerInfo getTeenagerInfo() {
        if (teenagerInfo == null) {
            teenagerInfo = new TeenagerInfo();
        }
        return teenagerInfo;
    }

    public void setTeenagerInfo(TeenagerInfo teenagerInfo) {
        this.teenagerInfo = teenagerInfo;
    }

    public List<AudioSectionInfo> getAudioSectionList() {
        return audioSectionList;
    }

    public void setAudioSectionList(List<AudioSectionInfo> audioSectionList) {
        this.audioSectionList = audioSectionList;
    }

    public SectionInfo getAudioSection() {
        if (audioSection == null) {
            audioSection = new AudioSectionInfo();
        }
        return audioSection;
    }

    public void setAudioSection(SectionInfo audioSection) {
        this.audioSection = audioSection;
    }

    public ListInfo getListInfo() {
        return listInfo;
    }

    public void setListInfo(ListInfo listInfo) {
        this.listInfo = listInfo;
    }

    public String getSubLabel() {
        return subLabel;
    }

    public void setSubLabel(String subLabel) {
        this.subLabel = subLabel;
    }

    public String getForbidState() {
        return forbidState;
    }

    public void setForbidState(String forbidState) {
        this.forbidState = forbidState;
    }

    public MaskInfo getMaskInfo() {
        if (maskInfo == null) {
            maskInfo = new MaskInfo();
        }
        return maskInfo;
    }

    public void setMaskInfo(MaskInfo maskInfo) {
        this.maskInfo = maskInfo;
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
        sb.append(", content='").append(content).append('\'');
        sb.append(", snapshotCount='").append(snapshotCount).append('\'');
        sb.append(", sectionCount='").append(sectionCount).append('\'');
        sb.append(", result='").append(result).append('\'');
        sb.append(", audioText='").append(audioText).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", dataId='").append(dataId).append('\'');
        sb.append(", label='").append(label).append('\'');
        sb.append(", subLabel='").append(subLabel).append('\'');
        sb.append(", forbidState='").append(forbidState).append('\'');
        sb.append(", pornInfo=").append(pornInfo);
        sb.append(", terroristInfo=").append(terroristInfo);
        sb.append(", politicsInfo=").append(politicsInfo);
        sb.append(", adsInfo=").append(adsInfo);
        sb.append(", abuseInfo=").append(abuseInfo);
        sb.append(", illegalInfo=").append(illegalInfo);
        sb.append(", teenagerInfo=").append(teenagerInfo);
        sb.append(", snapshotList=").append(snapshotList);
        sb.append(", audioSectionList=").append(audioSectionList);
        sb.append(", sectionList=").append(sectionList);
        sb.append(", audioSection=").append(audioSection);
        sb.append(", userInfo=").append(userInfo);
        sb.append(", listInfo=").append(listInfo);
        sb.append('}');
        return sb.toString();
    }
}
