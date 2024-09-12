package com.qcloud.cos.model.IntelligentTiering;

import com.qcloud.cos.model.TagSet;

import java.io.Serializable;
import java.util.List;

public class IntelligentTieringFilter implements Serializable {
    private String prefix;

    private List<TagSet> tagSets;

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public List<TagSet> getAllTagSets() {
        return this.tagSets;
    }

    public void setTagSets(List<TagSet> tagSets) {
        this.tagSets = tagSets;
    }
}
