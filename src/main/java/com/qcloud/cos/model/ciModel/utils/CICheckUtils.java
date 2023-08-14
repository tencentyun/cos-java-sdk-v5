package com.qcloud.cos.model.ciModel.utils;

import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.ciModel.persistence.PicOperations;

public class CICheckUtils {

    /**
     * 校验是否为上传覆盖原图请求
     *
     * @param putObjectRequest 上传内容请求
     */
    public static boolean isCoverImageRequest(PutObjectRequest putObjectRequest) {
        PicOperations picOperations = putObjectRequest.getPicOperations();
        if (picOperations != null && picOperations.getRules() != null) {
            for (PicOperations.Rule rule : picOperations.getRules()) {
                return true;
            }
        }
        return false;
    }

}
