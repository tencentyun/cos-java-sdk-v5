package com.qcloud.cos.model.ciModel.metaInsight;

import com.qcloud.cos.internal.CIServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.ArrayList;
import java.util.List;


public class CreateFileMetaIndexRequest extends CIServiceRequest {

    /**
     *数据集名称，同一个账户下唯一。;是否必传：是
     */
    private String datasetName;

    /**
     *用于建立索引的文件信息。;是否必传：是
     */
    private File file;

    public String getDatasetName() { return datasetName; }

    public void setDatasetName(String datasetName) { this.datasetName = datasetName; }

    public File getFile() { 
        if(file == null){
            file = new File(); 
        }
        return file;
    }

    public void setFile(File file) { this.file = file; }

    


}
