package com.qcloud.cos.model.ciModel.metaInsight;

import com.qcloud.cos.internal.CIServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.ArrayList;
import java.util.List;


public class DatasetFaceSearchRequest extends CIServiceRequest {

    /**
     *数据集名称，同一个账户下唯一。;是否必传：是
     */
    private String datasetName;

    /**
     *资源标识字段，表示需要建立索引的文件地址。;是否必传：是
     */
    private String uRI;

    /**
     *输入图片中检索的人脸数量，默认值为1(传0或不传采用默认值)，最大值为10。;是否必传：否
     */
    private Integer maxFaceNum;

    /**
     *检索的每张人脸返回相关人脸数量，默认值为10，最大值为100。;是否必传：否
     */
    private Integer limit;

    /**
     *出参 Score 中，只有超过 MatchThreshold 值的结果才会返回。范围：1-100，默认值为0，推荐值为80。;是否必传：否
     */
    private Integer matchThreshold;

    public String getDatasetName() { return datasetName; }

    public void setDatasetName(String datasetName) { this.datasetName = datasetName; }

    public String getURI() { return uRI; }

    public void setURI(String uRI) { this.uRI = uRI; }

    public Integer getMaxFaceNum() { return maxFaceNum; }

    public void setMaxFaceNum(Integer maxFaceNum) { this.maxFaceNum = maxFaceNum; }

    public Integer getLimit() { return limit; }

    public void setLimit(Integer limit) { this.limit = limit; }

    public Integer getMatchThreshold() { return matchThreshold; }

    public void setMatchThreshold(Integer matchThreshold) { this.matchThreshold = matchThreshold; }

    


}
