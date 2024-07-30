package com.qcloud.cos.model.ciModel.persistence;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

public class PlateDetailInfos {
    @XStreamImplicit(itemFieldName = "PlateLocation")
    private List<PlateDetail> plateDetailInfos;

    public List<PlateDetail> getPlateDetailInfos() {
        return plateDetailInfos;
    }

    public void setPlateDetailInfos(List<PlateDetail> plateDetailInfos) {
        this.plateDetailInfos = plateDetailInfos;
    }
}
