package com.qcloud.cos.model.ciModel.auditing;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

public class OcrResults {
    @XStreamAlias("Text")
    private String text;
    /**
     * 兼容字段：单值形式的关键词。
     * 服务端可能返回多个 &lt;Keywords&gt; 节点；本字段保留最后一次赋值以兼容旧代码。
     * 需要完整多值时请使用 {@link #keywordsList}。
     */
    @XStreamAlias("Keywords")
    private String keywords;
    /**
     * 多值形式的关键词列表。文档规定 Keywords 是 String Array，
     * XML 中会以多个 &lt;Keywords&gt; 节点重复出现。
     */
    @XStreamImplicit(itemFieldName = "Keywords")
    private List<String> keywordsList;
    @XStreamAlias("Location")
    private Location location;
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public List<String> getKeywordsList() {
        if (keywordsList == null) {
            keywordsList = new ArrayList<String>();
        }
        return keywordsList;
    }

    public void setKeywordsList(List<String> keywordsList) {
        this.keywordsList = keywordsList;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("OcrResults{");
        sb.append("text='").append(text).append('\'');
        sb.append(", keywords='").append(keywords).append('\'');
        sb.append(", keywordsList=").append(keywordsList);
        sb.append(", location=").append(location);
        sb.append('}');
        return sb.toString();
    }
}
