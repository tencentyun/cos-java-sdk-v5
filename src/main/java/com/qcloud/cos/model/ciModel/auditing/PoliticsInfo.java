package com.qcloud.cos.model.ciModel.auditing;

/**
 * 鉴政治敏感审核信息
 */
public class PoliticsInfo extends AudtingCommonInfo {
    private PoliticsInfoObjectResults politicsInfoObjectResults = new PoliticsInfoObjectResults();

    public PoliticsInfoObjectResults getPoliticsInfoObjectResults() {
        return politicsInfoObjectResults;
    }

    public void setPoliticsInfoObjectResults(PoliticsInfoObjectResults politicsInfoObjectResults) {
        this.politicsInfoObjectResults = politicsInfoObjectResults;
    }
}
