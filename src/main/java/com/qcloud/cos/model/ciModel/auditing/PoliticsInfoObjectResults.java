package com.qcloud.cos.model.ciModel.auditing;

/**
 * 该字段表示审核到的一些具体结果，例如：政治人物名称<br/>注意：该字段仅在 PoliticsInfo 中返回
 */
public class PoliticsInfoObjectResults {
    private String name;
    private String location;
    private String SubLabel;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setSubLabel(String SubLabel) {
        this.SubLabel = SubLabel;
    }

    public String getSubLabel() {
        return SubLabel;
    }

    @Override
    public String toString() {
        return "PoliticsInfoObjectResults{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", SubLabel='" + SubLabel + '\'' +
                '}';
    }
}
