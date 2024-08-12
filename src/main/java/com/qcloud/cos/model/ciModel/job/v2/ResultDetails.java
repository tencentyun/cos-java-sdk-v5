package com.qcloud.cos.model.ciModel.job.v2;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

@XStreamAlias("ResultDetails")
public class ResultDetails {
    /**
     * 截图的时间点，单位为秒
     */
    @XStreamAlias("Time")
    private String time;

    /**
     * 截图 URL
     */
    @XStreamAlias("Url")
    private String url;

    /**
     * 识别结果
     */
    @XStreamImplicit(itemFieldName = "ResultInfo")
    private List<ResultInfo> resultInfo;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ResultInfo> getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(List<ResultInfo> resultInfo) {
        this.resultInfo = resultInfo;
    }


}
