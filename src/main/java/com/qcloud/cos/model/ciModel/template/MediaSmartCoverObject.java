package com.qcloud.cos.model.ciModel.template;


public class MediaSmartCoverObject {

    /**
     * 封面图片类型
     * png、jpg、webp
     */
    private String format;
    /**
     * 封面图片宽度
     * 1. 值范围：[128，4096]
     * 2. 单位：px
     */
    private String width;
    /**
     * 封面图片高度
     * 1. 值范围：[128，4096]
     * 2. 单位：px
     */
    private String height;
    /**
     * 封面图片数量
     * 值范围：[1，10] 默认为3
     */
    private String count;
    /**
     * 封面是否去重
     * true/false
     */
    private String deleteDuplicates;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getDeleteDuplicates() {
        return deleteDuplicates;
    }

    public void setDeleteDuplicates(String deleteDuplicates) {
        this.deleteDuplicates = deleteDuplicates;
    }

    @Override
    public String
    toString() {
        final StringBuffer sb = new StringBuffer("MediaSmartCoverObject{");
        sb.append("format='").append(format).append('\'');
        sb.append(", width='").append(width).append('\'');
        sb.append(", height='").append(height).append('\'');
        sb.append(", count='").append(count).append('\'');
        sb.append(", deleteDuplicates='").append(deleteDuplicates).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
