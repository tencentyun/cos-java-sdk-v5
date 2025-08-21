package com.qcloud.cos.model.ciModel.workflow;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import java.util.LinkedList;

@XStreamAlias("JudgementInfo")
public class MediaTaskJudgementInfo {
    @XStreamAlias("ObjectCount")
    private int objectCount;

    @XStreamImplicit(itemFieldName = "JudgementResult")
    private LinkedList<JudgementResultObject> judgementResult;

    public int getObjectCount() {
        return objectCount;
    }

    public void setObjectCount(int objectCount) {
        this.objectCount = objectCount;
    }

    public LinkedList<JudgementResultObject> getJudgementResult() {
        if (judgementResult == null) {
            judgementResult = new LinkedList<>();
        }
        return judgementResult;
    }

    public void setJudgementResult(LinkedList<JudgementResultObject> judgementResult) {
        this.judgementResult = judgementResult;
    }

    @Override
    public String toString() {
        return "MediaTaskJudgementInfo{" +
                "objectCount=" + objectCount +
                ", judgementResult=" + judgementResult +
                '}';
    }
}
