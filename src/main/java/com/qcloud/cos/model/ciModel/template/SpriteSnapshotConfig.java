package com.qcloud.cos.model.ciModel.template;

public class SpriteSnapshotConfig {
    /**
     * 单图宽度 值范围：[8，4096]
     * 单位：px
     */
    private String cellWidth;
    /**
     * 单图高度 值范围：[8，4096]
     * 单位：px
     */
    private String CellHeight;
    /**
     * 雪碧图内边距大小 值范围：[0，1024]
     * 单位：px
     */
    private String Padding;
    /**
     * 雪碧图外边距大小 值范围：[0，1024]
     * 单位：px
     */
    private String Margin;
    /**
     * 背景颜色 FFmpeg https://www.ffmpeg.org/ffmpeg-utils.html#color-syntax
     */
    private String Color;
    /**
     * 雪碧图列数	 值范围：[1，10000]
     * 单位：px
     */
    private String Columns;
    /**
     * 雪碧图行数	 值范围：[1，10000]
     * 单位：px
     */
    private String Lines;
    /**
     * 雪碧图缩放模式
     * DirectScale: 指定宽高缩放
     * MaxWHScaleAndPad: 指定最大宽高缩放填充
     * MaxWHScale: 指定最大宽高缩放
     * 主动设置 CellWidth 和CellHeight 时生效
     */
    private String scaleMethod;

    public String getCellWidth() {
        return cellWidth;
    }

    public void setCellWidth(String cellWidth) {
        this.cellWidth = cellWidth;
    }

    public String getCellHeight() {
        return CellHeight;
    }

    public void setCellHeight(String cellHeight) {
        CellHeight = cellHeight;
    }

    public String getPadding() {
        return Padding;
    }

    public void setPadding(String padding) {
        Padding = padding;
    }

    public String getMargin() {
        return Margin;
    }

    public void setMargin(String margin) {
        Margin = margin;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getColumns() {
        return Columns;
    }

    public void setColumns(String columns) {
        Columns = columns;
    }

    public String getLines() {
        return Lines;
    }

    public void setLines(String lines) {
        Lines = lines;
    }

    public String getScaleMethod() {
        return scaleMethod;
    }

    public void setScaleMethod(String scaleMethod) {
        this.scaleMethod = scaleMethod;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SpriteSnapshotConfig{");
        sb.append("cellWidth='").append(cellWidth).append('\'');
        sb.append(", CellHeight='").append(CellHeight).append('\'');
        sb.append(", Padding='").append(Padding).append('\'');
        sb.append(", Margin='").append(Margin).append('\'');
        sb.append(", Color='").append(Color).append('\'');
        sb.append(", Columns='").append(Columns).append('\'');
        sb.append(", Lines='").append(Lines).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
