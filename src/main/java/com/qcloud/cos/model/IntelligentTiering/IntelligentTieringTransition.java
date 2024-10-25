package com.qcloud.cos.model.IntelligentTiering;

import java.io.Serializable;

public class IntelligentTieringTransition implements Serializable {
    private String accessTier;

    private int days = -1;

    private int requestFrequent = -1;

    public void setAccessTier(String accessTier) {
        this.accessTier = accessTier;
    }

    public String getAccessTier() {
        return accessTier;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getDays() {
        return days;
    }

    public int getRequestFrequent() {
        return requestFrequent;
    }

    public void setRequestFrequent(int requestFrequent) {
        this.requestFrequent = requestFrequent;
    }
}
