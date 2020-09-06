package com.qcloud.cos.model.ciModel.bucket;

import com.qcloud.cos.model.ciModel.common.MediaCommonResponse;

import java.util.ArrayList;
import java.util.List;

public class MediaBucketResponse extends MediaCommonResponse {
    private List<MediaBucketObject> MediaBucketList;

    public MediaBucketResponse() {
        MediaBucketList = new ArrayList<>();
    }

    public List<MediaBucketObject> getMediaBucketList() {
        return MediaBucketList;
    }

    public void setMediaBucketList(List<MediaBucketObject> mediaBucketList) {
        MediaBucketList = mediaBucketList;
    }

    @Override
    public String toString() {
        return "MediaBucketResponse{" +
                "MediaBucketList=" + MediaBucketList +
                '}';
    }
}
