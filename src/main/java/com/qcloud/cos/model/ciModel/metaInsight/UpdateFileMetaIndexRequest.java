package com.qcloud.cos.model.ciModel.metaInsight;

import com.qcloud.cos.internal.CIServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.ArrayList;
import java.util.List;


public class UpdateFileMetaIndexRequest extends CIServiceRequest {

    /**
     *数据集名称，同一个账户下唯一。;是否必传：是
     */
    private String datasetName;

    /**
     *元数据索引结果（以回调形式发送至您的回调地址，支持以 http:// 或者 https:// 开头的地址，例如： http://www.callback.com;是否必传：是
     */
    private String callback;

    /**
     *用于建立索引的文件信息。;是否必传：是
     */
    private File file;

    public String getDatasetName() { return datasetName; }

    public void setDatasetName(String datasetName) { this.datasetName = datasetName; }

    public String getCallback() { return callback; }

    public void setCallback(String callback) { this.callback = callback; }

    public File getFile() { 
        if(file == null){
            file = new File(); 
        }
        return file;
    }

    public void setFile(File file) { this.file = file; }

    


}
