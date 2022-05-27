package com.qcloud.cos.model.ciModel.workflow;

import com.qcloud.cos.internal.CIServiceRequest;
import com.qcloud.cos.model.CiServiceResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 工作流请求实体类 请见：https://cloud.tencent.com/document/product/460/45947
 */
public class MediaWorkflowListResponse extends CiServiceResult implements Serializable {

    /**
     * 请求id
     */
    private String requestId;
    /**
     * 工作流总数
     */
    private String totalCount;
    /**
     * 当前页数，同请求中的 pageNumber
     */
    private String pageNumber;
    /**
     * 每页个数，同请求中的 pageSize
     */
    private String pageSize;
    /**
     * 实例 ID
     */
    private String instanceId;
    /**
     * 工作流数组
     */
    private List<MediaWorkflowObject> mediaWorkflowList;


    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public List<MediaWorkflowObject> getMediaWorkflowList() {
        if (mediaWorkflowList == null) {
            mediaWorkflowList = new ArrayList<>();
        }
        return mediaWorkflowList;
    }

    public void setMediaWorkflowList(List<MediaWorkflowObject> mediaWorkflowList) {
        this.mediaWorkflowList = mediaWorkflowList;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MediaWorkflowListResponse{");
        sb.append("requestId='").append(requestId).append('\'');
        sb.append(", totalCount='").append(totalCount).append('\'');
        sb.append(", pageNumber='").append(pageNumber).append('\'');
        sb.append(", pageSize='").append(pageSize).append('\'');
        sb.append(", instanceId='").append(instanceId).append('\'');
        sb.append(", mediaWorkflowList=").append(mediaWorkflowList);
        sb.append('}');
        return sb.toString();
    }
}
