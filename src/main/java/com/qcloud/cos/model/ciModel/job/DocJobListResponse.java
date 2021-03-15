package com.qcloud.cos.model.ciModel.job;

import com.qcloud.cos.model.CiServiceResult;

import java.util.ArrayList;
import java.util.List;

public class DocJobListResponse extends CiServiceResult {
    List<DocJobDetail> docJobDetailList;

    public List<DocJobDetail> getDocJobDetailList() {
        if (docJobDetailList==null){
            docJobDetailList = new ArrayList<>();
        }
        return docJobDetailList;
    }

    public void setDocJobDetailList(List<DocJobDetail> docJobDetailList) {
        this.docJobDetailList = docJobDetailList;
    }
}
