package com.qcloud.cos.model.ciModel.ai;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qcloud.cos.model.CosServiceResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

@XStreamAlias("RecognitionResult")
public class CreateAIObjectDetectJobResponse extends CosServiceResult {

    /**
     * 主体识别结果状态。0表示未识别到，1表示识别到。
     */
    @XStreamAlias("Status")
    @JsonProperty("Status")
    private Integer status;

    /**
     * 主体识别结果，可能有多个。
     */
    @XStreamImplicit(itemFieldName = "DetectMultiObj")
    @JsonProperty("DetectMultiObj")
    private List<DetectMultiObj> detectMultiObj;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @JsonProperty("DetectMultiObj")
    public List<DetectMultiObj> getDetectMultiObj() {
        return detectMultiObj;
    }

    public void setDetectMultiObj(List<DetectMultiObj> detectMultiObj) {
        this.detectMultiObj = detectMultiObj;
    }

    @Override
    public String toString() {
        return "CreateAIObjectDetectJobResponse{" +
                "status=" + status +
                ", detectMultiObj=" + detectMultiObj +
                '}';

    }

    @XStreamAlias("DetectMultiObj")
    public class DetectMultiObj {
        /**
         * 识别到主体的名称。
         */
        @XStreamAlias("Name")
        @JsonProperty("Name")
        private String name;

        /**
         * 识别到主体的置信度，取值范围为[0-100]。值越高概率越大。
         */
        @XStreamAlias("Confidence")
        @JsonProperty("Confidence")
        private String confidence;

        /**
         * 图中识别到主体的坐标。
         */
        @XStreamAlias("Location")
        @JsonProperty("Location")
        private Location location;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getConfidence() {
            return confidence;
        }

        public void setConfidence(String confidence) {
            this.confidence = confidence;
        }

        @JsonProperty("Location")
        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        @Override
        public String toString() {
            return "DetectMultiObj{" +
                    "name='" + name + '\'' +
                    ", sorce=" + confidence +
                    ", location=" + location +
                    '}';
        }
    }

    @XStreamAlias("Location")
    public class Location {
        /**
         * 识别主体框左上角横坐标。
         */
        @XStreamAlias("X")
        @JsonProperty("X")
        private Integer x;

        /**
         * 识别主体框左上角纵坐标。
         */
        @XStreamAlias("Y")
        @JsonProperty("Y")
        private Integer y;

        /**
         * 识别主体框高度。
         */
        @XStreamAlias("Width")
        @JsonProperty("Width")
        private Integer width;

        /**
         * 识别主体框高度。
         */
        @XStreamAlias("Height")
        @JsonProperty("Height")
        private Integer height;

        public Integer getX() {
            return x;
        }

        public void setX(Integer x) {
            this.x = x;
        }

        public Integer getY() {
            return y;
        }

        public void setY(Integer y) {
            this.y = y;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        @Override
        public String toString() {
            return "Location{" +
                    "x=" + x +
                    ", y=" + y +
                    ", width=" + width +
                    ", height=" + height +
                    '}';
        }
    }

}
