package com.qcloud.cos.model.ciModel.auditing;

/**
 * 输入文件在cos中的位置
 * 例 cos根目录下的1.txt文件  则object = 1.txt
 * cos根目录下test文件夹中的1.txt文件 object = test/1.txt
 */
public class DocumentInputObject {
    private String url;
    private String type;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
