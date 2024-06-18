package com.qcloud.cos.model.ciModel.metaInsight;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.HashMap;

@XStreamAlias("FileResult")
public class FileResult {

    /**
     *对象唯一ID。      
     */
    @XStreamAlias("ObjectId")
    private String objectId;

    /**
     * 元数据创建时间的时间戳，格式为RFC3339Nano 
     */
    @XStreamAlias("CreateTime")
    private String createTime;

    /**
     * 元数据修改时间的时间戳，格式为RFC3339Nano创建元数据后，如果未更新过元数据，则元数据修改时间的时间戳和元数据创建时间的时间戳相同 
     */
    @XStreamAlias("UpdateTime")
    private String updateTime;

    /**
     *  资源标识字段，表示需要建立索引的文件地址  
     */
    @XStreamAlias("URI")
    private String uRI;

    /**
     *  文件路径  
     */
    @XStreamAlias("Filename")
    private String filename;

    /**
     *   文件媒体类型。 枚举值：image：图片。other：其他。document：文档。archive：压缩包。audio：音频。video：视频。
     */
    @XStreamAlias("MediaType")
    private String mediaType;

    /**
     *  文件内容类型（MIME Type）。
     */
    @XStreamAlias("ContentType")
    private String contentType;

    /**
     *  文件存储空间类型。
     */
    @XStreamAlias("COSStorageClass")
    private String cOSStorageClass;

    /**
     *   文件CRC64值。 
     */
    @XStreamAlias("COSCRC64")
    private String cOSCRC64;

    /**
     *   文件大小，单位为字节。 
     */
    @XStreamAlias("Size")
    private Integer size;

    /**
     *   指定Object被下载时网页的缓存行为。该字段需要设置COS Object HTTP属性Cache-Control。 
     */
    @XStreamAlias("CacheControl")
    private String cacheControl;

    /**
     *   指定Object被下载时的名称。需要设置COS Object HTTP属性Content-Disposition。 
     */
    @XStreamAlias("ContentDisposition")
    private String contentDisposition;

    /**
     *   指定该Object被下载时的内容编码格式。需要设置COS Object HTTP属性Content-Encoding。 
     */
    @XStreamAlias("ContentEncoding")
    private String contentEncoding;

    /**
     *   Object内容使用的语言。需要设置COS Object HTTP属性Content-Language。 
     */
    @XStreamAlias("ContentLanguage")
    private String contentLanguage;

    /**
     *   加密算法,需要设置x-cos-server-side-encryption。 
     */
    @XStreamAlias("ServerSideEncryption")
    private String serverSideEncryption;

    /**
     *   Object生成时会创建相应的ETag ，ETag用于标识一个Object的内容。 
     */
    @XStreamAlias("ETag")
    private String eTag;

    /**
     *  文件最近一次修改时间的时间戳， 格式为RFC3339Nano。
     */
    @XStreamAlias("FileModifiedTime")
    private String fileModifiedTime;

    /**
     *  该文件的自定义ID。该文件索引到数据集后，作为该行元数据的属性存储，用于和您的业务系统进行关联、对应。您可以根据业务需求传入该值，例如将某个URI关联到您系统内的某个ID。推荐传入全局唯一的值。
     */
    @XStreamAlias("CustomId")
    private String customId;

    /**
     *  文件自定义标签列表。储存您业务自定义的键名、键值对信息，用于在查询时可以据此为筛选项进行检索。
     */
    @XStreamAlias("CustomLabels")
    private HashMap<String, String> customLabels;

    /**
     *  cos自定义头部。储存您业务在cos object上的键名、键值对信息，用于在查询时可以据此为筛选项进行检索。
     */
    @XStreamAlias("COSUserMeta")
    private HashMap cOSUserMeta;

    /**
     *   文件访问权限属性。 
     */
    @XStreamAlias("ObjectACL")
    private String objectACL;

    /**
     *  cos自定义标签。储存您业务在cos object上的自定义标签的键名、键值对信息，用于在查询时可以据此为筛选项进行检索。
     */
    @XStreamAlias("COSTagging")
    private HashMap cOSTagging;

    /**
     *   cos自定义标签的数量。 
     */
    @XStreamAlias("COSTaggingCount")
    private Integer cOSTaggingCount;

    /**
     *数据集名称。
     */
    @XStreamAlias("DatasetName")
    private String datasetName;

    public String getObjectId() { return objectId; }

    public void setObjectId(String objectId) { this.objectId = objectId; }

    public String getCreateTime() { return createTime; }

    public void setCreateTime(String createTime) { this.createTime = createTime; }

    public String getUpdateTime() { return updateTime; }

    public void setUpdateTime(String updateTime) { this.updateTime = updateTime; }

    public String getURI() { return uRI; }

    public void setURI(String uRI) { this.uRI = uRI; }

    public String getFilename() { return filename; }

    public void setFilename(String filename) { this.filename = filename; }

    public String getMediaType() { return mediaType; }

    public void setMediaType(String mediaType) { this.mediaType = mediaType; }

    public String getContentType() { return contentType; }

    public void setContentType(String contentType) { this.contentType = contentType; }

    public String getCOSStorageClass() { return cOSStorageClass; }

    public void setCOSStorageClass(String cOSStorageClass) { this.cOSStorageClass = cOSStorageClass; }

    public String getCOSCRC64() { return cOSCRC64; }

    public void setCOSCRC64(String cOSCRC64) { this.cOSCRC64 = cOSCRC64; }

    public Integer getSize() { return size; }

    public void setSize(Integer size) { this.size = size; }

    public String getCacheControl() { return cacheControl; }

    public void setCacheControl(String cacheControl) { this.cacheControl = cacheControl; }

    public String getContentDisposition() { return contentDisposition; }

    public void setContentDisposition(String contentDisposition) { this.contentDisposition = contentDisposition; }

    public String getContentEncoding() { return contentEncoding; }

    public void setContentEncoding(String contentEncoding) { this.contentEncoding = contentEncoding; }

    public String getContentLanguage() { return contentLanguage; }

    public void setContentLanguage(String contentLanguage) { this.contentLanguage = contentLanguage; }

    public String getServerSideEncryption() { return serverSideEncryption; }

    public void setServerSideEncryption(String serverSideEncryption) { this.serverSideEncryption = serverSideEncryption; }

    public String getETag() { return eTag; }

    public void setETag(String eTag) { this.eTag = eTag; }

    public String getFileModifiedTime() { return fileModifiedTime; }

    public void setFileModifiedTime(String fileModifiedTime) { this.fileModifiedTime = fileModifiedTime; }

    public String getCustomId() { return customId; }

    public void setCustomId(String customId) { this.customId = customId; }

    public HashMap getCustomLabels() { return customLabels; }

    public void setCustomLabels(HashMap customLabels) { this.customLabels = customLabels; }

    public HashMap getCOSUserMeta() { return cOSUserMeta; }

    public void setCOSUserMeta(HashMap cOSUserMeta) { this.cOSUserMeta = cOSUserMeta; }

    public String getObjectACL() { return objectACL; }

    public void setObjectACL(String objectACL) { this.objectACL = objectACL; }

    public HashMap getCOSTagging() { return cOSTagging; }

    public void setCOSTagging(HashMap cOSTagging) { this.cOSTagging = cOSTagging; }

    public Integer getCOSTaggingCount() { return cOSTaggingCount; }

    public void setCOSTaggingCount(Integer cOSTaggingCount) { this.cOSTaggingCount = cOSTaggingCount; }

    public String getDatasetName() { return datasetName; }

    public void setDatasetName(String datasetName) { this.datasetName = datasetName; }

}
