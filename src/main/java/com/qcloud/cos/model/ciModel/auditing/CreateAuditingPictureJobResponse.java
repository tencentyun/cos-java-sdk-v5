package com.qcloud.cos.model.ciModel.auditing;


import com.qcloud.cos.model.CosServiceResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

@XStreamAlias("RecognitionResult")
public class CreateAuditingPictureJobResponse extends CosServiceResult {

    /**
     *图片标识，审核结果会返回原始内容，长度限制为512字节。
     */
    @XStreamAlias("DataId")
    private String dataId;

    /**
     *图片审核任务的 ID。
     */
    @XStreamAlias("JobId")
    private String jobId;

    /**
     *审核任务的状态，值为 Success（审核成功）。
     */
    @XStreamAlias("State")
    private String state;

    /**
     *存储在 COS 桶中的图片名称，创建任务使用 ObjectKey 时返回。
     */
    @XStreamAlias("Object")
    private String object;

    /**
     *图片文件的链接地址，创建任务使用 detect-url 时返回。
     */
    @XStreamAlias("Url")
    private String url;

    /**
     *图片是否被压缩处理，值为 0（未压缩），1（正常压缩）。
     */
    @XStreamAlias("CompressionResult")
    private Integer compressionResult;

    /**
     *该字段表示本次判定的审核结果，您可以根据该结果，进行后续的操作；建议您按照业务所需，对不同的审核结果进行相应处理。有效值：0（审核正常），1 （判定为违规敏感文件），2（疑似敏感，建议人工复核）。
     */
    @XStreamAlias("Result")
    private Integer result;

    /**
     *该字段用于返回检测结果中所对应的优先级最高的恶意标签，表示模型推荐的审核结果，建议您按照业务所需，对不同违规类型与建议值进行处理。返回值：Normal 表示正常，Porn 表示色情，Ads 表示广告，Quality 表示低质量，以及其他不安全或不适宜的类型。
     */
    @XStreamAlias("Label")
    private String label;

    /**
     *该字段为 Label 的子集，表示审核命中的具体审核类别。例如 Sexy，表示色情标签中的性感类别。
     */
    @XStreamAlias("Category")
    private String category;

    /**
     *该图命中的二级标签结果。
     */
    @XStreamAlias("SubLabel")
    private String subLabel;

    /**
     *该字段表示审核结果命中审核信息的置信度，取值范围：0（置信度最低）-100（置信度最高 ），越高代表该内容越有可能属于当前返回审核信息例如：色情 99，则表明该内容非常有可能属于色情内容
     */
    @XStreamAlias("Score")
    private Integer score;

    /**
     *该图里的文字内容（OCR），当审核策略开启文本内容检测时返回。
     */
    @XStreamAlias("Text")
    private String text;

    /**
     *禁止状态，0表示未禁止，1表示禁止。
     */
    @XStreamAlias("ForbidState")
    private Integer forbidState;

    /**
     *审核场景为色情性感的审核结果信息。
     */
    @XStreamAlias("PornInfo")
    private AuditingResultInfo pornInfo;

    /**
     *审核场景为广告引导的审核结果信息。
     */
    @XStreamAlias("AdsInfo")
    private AuditingResultInfo adsInfo;

    /**
     *审核场景为图片质量差不清晰的审核结果信息。
     */
    @XStreamAlias("QualityInfo")
    private AuditingResultInfo qualityInfo;

    /**
     *审核场景为政治敏感的审核结果信息。
     */
    @XStreamAlias("PoliticsInfo")
    private AuditingResultInfo politicsInfo;

    /**
     *审核场景为暴恐内容的审核结果信息。
     */
    @XStreamAlias("TerroristInfo")
    private AuditingResultInfo terroristInfo;

    public String getDataId() { return dataId; }

    public void setDataId(String dataId) { this.dataId = dataId; }

    public String getJobId() { return jobId; }

    public void setJobId(String jobId) { this.jobId = jobId; }

    public String getState() { return state; }

    public void setState(String state) { this.state = state; }

    public String getObject() { return object; }

    public void setObject(String object) { this.object = object; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    public Integer getCompressionResult() { return compressionResult; }

    public void setCompressionResult(Integer compressionResult) { this.compressionResult = compressionResult; }

    public Integer getResult() { return result; }

    public void setResult(Integer result) { this.result = result; }

    public String getLabel() { return label; }

    public void setLabel(String label) { this.label = label; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public String getSubLabel() { return subLabel; }

    public void setSubLabel(String subLabel) { this.subLabel = subLabel; }

    public Integer getScore() { return score; }

    public void setScore(Integer score) { this.score = score; }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    public Integer getForbidState() { return forbidState; }

    public void setForbidState(Integer forbidState) { this.forbidState = forbidState; }

    public AuditingResultInfo getPornInfo() { return pornInfo; }

    public void setPornInfo(AuditingResultInfo pornInfo) { this.pornInfo = pornInfo; }

    public AuditingResultInfo getAdsInfo() { return adsInfo; }

    public void setAdsInfo(AuditingResultInfo adsInfo) { this.adsInfo = adsInfo; }

    public AuditingResultInfo getQualityInfo() { return qualityInfo; }

    public void setQualityInfo(AuditingResultInfo qualityInfo) { this.qualityInfo = qualityInfo; }

    public AuditingResultInfo getPoliticsInfo() { return politicsInfo; }

    public void setPoliticsInfo(AuditingResultInfo politicsInfo) { this.politicsInfo = politicsInfo; }

    public AuditingResultInfo getTerroristInfo() { return terroristInfo; }

    public void setTerroristInfo(AuditingResultInfo terroristInfo) { this.terroristInfo = terroristInfo; }


    @XStreamAlias("AuditingResultInfo")
    public class AuditingResultInfo {
        /**
         *单个审核场景的错误码，0为成功，其他为失败。详情请参见 错误码。
         */
        @XStreamAlias("Code")
        private Integer code;

        /**
         *具体错误信息，如正常则为 OK。
         */
        @XStreamAlias("Msg")
        private String msg;

        /**
         *用于返回该审核场景的审核结果，返回值：0：正常。1：确认为当前场景的违规内容。2：疑似为当前场景的违规内容。
         */
        @XStreamAlias("HitFlag")
        private Integer hitFlag;

        /**
         *该字段表示审核结果命中审核信息的置信度，取值范围：0（置信度最低）-100（置信度最高 ），越高代表该内容越有可能属于当前返回审核信息例如：色情 99，则表明该内容非常有可能属于色情内容。
         */
        @XStreamAlias("Score")
        private Integer score;

        /**
         *该图的结果标签（为综合标签，可能为 SubLabel，可能为人物名字等）。
         */
        @XStreamAlias("Label")
        private String label;

        /**
         *该字段为 Label 的子集，表示审核命中的具体审核类别。例如 Sexy，表示色情标签中的性感类别。
         */
        @XStreamAlias("Category")
        private String category;

        /**
         *该图的二级标签结果。
         */
        @XStreamAlias("SubLabel")
        private String subLabel;

        /**
         *该字段表示 OCR 文本识别的详细检测结果，包括文本坐标信息、文本识别结果等信息，有相关违规内容时返回。
         */
        @XStreamImplicit(itemFieldName = "OcrResults")
        private List<OcrResults> ocrResults;

        /**
         *该字段用于返回基于风险库识别的结果。注意：未命中风险库中样本时，此字段不返回。
         */
        @XStreamImplicit(itemFieldName = "LibResults")
        private List<ImageLibResults> libResults;

        /**
         *该字段用于返回对象检测结果，例如人物名称等。
         */
        @XStreamImplicit(itemFieldName = "ObjectResults")
        private List<ObjectResults> objectResults;

        public Integer getCode() { return code; }

        public void setCode(Integer code) { this.code = code; }

        public String getMsg() { return msg; }

        public void setMsg(String msg) { this.msg = msg; }

        public Integer getHitFlag() { return hitFlag; }

        public void setHitFlag(Integer hitFlag) { this.hitFlag = hitFlag; }

        public Integer getScore() { return score; }

        public void setScore(Integer score) { this.score = score; }

        public String getLabel() { return label; }

        public void setLabel(String label) { this.label = label; }

        public String getCategory() { return category; }

        public void setCategory(String category) { this.category = category; }

        public String getSubLabel() { return subLabel; }

        public void setSubLabel(String subLabel) { this.subLabel = subLabel; }

        public List<OcrResults> getOcrResults() { return ocrResults; }

        public void setOcrResults(List<OcrResults> ocrResults) { this.ocrResults = ocrResults; }

        public List<ImageLibResults> getLibResults() { return libResults; }

        public void setLibResults(List<ImageLibResults> libResults) { this.libResults = libResults; }

        public List<ObjectResults> getObjectResults() { return objectResults; }

        public void setObjectResults(List<ObjectResults> objectResults) { this.objectResults = objectResults; }

        @XStreamAlias("ObjectResults")
        public class ObjectResults {
            /**
             *对象名称，例如人物名称。
             */
            @XStreamAlias("Name")
            private String name;

            /**
             *该对象命中的二级标签结果。
             */
            @XStreamAlias("SubLabel")
            private String subLabel;

            /**
             *该对象在图片中的位置信息。
             */
            @XStreamAlias("Location")
            private RotateLocation location;

            public String getName() { return name; }

            public void setName(String name) { this.name = name; }

            public String getSubLabel() { return subLabel; }

            public void setSubLabel(String subLabel) { this.subLabel = subLabel; }

            public RotateLocation getLocation() { return location; }

            public void setLocation(RotateLocation location) { this.location = location; }
        }
    }

    @XStreamAlias("ImageLibResults")
    public class ImageLibResults {
        /**
         *该字段表示命中的风险库中的图片样本 ID。
         */
        @XStreamAlias("ImageId")
        private String imageId;

        /**
         *该字段用于返回当前标签下的置信度，取值范围：0（置信度最低）-100（置信度最高），越高代表当前的图片越有可能命中库中的样本。例如：色情 99，则表明该数据非常有可能命中库中的色情样本。
         */
        @XStreamAlias("Score")
        private Integer score;

        public String getImageId() { return imageId; }

        public void setImageId(String imageId) { this.imageId = imageId; }

        public Integer getScore() { return score; }

        public void setScore(Integer score) { this.score = score; }

    }

    @XStreamAlias("OcrResults")
    public class OcrResults {
        /**
         *有敏感信息的 Ocr 文本内容。
         */
        @XStreamAlias("Text")
        private String text;

        /**
         *该段内容中的敏感文字。
         */
        @XStreamImplicit(itemFieldName = "Keywords")
        private List<String> keywords;

        /**
         *该段文字在图片中的位置信息。
         */
        @XStreamAlias("Location")
        private RotateLocation location;

        public String getText() { return text; }

        public void setText(String text) { this.text = text; }

        public List<String> getKeywords() { return keywords; }

        public void setKeywords(List<String> keywords) { this.keywords = keywords; }

        public RotateLocation getLocation() { return location; }

        public void setLocation(RotateLocation location) { this.location = location; }

    }

    @XStreamAlias("RotateLocation")
    public class RotateLocation {
        /**
         *该参数用于返回检测框左上角位置的横坐标（x）所在的像素位置，结合剩余参数可唯一确定检测框的大小和位置。
         */
        @XStreamAlias("X")
        private float x;

        /**
         *该参数用于返回检测框左上角位置的纵坐标（y）所在的像素位置，结合剩余参数可唯一确定检测框的大小和位置。
         */
        @XStreamAlias("Y")
        private float y;

        /**
         *该参数用于返回检测框的高度（由左上角出发在 y 轴向下延伸的长度），结合剩余参数可唯一确定检测框的大小和位置。
         */
        @XStreamAlias("Height")
        private float height;

        /**
         *该参数用于返回检测框的宽度（由左上角出发在 x 轴向右延伸的长度），结合剩余参数可唯一确定检测框的大小和位置。
         */
        @XStreamAlias("Width")
        private float width;

        /**
         *该参数用于返回检测框的旋转角度，该参数结合 X 和 Y 两个坐标参数可唯一确定检测框的具体位置；取值：0-360（角度制），方向为逆时针旋转。
         */
        @XStreamAlias("Rotate")
        private float rotate;

        public float getX() { return x; }

        public void setX(float x) { this.x = x; }

        public float getY() { return y; }

        public void setY(float y) { this.y = y; }

        public float getHeight() { return height; }

        public void setHeight(float height) { this.height = height; }

        public float getWidth() { return width; }

        public void setWidth(float width) { this.width = width; }

        public float getRotate() { return rotate; }

        public void setRotate(float rotate) { this.rotate = rotate; }

    }

}