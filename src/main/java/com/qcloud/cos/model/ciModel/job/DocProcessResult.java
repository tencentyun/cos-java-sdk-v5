package com.qcloud.cos.model.ciModel.job;

import java.util.ArrayList;
import java.util.List;

/**
 * 文档处理结果实体类
 */
public class DocProcessResult {

    /**
     * 文档产物信息列表
     */
    private List<DocProcessPageInfo> docProcessPageInfoList;

    /**
     * 预览任务产物的总数
     */
    private String totalPageCount;

    /**
     * 预览任务产物的成功数
     */
    private String succPageCount;

    /**
     * 预览任务产物的失败数
     */
    private String failPageCount;

    /**
     * 预览产物目标格式
     */
    private String tgtType;

    /**
     * 预览任务的 Sheet 总数（源文件为 Excel 特有参数）
     */
    private String totalSheetCount;

    private WatermarkInfo watermarkInfo;


    public List<DocProcessPageInfo> getDocProcessPageInfoList() {
        if (docProcessPageInfoList == null) {
            docProcessPageInfoList = new ArrayList<>();
        }
        return docProcessPageInfoList;
    }

    public void setDocProcessPageInfoList(List<DocProcessPageInfo> docProcessPageInfoList) {
        this.docProcessPageInfoList = docProcessPageInfoList;
    }

    public String getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(String totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public String getSuccPageCount() {
        return succPageCount;
    }

    public void setSuccPageCount(String succPageCount) {
        this.succPageCount = succPageCount;
    }

    public String getFailPageCount() {
        return failPageCount;
    }

    public void setFailPageCount(String failPageCount) {
        this.failPageCount = failPageCount;
    }

    public String getTgtType() {
        return tgtType;
    }

    public void setTgtType(String tgtType) {
        this.tgtType = tgtType;
    }

    public String getTotalSheetCount() {
        return totalSheetCount;
    }

    public void setTotalSheetCount(String totalSheetCount) {
        this.totalSheetCount = totalSheetCount;
    }

    public WatermarkInfo getWatermarkInfo() {
        if (watermarkInfo == null) {
            watermarkInfo = new WatermarkInfo();
        }
        return watermarkInfo;
    }

    public void setWatermarkInfo(WatermarkInfo watermarkInfo) {
        this.watermarkInfo = watermarkInfo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DocProcessResult{");
        sb.append("docProcessPageInfoList=").append(docProcessPageInfoList);
        sb.append(", totalPageCount='").append(totalPageCount).append('\'');
        sb.append(", succPageCount='").append(succPageCount).append('\'');
        sb.append(", failPageCount='").append(failPageCount).append('\'');
        sb.append(", tgtType='").append(tgtType).append('\'');
        sb.append(", totalSheetCount='").append(totalSheetCount).append('\'');
        sb.append(", watermarkInfo=").append(watermarkInfo);
        sb.append('}');
        return sb.toString();
    }
}
