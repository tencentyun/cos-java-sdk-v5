
package com.qcloud.cos.model.ciModel.workflow;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BasicInfo")
public class BasicInfo {
    @XStreamAlias("ContentType")
    private String contentType;

    @XStreamAlias("Size")
    private String size;

    @XStreamAlias("ETag")
    private String eTag;

    @XStreamAlias("LastModified")
    private String lastModified;

    @XStreamAlias("Object")
    private String object;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String geteTag() {
        return eTag;
    }

    public void seteTag(String eTag) {
        this.eTag = eTag;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }
}