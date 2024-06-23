package com.qcloud.cos.model.ciModel.metaInsight;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public class FilesDetail {

    /**
     * 元数据创建时间的时间戳，格式为RFC3339Nano 
     */
    @JsonProperty("CreateTime")
    private String createTime;

    /**
     * 元数据修改时间的时间戳，格式为RFC3339Nano创建元数据后，如果未更新过元数据，则元数据修改时间的时间戳和元数据创建时间的时间戳相同 
     */
    @JsonProperty("UpdateTime")
    private String updateTime;

    /**
     *  资源标识字段，表示需要建立索引的文件地址  
     */
    @JsonProperty("URI")
    private String uri;

    /**
     *  文件路径  
     */
    @JsonProperty("Filename")
    private String filename;

    /**
     *   文件媒体类型。 枚举值：image：图片。other：其他。document：文档。archive：压缩包。audio：音频。video：视频。
     */
    @JsonProperty("MediaType")
    private String mediaType;

    /**
     *  文件内容类型（MIME Type）。
     */
    @JsonProperty("ContentType")
    private String contentType;

    /**
     *  文件存储空间类型。
     */
    @JsonProperty("COSStorageClass")
    private String COSStorageClass;

    /**
     *   文件CRC64值。 
     */
    @JsonProperty("COSCRC64")
    private String COSCRC64;

    /**
     *   对象ACL。 
     */
    @JsonProperty("ObjectACL")
    private String objectACL;

    /**
     *   文件大小，单位为字节。 
     */
    @JsonProperty("Size")
    private Integer size;

    /**
     *   指定Object被下载时网页的缓存行为。 
     */
    @JsonProperty("CacheControl")
    private String cacheControl;

    /**
     *   Object生成时会创建相应的ETag ，ETag用于标识一个Object的内容。 
     */
    @JsonProperty("ETag")
    private String eTag;

    /**
     *  文件最近一次修改时间的时间戳， 格式为RFC3339Nano。
     */
    @JsonProperty("FileModifiedTime")
    private String fileModifiedTime;

    /**
     *  该文件的自定义ID。该文件索引到数据集后，作为该行元数据的属性存储，用于和您的业务系统进行关联、对应。您可以根据业务需求传入该值，例如将某个URI关联到您系统内的某个ID。推荐传入全局唯一的值。
     */
    @JsonProperty("CustomId")
    private String customId;

    @JsonProperty("OwnerID")
    private String ownerID;

    @JsonProperty("ObjectId")
    private String objectId;
    /**
     *  文件自定义标签列表。储存您业务自定义的键名、键值对信息，用于在查询时可以据此为筛选项进行检索。
     */
    @JsonProperty("CustomLabels")
    private HashMap<String, String> customLabels;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public void setCustomLabels(HashMap<String, String> customLabels) {
        this.customLabels = customLabels;
    }

    public String geteTag() {
        return eTag;
    }

    public void seteTag(String eTag) {
        this.eTag = eTag;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getCreateTime() { return createTime; }

    public void setCreateTime(String createTime) { this.createTime = createTime; }

    public String getUpdateTime() { return updateTime; }

    public void setUpdateTime(String updateTime) { this.updateTime = updateTime; }

    public String getURI() { return uri; }

    public void setURI(String uRI) { this.uri = uRI; }

    public String getFilename() { return filename; }

    public void setFilename(String filename) { this.filename = filename; }

    public String getMediaType() { return mediaType; }

    public void setMediaType(String mediaType) { this.mediaType = mediaType; }

    public String getContentType() { return contentType; }

    public void setContentType(String contentType) { this.contentType = contentType; }

    public String getCOSStorageClass() { return COSStorageClass; }

    public void setCOSStorageClass(String cOSStorageClass) { this.COSStorageClass = cOSStorageClass; }

    public String getCOSCRC64() { return COSCRC64; }

    public void setCOSCRC64(String cOSCRC64) { this.COSCRC64 = cOSCRC64; }

    public String getObjectACL() { return objectACL; }

    public void setObjectACL(String objectACL) { this.objectACL = objectACL; }

    public Integer getSize() { return size; }

    public void setSize(Integer size) { this.size = size; }

    public String getCacheControl() { return cacheControl; }

    public void setCacheControl(String cacheControl) { this.cacheControl = cacheControl; }

    public String getETag() { return eTag; }

    public void setETag(String eTag) { this.eTag = eTag; }

    public String getFileModifiedTime() { return fileModifiedTime; }

    public void setFileModifiedTime(String fileModifiedTime) { this.fileModifiedTime = fileModifiedTime; }

    public String getCustomId() { return customId; }

    public void setCustomId(String  CustomId) { this.customId =  CustomId; }

    public HashMap getCustomLabels() { return customLabels; }

}
