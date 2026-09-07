package com.qcloud.cos.model.ciModel.auditing;

import java.util.ArrayList;
import java.util.List;


public class LibResult {
    private String imageId;
    private String score;
    /**
     * 命中库的类型（音频审核 AudioSection.*Info.LibResults 使用）。
     * 例如：预设黑名单库 / 用户自定义库。
     */
    private String libType;
    /**
     * 命中库的名称（音频审核 AudioSection.*Info.LibResults 使用）。
     */
    private String libName;
    /**
     * 命中的关键词列表（音频审核 AudioSection.*Info.LibResults 使用）。
     * XML 中以多个 &lt;Keywords&gt; 节点重复出现，SDK 用 List 承载。
     */
    private List<String> keywords;

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getLibType() {
        return libType;
    }

    public void setLibType(String libType) {
        this.libType = libType;
    }

    public String getLibName() {
        return libName;
    }

    public void setLibName(String libName) {
        this.libName = libName;
    }

    public List<String> getKeywords() {
        if (keywords == null) {
            keywords = new ArrayList<String>();
        }
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("LibResult{");
        sb.append("imageId='").append(imageId).append('\'');
        sb.append(", score='").append(score).append('\'');
        sb.append(", libType='").append(libType).append('\'');
        sb.append(", libName='").append(libName).append('\'');
        sb.append(", keywords=").append(keywords);
        sb.append('}');
        return sb.toString();
    }
}
