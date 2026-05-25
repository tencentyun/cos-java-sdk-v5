
package com.qcloud.cos.model.ciModel.persistence;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import java.util.ArrayList;
import java.util.List;

/**
 * 宠物识别响应实体 参数详情参考：https://cloud.tencent.com/document/product/460/95753
 */
@XStreamAlias("Response")
public class DetectPetResponse {
    /**
     * 宠物识别的结果数组，支持返回多个宠物信息
     */
    @XStreamImplicit(itemFieldName = "ResultInfo")
    private List<PetInfo> resultInfo = new ArrayList<>();

    public List<PetInfo> getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(List<PetInfo> resultInfo) {
        this.resultInfo = resultInfo;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DetectPetResponse{");
        sb.append("resultInfo=").append(resultInfo);
        sb.append('}');
        return sb.toString();
    }
}