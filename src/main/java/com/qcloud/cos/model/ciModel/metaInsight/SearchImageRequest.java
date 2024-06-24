package com.qcloud.cos.model.ciModel.metaInsight;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qcloud.cos.internal.CIServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.ArrayList;
import java.util.List;


public class SearchImageRequest extends CIServiceRequest {

    /**
     *数据集名称，同一个账户下唯一。;是否必传：是
     */
    private String datasetName;

    /**
     *指定检索方式为图片或文本，pic 为图片检索，text 为文本检索，默认为 pic。;是否必传：否
     */
    private String mode;

    /**
     *资源标识字段，表示需要建立索引的文件地址(Mode 为 pic 时必选)。;是否必传：否
     */
    @JsonProperty("URI")
    private String uRI;

    /**
     *返回相关图片的数量，默认值为10，最大值为100。;是否必传：否
     */
    private Integer limit;

    /**
     *检索语句，检索方式为 text 时必填，最多支持60个字符 (Mode 为 text 时必选)。;是否必传：否
     */
    private String text;

    /**
     *出参 Score（相关图片匹配得分） 中，只有超过 MatchThreshold 值的结果才会返回。默认值为0，推荐值为80。;是否必传：否
     */
    private Integer matchThreshold;

    public String getDatasetName() { return datasetName; }

    public void setDatasetName(String datasetName) { this.datasetName = datasetName; }

    public String getMode() { return mode; }

    public void setMode(String mode) { this.mode = mode; }

    public String getURI() { return uRI; }

    public void setURI(String uRI) { this.uRI = uRI; }

    public Integer getLimit() { return limit; }

    public void setLimit(Integer limit) { this.limit = limit; }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    public Integer getMatchThreshold() { return matchThreshold; }

    public void setMatchThreshold(Integer matchThreshold) { this.matchThreshold = matchThreshold; }

    


}
