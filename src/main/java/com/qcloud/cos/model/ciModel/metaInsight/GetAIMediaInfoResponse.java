package com.qcloud.cos.model.ciModel.metaInsight;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qcloud.cos.model.CiServiceResult;

import java.util.List;
import java.util.Map;


public class GetAIMediaInfoResponse extends CiServiceResult {

    /**
     *媒资信息。
     */
    @JsonProperty(value = "MediaInfo")
    private MediaInfo mediaInfo;

    public MediaInfo getMediaInfo() { return mediaInfo; }

    public void setMediaInfo(MediaInfo mediaInfo) { this.mediaInfo = mediaInfo; }


    public static class MediaInfo {
        /**
         *被查询的媒资在相应系统中的地址。
         */
        @JsonProperty("URI")
        private String uRI;

        /**
         *COS 自定义标签。存储业务在 COS object 上的自定义标签键值对信息，可用于查询时作为筛选条件。
         */
        private Map<String, String> cosTagging;

        /**
         *文件自定义标签列表。存储业务自定义的键名、键值对信息，可用于查询时作为筛选条件。
         */
        private Map<String, String> customLabels;

        /**
         *COS 自定义头部。存储业务在 COS object 上的键名、键值对信息，可用于查询时作为筛选条件。
         */
        private Map<String, String> cosUserMeta;

        /**
         *媒资修改时间。
         */
        private String modifiedTime;

        /**
         *文件信息。
         */
        private FileInfo fileInfo;

        public String getURI() { return uRI; }

        public void setURI(String uRI) { this.uRI = uRI; }

        public Map<String, String> getCosTagging() { return cosTagging; }

        public void setCosTagging(Map<String, String> cosTagging) { this.cosTagging = cosTagging; }

        public Map<String, String> getCustomLabels() { return customLabels; }

        public void setCustomLabels(Map<String, String> customLabels) { this.customLabels = customLabels; }

        public Map<String, String> getCosUserMeta() { return cosUserMeta; }

        public void setCosUserMeta(Map<String, String> cosUserMeta) { this.cosUserMeta = cosUserMeta; }

        public String getModifiedTime() { return modifiedTime; }

        public void setModifiedTime(String modifiedTime) { this.modifiedTime = modifiedTime; }

        public FileInfo getFileInfo() { return fileInfo; }

        public void setFileInfo(FileInfo fileInfo) { this.fileInfo = fileInfo; }

    }

    public static class FileInfo {
        /**
         *文件基础信息。
         */
        private FileBasicInfo fileBasicInfo;

        /**
         *AI 分析详细信息。
         */
        @JsonProperty("AiData")
        private AiData aiData;

        public FileBasicInfo getFileBasicInfo() { return fileBasicInfo; }

        public void setFileBasicInfo(FileBasicInfo fileBasicInfo) { this.fileBasicInfo = fileBasicInfo; }

        public AiData getAiData() { return aiData; }

        public void setAiData(AiData aiData) { this.aiData = aiData; }

    }

    public static class FileBasicInfo {
        /**
         *文件名。适用范围：视频、图片。
         */
        private String fileName;

        /**
         *文件大小，单位为字节。适用范围：视频、图片。
         */
        private Long fileSize;

        /**
         *文件 COS 地址。适用范围：视频、图片。
         */
        private String fileUrl;

        /**
         *文件存储区域。适用范围：视频、图片。
         */
        private String region;

        /**
         *文件修改时间。适用范围：视频、图片。
         */
        private String modifiedTime;

        /**
         *视频封装格式名称，例如 mov,mp4,m4a,3gp,3g2,mj2。适用范围：仅视频。
         */
        private String formatName;

        /**
         *视频时长，单位为秒。适用范围：仅视频。
         */
        private Double duration;

        /**
         *视频码率，单位为 kbps。适用范围：仅视频。
         */
        private Double bitrate;

        /**
         *视频宽度，单位为像素。适用范围：仅视频。
         */
        private Integer width;

        /**
         *视频高度，单位为像素。适用范围：仅视频。
         */
        private Integer height;

        public String getFileName() { return fileName; }

        public void setFileName(String fileName) { this.fileName = fileName; }

        public Long getFileSize() { return fileSize; }

        public void setFileSize(Long fileSize) { this.fileSize = fileSize; }

        public String getFileUrl() { return fileUrl; }

        public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }

        public String getRegion() { return region; }

        public void setRegion(String region) { this.region = region; }

        public String getModifiedTime() { return modifiedTime; }

        public void setModifiedTime(String modifiedTime) { this.modifiedTime = modifiedTime; }

        public String getFormatName() { return formatName; }

        public void setFormatName(String formatName) { this.formatName = formatName; }

        public Double getDuration() { return duration; }

        public void setDuration(Double duration) { this.duration = duration; }

        public Double getBitrate() { return bitrate; }

        public void setBitrate(Double bitrate) { this.bitrate = bitrate; }

        public Integer getWidth() { return width; }

        public void setWidth(Integer width) { this.width = width; }

        public Integer getHeight() { return height; }

        public void setHeight(Integer height) { this.height = height; }

    }

    public static class AiData {
        /**
         *AI 标签信息列表。适用范围：视频、图片。
         */
        private List<AiLabelInfo> aiLabelInfo;

        /**
         *语音识别信息列表。适用范围：仅视频。
         */
        private List<AsrInfo> asrInfo;

        /**
         *文字识别信息列表。适用范围：视频、图片。
         */
        private List<OcrInfo> ocrInfo;

        /**
         *AI 粗分类信息。适用范围：仅视频。
         */
        private AiRoughData aiRoughData;

        /**
         *人脸识别信息列表，按人物聚合，每个元素对应一个匹配到的人物。适用范围：视频、图片。
         */
        private List<FaceInfo> faceInfo;

        public List<AiLabelInfo> getAiLabelInfo() { return aiLabelInfo; }

        public void setAiLabelInfo(List<AiLabelInfo> aiLabelInfo) { this.aiLabelInfo = aiLabelInfo; }

        public List<AsrInfo> getAsrInfo() { return asrInfo; }

        public void setAsrInfo(List<AsrInfo> asrInfo) { this.asrInfo = asrInfo; }

        public List<OcrInfo> getOcrInfo() { return ocrInfo; }

        public void setOcrInfo(List<OcrInfo> ocrInfo) { this.ocrInfo = ocrInfo; }

        public AiRoughData getAiRoughData() { return aiRoughData; }

        public void setAiRoughData(AiRoughData aiRoughData) { this.aiRoughData = aiRoughData; }

        public List<FaceInfo> getFaceInfo() { return faceInfo; }

        public void setFaceInfo(List<FaceInfo> faceInfo) { this.faceInfo = faceInfo; }

    }

    public static class AiLabelInfo {
        /**
         *片段起始时间，单位为秒。适用范围：仅视频。
         */
        private Double from;

        /**
         *片段结束时间，单位为秒。适用范围：仅视频。
         */
        private Double to;

        /**
         *片段时间戳，单位为秒。适用范围：仅视频。
         */
        private Double timestamp;

        /**
         *标签详情列表。适用范围：视频、图片。
         */
        private List<LabelDetail> labelDetail;

        public Double getFrom() { return from; }

        public void setFrom(Double from) { this.from = from; }

        public Double getTo() { return to; }

        public void setTo(Double to) { this.to = to; }

        public Double getTimestamp() { return timestamp; }

        public void setTimestamp(Double timestamp) { this.timestamp = timestamp; }

        public List<LabelDetail> getLabelDetail() { return labelDetail; }

        public void setLabelDetail(List<LabelDetail> labelDetail) { this.labelDetail = labelDetail; }

    }

    public static class LabelDetail {
        /**
         *标签信息，key 为标签名称（如 Type、Category、Name），value 为对应标签值。
         */
        private Map<String, String> labelInfos;

        /**
         *置信度，例如 high、medium。
         */
        private String confidence;

        public Map<String, String> getLabelInfos() { return labelInfos; }

        public void setLabelInfos(Map<String, String> labelInfos) { this.labelInfos = labelInfos; }

        public String getConfidence() { return confidence; }

        public void setConfidence(String confidence) { this.confidence = confidence; }

    }

    public static class AsrInfo {
        /**
         *片段起始时间，单位为秒。
         */
        private Double from;

        /**
         *片段结束时间，单位为秒。
         */
        private Double to;

        /**
         *片段 ID。
         */
        private String clipId;

        /**
         *语音识别文本内容。
         */
        private String content;

        /**
         *片段时间戳，单位为秒。
         */
        private Double timestamp;

        public Double getFrom() { return from; }

        public void setFrom(Double from) { this.from = from; }

        public Double getTo() { return to; }

        public void setTo(Double to) { this.to = to; }

        public String getClipId() { return clipId; }

        public void setClipId(String clipId) { this.clipId = clipId; }

        public String getContent() { return content; }

        public void setContent(String content) { this.content = content; }

        public Double getTimestamp() { return timestamp; }

        public void setTimestamp(Double timestamp) { this.timestamp = timestamp; }

    }

    public static class OcrInfo {
        /**
         *片段起始时间，单位为秒。适用范围：仅视频。
         */
        private Double from;

        /**
         *片段结束时间，单位为秒。适用范围：仅视频。
         */
        private Double to;

        /**
         *片段 ID。适用范围：仅视频。
         */
        private String clipId;

        /**
         *文字识别文本内容。适用范围：视频、图片。
         */
        private String content;

        /**
         *识别文本内容的坐标位置。适用范围：视频、图片。
         */
        private BoxPosition boxPosition;

        /**
         *相似分数。适用范围：视频、图片。
         */
        private Double score;

        /**
         *片段时间戳，单位为秒。适用范围：仅视频。
         */
        private Double timestamp;

        public Double getFrom() { return from; }

        public void setFrom(Double from) { this.from = from; }

        public Double getTo() { return to; }

        public void setTo(Double to) { this.to = to; }

        public String getClipId() { return clipId; }

        public void setClipId(String clipId) { this.clipId = clipId; }

        public String getContent() { return content; }

        public void setContent(String content) { this.content = content; }

        public BoxPosition getBoxPosition() { return boxPosition; }

        public void setBoxPosition(BoxPosition boxPosition) { this.boxPosition = boxPosition; }

        public Double getScore() { return score; }

        public void setScore(Double score) { this.score = score; }

        public Double getTimestamp() { return timestamp; }

        public void setTimestamp(Double timestamp) { this.timestamp = timestamp; }

    }

    public static class AiRoughData {
        /**
         *AI 粗分类类别，例如 生活。
         */
        private String aiCategory;

        /**
         *视频整体描述。
         */
        private String description;

        public String getAiCategory() { return aiCategory; }

        public void setAiCategory(String aiCategory) { this.aiCategory = aiCategory; }

        public String getDescription() { return description; }

        public void setDescription(String description) { this.description = description; }

    }

    public static class FaceInfo {
        /**
         *人脸 ID，与人脸库中的 FaceId 一致，可作为人脸定位媒资片段等接口的入参。
         */
        private String faceId;

        /**
         *人脸匹配得分，范围为 [0, 100]。
         */
        private Double score;

        /**
         *实体/人物名称。
         */
        private String labelName;

        /**
         *人物类型，可选值：celebrity（名人）、sensitive（敏感人物）、politician（政治人物）、custom（自定义人物）、unknown（未知）。
         */
        private String category;

        /**
         *人物片段聚类信息。适用范围：仅视频。
         */
        private List<OccurrencesInfo> occurrencesInfos;

        /**
         *人脸单帧详细信息列表。适用范围：仅图片。
         */
        private List<TrackData> trackData;

        public String getFaceId() { return faceId; }

        public void setFaceId(String faceId) { this.faceId = faceId; }

        public Double getScore() { return score; }

        public void setScore(Double score) { this.score = score; }

        public String getLabelName() { return labelName; }

        public void setLabelName(String labelName) { this.labelName = labelName; }

        public String getCategory() { return category; }

        public void setCategory(String category) { this.category = category; }

        public List<OccurrencesInfo> getOccurrencesInfos() { return occurrencesInfos; }

        public void setOccurrencesInfos(List<OccurrencesInfo> occurrencesInfos) { this.occurrencesInfos = occurrencesInfos; }

        public List<TrackData> getTrackData() { return trackData; }

        public void setTrackData(List<TrackData> trackData) { this.trackData = trackData; }

    }

    public static class OccurrencesInfo {
        /**
         *片段起始时间，单位为秒。
         */
        private Double from;

        /**
         *片段结束时间，单位为秒。
         */
        private Double to;

        /**
         *人脸单帧详细信息列表。
         */
        private List<TrackData> trackData;

        public Double getFrom() { return from; }

        public void setFrom(Double from) { this.from = from; }

        public Double getTo() { return to; }

        public void setTo(Double to) { this.to = to; }

        public List<TrackData> getTrackData() { return trackData; }

        public void setTrackData(List<TrackData> trackData) { this.trackData = trackData; }

    }

    public static class TrackData {
        /**
         *人脸出现时间戳，单位为秒。适用范围：仅视频。
         */
        private Double timestamp;

        /**
         *人脸坐标框。适用范围：视频、图片。
         */
        private BoxPosition boxPosition;

        public Double getTimestamp() { return timestamp; }

        public void setTimestamp(Double timestamp) { this.timestamp = timestamp; }

        public BoxPosition getBoxPosition() { return boxPosition; }

        public void setBoxPosition(BoxPosition boxPosition) { this.boxPosition = boxPosition; }

    }

    public static class BoxPosition {
        /**
         *坐标框左上角横坐标，单位为像素。
         */
        private Integer left;

        /**
         *坐标框左上角纵坐标，单位为像素。
         */
        private Integer top;

        /**
         *坐标框宽度，单位为像素。
         */
        private Integer width;

        /**
         *坐标框高度，单位为像素。
         */
        private Integer height;

        public Integer getLeft() { return left; }

        public void setLeft(Integer left) { this.left = left; }

        public Integer getTop() { return top; }

        public void setTop(Integer top) { this.top = top; }

        public Integer getWidth() { return width; }

        public void setWidth(Integer width) { this.width = width; }

        public Integer getHeight() { return height; }

        public void setHeight(Integer height) { this.height = height; }

    }

}
