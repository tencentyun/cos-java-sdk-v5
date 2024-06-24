package com.qcloud.cos.model.ciModel.metaInsight;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;

public class File {

    /**
     *自定义ID。该文件索引到数据集后，作为该行元数据的属性存储，用于和您的业务系统进行关联、对应。您可以根据业务需求传入该值，例如将某个URI关联到您系统内的某个ID。推荐传入全局唯一的值。在查询时，该字段支持前缀查询和排序，详情请见字段和操作符的支持列表。   
     */
    @JsonProperty("CustomId")
    private String customId;

    /**
     *自定义标签。您可以根据业务需要自定义添加标签键值对信息，用于在查询时可以据此为筛选项进行检索，详情请见字段和操作符的支持列表。  
     */
    @JsonProperty("CustomLabels")
    private HashMap<String, String> customLabels;

    /**
     *自定义标签键 
     */
    @JsonProperty("Key")
    private String key;

    /**
     *自定义标签值 
     */
    @JsonProperty("Value")
    private String value;

    /**
     *可选项，文件媒体类型，枚举值： image：图片。  other：其他。 document：文档。 archive：压缩包。 video：视频。  audio：音频。  
     */
    @JsonProperty("MediaType")
    private String mediaType;

    /**
     *可选项，文件内容类型（MIME Type），如image/jpeg。  
     */
    @JsonProperty("ContentType")
    private String contentType;

    /**
     *资源标识字段，表示需要建立索引的文件地址，当前仅支持COS上的文件，字段规则：cos:///，其中BucketName表示COS存储桶名称，ObjectKey表示文件完整路径，例如：cos://examplebucket-1250000000/test1/img.jpg。 注意： 1、仅支持本账号内的COS文件 2、不支持HTTP开头的地址
     */
    @JsonProperty("URI")
    private String uRI;

    /**
     *输入图片中检索的人脸数量，默认值为20，最大值为20。(仅当数据集模板 ID 为 Official:FaceSearch 有效)。
     */
    @JsonProperty("MaxFaceNum")
    private Integer maxFaceNum;

    /**
     *自定义人物属性(仅当数据集模板 ID 为 Official:FaceSearch 有效)。
     */
    @JsonProperty("Persons")
    private List<Persons> persons;

    public String getCustomId() { return customId; }

    public void setCustomId(String customId) { this.customId = customId; }

    public HashMap getCustomLabels() { return customLabels; }

    public void setCustomLabels(HashMap customLabels) { this.customLabels = customLabels; }

    public String getKey() { return key; }

    public void setKey(String key) { this.key = key; }

    public String getValue() { return value; }

    public void setValue(String value) { this.value = value; }

    public String getMediaType() { return mediaType; }

    public void setMediaType(String mediaType) { this.mediaType = mediaType; }

    public String getContentType() { return contentType; }

    public void setContentType(String contentType) { this.contentType = contentType; }

    public String getURI() { return uRI; }

    public void setURI(String uRI) { this.uRI = uRI; }

    public Integer getMaxFaceNum() { return maxFaceNum; }

    public void setMaxFaceNum(Integer maxFaceNum) { this.maxFaceNum = maxFaceNum; }

    public List<Persons> getPersons() { return persons; }

    public void setPersons(List<Persons> persons) { this.persons = persons; }

}
