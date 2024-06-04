package com.qcloud.cos.model.ciModel.ai;

import com.qcloud.cos.model.CosServiceResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

@XStreamAlias("RecognitionResult")
public class RecognizeLogoResponse extends CosServiceResult {

    /**
     *Logo识别结果，可能有多个
     */
    @XStreamImplicit(itemFieldName = "LogoInfo")
    private List<LogoInfo> logoInfo;

    /**
     *Logo识别结果。0表示未识别到，1表示识别到
     */
    @XStreamAlias("Status")
    private Integer status;

    public List<LogoInfo> getLogoInfo() { return logoInfo; }

    public void setLogoInfo(List<LogoInfo> logoInfo) { this.logoInfo = logoInfo; }

    public Integer getStatus() { return status; }

    public void setStatus(Integer status) { this.status = status; }

    
    @XStreamAlias("LogoInfo")
    public class LogoInfo {
        /**
         *Logo的名称
         */
        @XStreamAlias("Name")
        private String name;

        /**
         *Logo的置信度，取值范围为[0-100]。值越高概率越大。
         */
        @XStreamAlias("Score")
        private Integer score;

        /**
         *图中识别到Logo的坐标
         */
        @XStreamImplicit(itemFieldName = "Location")
        private List<Location> location;

        public String getName() { return name; }

        public void setName(String name) { this.name = name; }

        public Integer getScore() { return score; }

        public void setScore(Integer score) { this.score = score; }

        public List<Location> getLocation() { return location; }

        public void setLocation(List<Location> location) { this.location = location; }

    }

    @XStreamAlias("Location")
    public class Location {
        /**
         *Logo坐标点（X坐标,Y坐标）
         */
        @XStreamImplicit(itemFieldName = "Point")
        private List<String> point;

        public List<String> getPoint() { return point; }

        public void setPoint(List<String> point) { this.point = point; }

    }

}
