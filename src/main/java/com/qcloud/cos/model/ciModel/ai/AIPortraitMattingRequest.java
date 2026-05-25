package com.qcloud.cos.model.ciModel.ai;


import com.qcloud.cos.internal.CosServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("Request")
public class AIPortraitMattingRequest extends CosServiceRequest {

    /**
     * 存储桶名称，Bucket 的命名规则为 BucketName-APPID ，此处填写的存储桶名称必须为此格式
     */
    private String bucketName;

    /**
     * 对象文件名，例如：folder/document.jpg。
     */
    @XStreamOmitField
    private String objectKey;

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    /**
     * 数据万象处理能力，人像抠图固定为 AIPortraitMatting。;是否必传：是
     */
    @XStreamOmitField
    private String ciProcess = "AIPortraitMatting";

    /**
     * 您可以通过填写 detect-url 处理任意公网可访问的图片链接。不填写 detect-url 时，后台会默认处理 ObjectKey ，填写了 detect-url 时，后台会处理 detect-url 链接，无需再填写 ObjectKey。 detect-url 示例：http://www.example.com/abc.jpg，需要进行 UrlEncode，处理后为http%25253A%25252F%25252Fwww.example.com%25252Fabc.jpg。;是否必传：否
     */
    @XStreamOmitField
    private String detectUrl;

    /**
     * 抠图主体居中显示；值为1时居中显示，值为0不做处理，默认为0;是否必传：否
     */
    @XStreamOmitField
    private Integer centerLayout;

    /**
     * 将处理后的图片四边进行留白，形式为 padding-layout=x，左右两边各进行 dx 像素的留白，上下两边各进行 dy 像素的留白，例如：padding-layout=20x10默认不进行留白操作，dx、dy最大值为1000像素。;是否必传：否
     */
    @XStreamOmitField
    private String paddingLayout;

    public String getCiProcess() {
        return ciProcess;
    }

    public void setCiProcess(String ciProcess) {
        this.ciProcess = ciProcess;
    }

    public String getDetectUrl() {
        return detectUrl;
    }

    public void setDetectUrl(String detectUrl) {
        this.detectUrl = detectUrl;
    }

    public Integer getCenterLayout() {
        return centerLayout;
    }

    public void setCenterLayout(Integer centerLayout) {
        this.centerLayout = centerLayout;
    }

    public String getPaddingLayout() {
        return paddingLayout;
    }

    public void setPaddingLayout(String paddingLayout) {
        this.paddingLayout = paddingLayout;
    }


}
