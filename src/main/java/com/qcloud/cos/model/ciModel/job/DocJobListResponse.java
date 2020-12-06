package com.qcloud.cos.model.ciModel.job;

import java.util.ArrayList;
import java.util.List;

public class DocJobListResponse {
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
