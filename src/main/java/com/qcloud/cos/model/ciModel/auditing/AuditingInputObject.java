package com.qcloud.cos.model.ciModel.auditing;

/**
 * 输入文件在cos中的位置
 * 例 cos根目录下的1.txt文件  则object = 1.txt
 * cos根目录下test文件夹中的1.txt文件 object = test/1.txt
 */
public class AuditingInputObject {
    private String object;
    private String content;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AuditingInputObject{");
        sb.append("object='").append(object).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
