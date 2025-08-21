package com.qcloud.cos.model.ciModel.workflow;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.LinkedList;

@XStreamAlias("ResultInfo")
public class MediaTaskResultInfo {
    @XStreamAlias("ObjectCount")
    private String objectCount;

    @XStreamAlias("SpriteObjectCount")
    private String spriteObjectCount;

    @XStreamImplicit(itemFieldName="ObjectInfo")
    private LinkedList<MediaTaskResultInfoObjectInfo> objectInfo;

    @XStreamImplicit(itemFieldName="SpriteObjectInfo")
    private LinkedList<SpriteObjectInfo> spriteObjectInfo;

    public String getObjectCount() {
        return objectCount;
    }

    public void setObjectCount(String objectCount) {
        this.objectCount = objectCount;
    }

    public String getSpriteObjectCount() {
        return spriteObjectCount;
    }

    public void setSpriteObjectCount(String spriteObjectCount) {
        this.spriteObjectCount = spriteObjectCount;
    }

    public LinkedList<MediaTaskResultInfoObjectInfo> getObjectInfo() {
        if (objectInfo == null) {
            objectInfo = new LinkedList<>();
        }
        return objectInfo;
    }

    public void setObjectInfo(LinkedList<MediaTaskResultInfoObjectInfo> objectInfo) {
        this.objectInfo = objectInfo;
    }

    public LinkedList<SpriteObjectInfo> getSpriteObjectInfo() {
        if (spriteObjectInfo == null) {
            spriteObjectInfo = new LinkedList<>();
        }
        return spriteObjectInfo;
    }

    public void setSpriteObjectInfo(LinkedList<SpriteObjectInfo> spriteObjectInfo) {
        this.spriteObjectInfo = spriteObjectInfo;
    }

    @Override
    public String toString() {
        return "MediaTaskResultInfo{" +
                "objectCount='" + objectCount + '\'' +
                ", spriteObjectCount='" + spriteObjectCount + '\'' +
                ", objectInfo=" + objectInfo +
                ", spriteObjectInfo=" + spriteObjectInfo +
                '}';
    }
}
