package com.qcloud.cos.model.ciModel.common;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 输入文件在cos中的位置
 * 例 cos根目录下的1.txt文件  则object = 1.txt
 *    cos根目录下test文件夹中的1.txt文件 object = test/1.txt
 */
public class MediaInputObject {
    @XStreamAlias("Object")
    private String object;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }
}
