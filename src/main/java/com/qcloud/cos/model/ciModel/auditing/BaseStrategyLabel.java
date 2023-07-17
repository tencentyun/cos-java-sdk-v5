package com.qcloud.cos.model.ciModel.auditing;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

public class BaseStrategyLabel {
    @XStreamImplicit(itemFieldName = "Porn")
    private List<String> porn;
    @XStreamImplicit(itemFieldName = "Politics")
    private List<String> politics;
    @XStreamImplicit(itemFieldName = "Terrorism")
    private List<String> terrorism;
    @XStreamImplicit(itemFieldName = "Ads")
    private List<String> ads;
    @XStreamImplicit(itemFieldName = "Abuse")
    private List<String> abuse;
    @XStreamImplicit(itemFieldName = "Illegal")
    private List<String> illegal;

    public List<String> getPorn() {
        if (porn == null) {
            porn = new ArrayList<>();
        }
        return porn;
    }

    public void setPorn(List<String> porn) {
        this.porn = porn;
    }

    public List<String> getPolitics() {
        if (politics == null) {
            politics = new ArrayList<>();
        }
        return politics;
    }

    public void setPolitics(List<String> politics) {
        this.politics = politics;
    }

    public List<String> getTerrorism() {
        if (terrorism == null) {
            terrorism = new ArrayList<>();
        }
        return terrorism;
    }

    public void setTerrorism(List<String> terrorism) {
        this.terrorism = terrorism;
    }

    public List<String> getAds() {
        if (ads == null) {
            ads = new ArrayList<>();
        }
        return ads;
    }

    public void setAds(List<String> ads) {
        this.ads = ads;
    }

    public List<String> getAbuse() {
        if (abuse == null) {
            abuse = new ArrayList<>();
        }
        return abuse;
    }

    public void setAbuse(List<String> abuse) {
        this.abuse = abuse;
    }

    public List<String> getIllegal() {
        if (illegal == null) {
            illegal = new ArrayList<>();
        }
        return illegal;
    }

    public void setIllegal(List<String> illegal) {
        this.illegal = illegal;
    }
}
