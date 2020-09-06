package com.qcloud.cos.model.ciModel.workflow;

import java.util.HashMap;
import java.util.Map;

public class MediaTopology {
    private Map<String,MediaWorkflowDependency> mediaWorkflowDependency;
    private Map<String,MediaWorkflowNode> mediaWorkflowNodes;

    public Map<String,MediaWorkflowDependency> getMediaWorkflowDependency() {
        if (mediaWorkflowDependency==null){
            mediaWorkflowDependency = new HashMap<>();
        }
        return mediaWorkflowDependency;
    }

    public void setMediaWorkflowDependency(Map<String, MediaWorkflowDependency> mediaWorkflowDependency) {
        this.mediaWorkflowDependency = mediaWorkflowDependency;
    }

    public Map<String, MediaWorkflowNode> getMediaWorkflowNodes() {
        if (mediaWorkflowNodes == null){
            mediaWorkflowNodes = new HashMap<>();
        }
        return mediaWorkflowNodes;
    }

    public void setMediaWorkflowNodes(Map<String, MediaWorkflowNode> mediaWorkflowNodes) {
        this.mediaWorkflowNodes = mediaWorkflowNodes;
    }

    @Override
    public String toString() {
        return "MediaTopology{" +
                "mediaWorkflowDependency=" + mediaWorkflowDependency +
                ", mediaWorkflowNodes=" + mediaWorkflowNodes +
                '}';
    }
}
