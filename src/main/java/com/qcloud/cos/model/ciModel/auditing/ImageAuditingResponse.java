package com.qcloud.cos.model.ciModel.auditing;

import com.qcloud.cos.model.CosServiceResult;

/**
 * 图片审核响应实体 参数详情参考：https://cloud.tencent.com/document/product/460/37318
 */
public class ImageAuditingResponse extends CosServiceResult {
    private PornInfo pornInfo;
    private TerroristInfo terroristInfo;
    private PoliticsInfo politicsInfo;
    private AdsInfo adsInfo;
    private UserInfo userInfo = new UserInfo();

    public PornInfo getPornInfo() {
        if (pornInfo == null) {
            pornInfo = new PornInfo();
        }
        return pornInfo;
    }

    public void setPornInfo(PornInfo pornInfo) {
        this.pornInfo = pornInfo;
    }

    public TerroristInfo getTerroristInfo() {
        if (terroristInfo == null) {
            terroristInfo = new TerroristInfo();
        }
        return terroristInfo;
    }

    public void setTerroristInfo(TerroristInfo terroristInfo) {
        this.terroristInfo = terroristInfo;
    }

    public PoliticsInfo getPoliticsInfo() {
        if (politicsInfo == null) {
            politicsInfo = new PoliticsInfo();
        }
        return politicsInfo;
    }

    public void setPoliticsInfo(PoliticsInfo politicsInfo) {
        this.politicsInfo = politicsInfo;
    }

    public AdsInfo getAdsInfo() {
        if (adsInfo == null) {
            adsInfo = new AdsInfo();
        }
        return adsInfo;
    }

    public void setAdsInfo(AdsInfo adsInfo) {
        this.adsInfo = adsInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String toString() {
        return "ImageAuditingResponse{" +
                "pornInfo=" + pornInfo +
                ", terroristInfo=" + terroristInfo +
                ", politicsInfo=" + politicsInfo +
                ", adsInfo=" + adsInfo +
                ", userInfo=" + userInfo +
                '}';
    }
}
