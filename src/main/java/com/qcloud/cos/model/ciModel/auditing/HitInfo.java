package com.qcloud.cos.model.ciModel.auditing;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

public class HitInfo {
    @XStreamAlias("Type")
    private String type;

    @XStreamAlias("Keyword")
    private String keyword;

    @XStreamAlias("LibName")
    private String libName;

    @XStreamImplicit(itemFieldName = "Positions")
    private List<TextPosition> positions;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getLibName() {
        return libName;
    }

    public void setLibName(String libName) {
        this.libName = libName;
    }

    public List<TextPosition> getPositions() {
        return positions;
    }

    public void setPositions(List<TextPosition> positions) {
        this.positions = positions;
    }
}
