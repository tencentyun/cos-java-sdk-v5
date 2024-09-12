package com.qcloud.cos.model;

import com.qcloud.cos.model.IntelligentTiering.BucketIntelligentTieringConfiguration;

import java.util.List;

public class BucketGetMetadataResult {
    public String bucketUrl;

    public String bucketName;

    public String location;

    public boolean isMaz = false;

    public boolean isOFS = false;

    public BucketEncryptionConfiguration encryptionConfiguration;

    public AccessControlList accessControlList;

    public BucketWebsiteConfiguration websiteConfiguration;

    public BucketLoggingConfiguration loggingConfiguration;

    public BucketCrossOriginConfiguration crossOriginConfiguration;

    public BucketVersioningConfiguration versioningConfiguration;

    public BucketLifecycleConfiguration lifecycleConfiguration;

    public BucketTaggingConfiguration taggingConfiguration;

    public BucketReplicationConfiguration replicationConfiguration;

    public List<BucketIntelligentTieringConfiguration> tieringConfigurations;

    public BucketObjectLockConfiguration bucketObjectLockConfiguration;
}
