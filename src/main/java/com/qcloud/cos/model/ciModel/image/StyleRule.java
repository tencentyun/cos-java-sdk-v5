package com.qcloud.cos.model.ciModel.image;

public class StyleRule {
    /**
     * 样式名称
     */
    private String styleName;
    /**
     * 样式详情
     */
    private String styleBody;

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public String getStyleBody() {
        return styleBody;
    }

    public void setStyleBody(String styleBody) {
        this.styleBody = styleBody;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("StyleRule{");
        sb.append("styleName='").append(styleName).append('\'');
        sb.append(", styleBody='").append(styleBody).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
