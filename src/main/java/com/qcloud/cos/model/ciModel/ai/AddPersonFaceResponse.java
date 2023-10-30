package com.qcloud.cos.model.ciModel.ai;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author markjrzhang
 * @date 2023/10/27 16:14
 */
@XStreamAlias("Response")
public class AddPersonFaceResponse {
    /**
     * 加入成功的人脸数量示例值：1;是 否必传：否
     */
    @XStreamAlias("SucFaceNum")
    private Integer sucFaceNum;

    /**
     * 加入成功的人脸ID列表
     * 示例值：["2875186538564559728"];是否必传：否
     */
    @XStreamAlias("SucFaceId")
    private String sucFaceId;

    /**
     * 每张人脸图片添加结果，-1101 代表未检测到人脸，-1102 代表图片解码失败，
     * -1601代表不符合图片质量控制要求, -1604 代表人脸相似度没有超过FaceMatchThreshold。
     * 其他非 0 值代表算法服务异常。
     * 示例值：[0];是否必传：否
     */
    @XStreamAlias("RetCode")
    private Integer retCode;

    /**
     * 加入成功的人脸索引。索引顺序和入参中 Images 或 Urls 的顺序一致。
     * 例如， Urls 中 有 3 个 url，第二个 url 失败，则 SucIndexes 值为 [0,2] 。
     * 示例值：[0];是否必传：否
     */
    @XStreamAlias("SucIndexes")
    private Integer sucIndexes;

    /**
     * 加入成功的人脸框位置。顺序和入参中 objectkey 的顺序一致。;是否必传：否
     */
    @XStreamAlias("SucFaceRect")
    private SucFaceRects sucFaceRect;

    /**
     * 唯一请求 ID，每次请求都会返回。定位问题时需要提供该次请求的 RequestId。;是否必传：否
     */
    @XStreamAlias("RequestId")
    private String requestId;

    public Integer getSucFaceNum() {
        return sucFaceNum;
    }

    public void setSucFaceNum(Integer sucFaceNum) {
        this.sucFaceNum = sucFaceNum;
    }

    public Integer getRetCode() {
        return retCode;
    }

    public void setRetCode(Integer retCode) {
        this.retCode = retCode;
    }

    public Integer getSucIndexes() {
        return sucIndexes;
    }

    public void setSucIndexes(Integer sucIndexes) {
        this.sucIndexes = sucIndexes;
    }

    public SucFaceRects getSucFaceRect() {
        return sucFaceRect;
    }

    public void setSucFaceRect(SucFaceRects sucFaceRect) {
        this.sucFaceRect = sucFaceRect;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getSucFaceId() {
        return sucFaceId;
    }

    public void setSucFaceId(String sucFaceId) {
        this.sucFaceId = sucFaceId;
    }
}
