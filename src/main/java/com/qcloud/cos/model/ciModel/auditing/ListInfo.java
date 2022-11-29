package com.qcloud.cos.model.ciModel.auditing;

import java.util.ArrayList;
import java.util.List;

public class ListInfo {
    /**
     * 用户业务TokenId
     */
    private List<ListResult> listResults;

    public List<ListResult> getListResults() {
        if (listResults ==null){
            listResults = new ArrayList<>();
        }
        return listResults;
    }

    public void setListResults(List<ListResult> listResults) {
        this.listResults = listResults;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ListInfo{");
        sb.append("listResults=").append(listResults);
        sb.append('}');
        return sb.toString();
    }
}
