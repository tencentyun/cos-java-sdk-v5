package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

public class OutputFile {
    @XStreamAlias("Region")
    private String region;
    @XStreamAlias("Bucket")
    private String bucket;
    @XStreamAlias("ObjectPrefix")
    private String objectPrefix;
    @XStreamImplicit(itemFieldName = "ObjectName")
    private List<String> objectNames;
    @XStreamAlias("Md5Info")
    private Md5Info md5Info;
    @XStreamImplicit(itemFieldName = "ObjectUrl")
    private List<String> objectUrls;

    public List<String> getObjectUrls() {
        return objectUrls;
    }

    public void setObjectUrls(List<String> objectUrls) {
        this.objectUrls = objectUrls;
    }
    
    public String getObjectUrl() {
        if (objectUrls != null && !objectUrls.isEmpty()) {
            return objectUrls.get(0);
        }
        return null;
    }

    public void setObjectUrl(String objectUrl) {
        if (objectUrls == null) {
            objectUrls = new ArrayList<>();
        }
        if (!objectUrls.isEmpty()) {
            objectUrls.set(0, objectUrl);
        } else {
            objectUrls.add(objectUrl);
        }
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getObjectPrefix() {
        return objectPrefix;
    }

    public void setObjectPrefix(String objectPrefix) {
        this.objectPrefix = objectPrefix;
    }

    public List<String> getObjectNames() {
        return objectNames;
    }

    public void setObjectNames(List<String> objectNames) {
        this.objectNames = objectNames;
    }
    
    public String getObjectName() {
        if (objectNames != null && !objectNames.isEmpty()) {
            return objectNames.get(0);
        }
        return null;
    }

    public void setObjectName(String objectName) {
        if (objectNames == null) {
            objectNames = new ArrayList<>();
        }
        if (!objectNames.isEmpty()) {
            objectNames.set(0, objectName);
        } else {
            objectNames.add(objectName);
        }
    }

    public Md5Info getMd5Info() {
        if (md5Info == null) {
            md5Info = new Md5Info();
        }
        return md5Info;
    }

    public void setMd5Info(Md5Info md5Info) {
        this.md5Info = md5Info;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OutputFile{");
        sb.append("region='").append(region).append('\'');
        sb.append(", bucket='").append(bucket).append('\'');
        sb.append(", objectPrefix='").append(objectPrefix).append('\'');
        sb.append(", objectNames=").append(objectNames);
        sb.append(", objectUrls=").append(objectUrls);
        sb.append(", md5Info=").append(md5Info);
        sb.append('}');
        return sb.toString();
    }
}
