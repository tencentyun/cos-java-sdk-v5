package com.qcloud.cos.model.ciModel.job;

import java.util.ArrayList;
import java.util.List;

public class DetailedResult {
    private String type;
    private List<QualityEstimateItem> items;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<QualityEstimateItem> getItems() {
        if (items == null) {
            items = new ArrayList<>();
        }
        return items;
    }

    public void setItems(List<QualityEstimateItem> items) {
        this.items = items;
    }
}
