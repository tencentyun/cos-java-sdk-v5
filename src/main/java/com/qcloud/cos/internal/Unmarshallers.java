/*
 * Copyright 2010-2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.

 * According to cos feature, we modify some class，comment, field name, etc.
 */


package com.qcloud.cos.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.qcloud.cos.internal.XmlResponsesSaxParser.CompleteMultipartUploadHandler;
import com.qcloud.cos.internal.XmlResponsesSaxParser.CopyObjectResultHandler;
import com.qcloud.cos.internal.cihandler.*;
import com.qcloud.cos.model.*;
import com.qcloud.cos.model.IntelligentTiering.BucketIntelligentTieringConfiguration;
import com.qcloud.cos.model.bucketcertificate.BucketGetDomainCertificate;
import com.qcloud.cos.model.ciModel.auditing.AudioAuditingResponse;
import com.qcloud.cos.model.ciModel.auditing.AuditingKeywordResponse;
import com.qcloud.cos.model.ciModel.auditing.AuditingStrategyListResponse;
import com.qcloud.cos.model.ciModel.auditing.AuditingStrategyResponse;
import com.qcloud.cos.model.ciModel.auditing.AuditingTextLibResponse;
import com.qcloud.cos.model.ciModel.auditing.BatchImageAuditingResponse;
import com.qcloud.cos.model.ciModel.auditing.DocumentAuditingResponse;
import com.qcloud.cos.model.ciModel.auditing.ImageAuditingResponse;
import com.qcloud.cos.model.ciModel.auditing.TextAuditingResponse;
import com.qcloud.cos.model.ciModel.auditing.VideoAuditingResponse;
import com.qcloud.cos.model.ciModel.auditing.WebpageAuditingResponse;
import com.qcloud.cos.model.ciModel.bucket.DocBucketResponse;
import com.qcloud.cos.model.ciModel.bucket.MediaBucketResponse;
import com.qcloud.cos.model.ciModel.image.AutoTranslationBlockResponse;
import com.qcloud.cos.model.ciModel.image.DetectFaceResponse;
import com.qcloud.cos.model.ciModel.image.ImageInspectRequest;
import com.qcloud.cos.model.ciModel.image.ImageLabelResponse;
import com.qcloud.cos.model.ciModel.image.ImageLabelV2Response;
import com.qcloud.cos.model.ciModel.image.ImageSearchResponse;
import com.qcloud.cos.model.ciModel.image.ImageStyleResponse;
import com.qcloud.cos.model.ciModel.job.BatchJobResponse;
import com.qcloud.cos.model.ciModel.job.DocJobListResponse;
import com.qcloud.cos.model.ciModel.job.DocJobResponse;
import com.qcloud.cos.model.ciModel.job.FileProcessJobResponse;
import com.qcloud.cos.model.ciModel.job.MediaJobResponse;
import com.qcloud.cos.model.ciModel.job.MediaListJobResponse;
import com.qcloud.cos.model.ciModel.mediaInfo.MediaInfoResponse;
import com.qcloud.cos.model.ciModel.persistence.AIGameRecResponse;
import com.qcloud.cos.model.ciModel.persistence.CIUploadResult;
import com.qcloud.cos.model.ciModel.persistence.DetectCarResponse;
import com.qcloud.cos.model.ciModel.queue.DocListQueueResponse;
import com.qcloud.cos.model.ciModel.queue.MediaListQueueResponse;
import com.qcloud.cos.model.ciModel.queue.MediaQueueResponse;
import com.qcloud.cos.model.ciModel.snapshot.PrivateM3U8Response;
import com.qcloud.cos.model.ciModel.snapshot.SnapshotResponse;
import com.qcloud.cos.model.ciModel.template.MediaListTemplateResponse;
import com.qcloud.cos.model.ciModel.template.MediaTemplateResponse;
import com.qcloud.cos.model.ciModel.workflow.MediaWorkflowExecutionResponse;
import com.qcloud.cos.model.ciModel.workflow.MediaWorkflowExecutionsResponse;
import com.qcloud.cos.model.ciModel.workflow.MediaWorkflowListResponse;
import com.qcloud.cos.model.ciModel.workflow.MediaWorkflowResponse;
import com.qcloud.cos.model.inventory.PostBucketInventoryConfigurationResult;

/*** Collection of unmarshallers for COS XML responses. */

public class Unmarshallers {

    /**
     * Unmarshaller for the ListBuckets XML response.
     */
    public static final class GetServiceUnmarshaller
            implements Unmarshaller<ListBucketsResult, InputStream> {
        public ListBucketsResult unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseGetServiceResponse(in).getResult();
        }
    }

    // /**
    // * Unmarshaller for the ListBuckets XML response, parsing out the owner even if the list is
    // * empty.
    // */
    // public static final class ListBucketsOwnerUnmarshaller
    // implements Unmarshaller<Owner, InputStream> {
    // public Owner unmarshall(InputStream in) throws Exception {
    // return new XmlResponsesSaxParser().parseListMyBucketsResponse(in).getOwner();
    // }
    // }

    /**
     * Unmarshaller for the ListObjects XML response.
     */
    public static final class ListObjectsUnmarshaller
            implements Unmarshaller<ObjectListing, InputStream> {
        private final boolean shouldSDKDecodeResponse;

        public ListObjectsUnmarshaller(final boolean shouldSDKDecodeResponse) {
            this.shouldSDKDecodeResponse = shouldSDKDecodeResponse;
        }

        public ObjectListing unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseListBucketObjectsResponse(in, shouldSDKDecodeResponse).getObjectListing();
        }
    }

    /**
     * Unmarshaller for the ListVersions XML response.
     */
    public static final class VersionListUnmarshaller
            implements Unmarshaller<VersionListing, InputStream> {

        private final boolean shouldSDKDecodeResponse;

        public VersionListUnmarshaller(final boolean shouldSDKDecodeResponse) {
            this.shouldSDKDecodeResponse = shouldSDKDecodeResponse;
        }

        public VersionListing unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseListVersionsResponse(in, shouldSDKDecodeResponse).getListing();
        }
    }

    /**
     * Unmarshaller for the AccessControlList XML response.
     */
    public static final class AccessControlListUnmarshaller
            implements Unmarshaller<AccessControlList, InputStream> {
        public AccessControlList unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseAccessControlListResponse(in)
                    .getAccessControlList();
        }
    }

    /**
     * Unmarshaller for the AccessControlList XML response.
     */
    public static final class ImagePersistenceUnmarshaller
            implements Unmarshaller<ObjectMetadata, InputStream> {
        public ObjectMetadata unmarshall(InputStream in) throws Exception {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            CICommonUnmarshaller<CIUploadResult> unmarshaller = new CICommonUnmarshaller<>(CIUploadResult.class);
            objectMetadata.setCiUploadResult(unmarshaller.unmarshall(in));
            return objectMetadata;
        }
    }
    //
    // /**
    // * Unmarshaller for the BucketLoggingStatus XML response.
    // */
    // public static final class BucketLoggingConfigurationnmarshaller
    // implements Unmarshaller<BucketLoggingConfiguration, InputStream> {
    // public BucketLoggingConfiguration unmarshall(InputStream in) throws Exception {
    // return new XmlResponsesSaxParser().parseLoggingStatusResponse(in)
    // .getBucketLoggingConfiguration();
    // }
    // }
    //
    /**
     * Unmarshaller for the BucketLocation XML response.
     */
    public static final class BucketLocationUnmarshaller
            implements Unmarshaller<String, InputStream> {
        public String unmarshall(InputStream in) throws Exception {
            String location = new XmlResponsesSaxParser().parseBucketLocationResponse(in);

            if (location == null)
                location = "Unknown Region";

            return location;
        }
    }

    /**
     * Unmarshaller for the BucketVersionConfiguration XML response.
     */
    public static final class BucketVersioningConfigurationUnmarshaller
            implements Unmarshaller<BucketVersioningConfiguration, InputStream> {
        public BucketVersioningConfiguration unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseVersioningConfigurationResponse(in)
                    .getConfiguration();
        }
    }

    /**
     * Unmarshaller for the BucketTaggingConfiguration XML response.
     */
    public static final class BucketTaggingConfigurationUnmarshaller implements
            Unmarshaller<BucketTaggingConfiguration, InputStream> {
        public BucketTaggingConfiguration unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseTaggingConfigurationResponse(in).getConfiguration();
        }
    }

    //
    // /**
    // * Unmarshaller for the BucketWebsiteConfiguration XML response.
    // */
    // public static final class BucketWebsiteConfigurationUnmarshaller
    // implements Unmarshaller<BucketWebsiteConfiguration, InputStream> {
    // public BucketWebsiteConfiguration unmarshall(InputStream in) throws Exception {
    // return new XmlResponsesSaxParser().parseWebsiteConfigurationResponse(in)
    // .getConfiguration();
    // }
    // }
    //
    // /**
    // * Unmarshaller for the BucketNotificationConfiguration XML response.
    // */
    // public static final class BucketNotificationConfigurationUnmarshaller
    // implements Unmarshaller<BucketNotificationConfiguration, InputStream> {
    // public BucketNotificationConfiguration unmarshall(InputStream in) throws Exception {
    // return new XmlResponsesSaxParser().parseNotificationConfigurationResponse(in)
    // .getConfiguration();
    // }
    // }
    //
    // /**
    // * Unmarshaller for the BucketTaggingConfiguration XML response.
    // */
    // public static final class BucketTaggingConfigurationUnmarshaller
    // implements Unmarshaller<BucketTaggingConfiguration, InputStream> {
    // public BucketTaggingConfiguration unmarshall(InputStream in) throws Exception {
    // return new XmlResponsesSaxParser().parseTaggingConfigurationResponse(in)
    // .getConfiguration();
    // }
    // }

    /**
     * Unmarshaller for the a direct InputStream response.
     */
    public static final class InputStreamUnmarshaller
            implements Unmarshaller<InputStream, InputStream> {
        public InputStream unmarshall(InputStream in) throws Exception {
            return in;
        }
    }

    /**
     * Unmarshaller for the CopyObject XML response.
     */
    public static final class CopyObjectUnmarshaller
            implements Unmarshaller<CopyObjectResultHandler, InputStream> {
        public CopyObjectResultHandler unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseCopyObjectResponse(in);
        }
    }

    public static final class CompleteMultipartUploadResultUnmarshaller
            implements Unmarshaller<CompleteMultipartUploadHandler, InputStream> {
        public CompleteMultipartUploadHandler unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseCompleteMultipartUploadResponse(in);
        }
    }

    public static final class InitiateMultipartUploadResultUnmarshaller
            implements Unmarshaller<InitiateMultipartUploadResult, InputStream> {
        public InitiateMultipartUploadResult unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseInitiateMultipartUploadResponse(in)
                    .getInitiateMultipartUploadResult();
        }
    }

    public static final class ListMultipartUploadsResultUnmarshaller
            implements Unmarshaller<MultipartUploadListing, InputStream> {
        public MultipartUploadListing unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseListMultipartUploadsResponse(in)
                    .getListMultipartUploadsResult();
        }
    }

    public static final class ListPartsResultUnmarshaller
            implements Unmarshaller<PartListing, InputStream> {
        public PartListing unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseListPartsResponse(in).getListPartsResult();
        }
    }

    public static final class DeleteObjectsResultUnmarshaller
            implements Unmarshaller<DeleteObjectsResponse, InputStream> {

        public DeleteObjectsResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseDeletedObjectsResult(in)
                    .getDeleteObjectResult();
        }
    }

    public static final class BucketLifecycleConfigurationUnmarshaller
            implements Unmarshaller<BucketLifecycleConfiguration, InputStream> {

        public BucketLifecycleConfiguration unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseBucketLifecycleConfigurationResponse(in)
                    .getConfiguration();
        }
    }

    public static final class BucketCrossOriginConfigurationUnmarshaller
            implements Unmarshaller<BucketCrossOriginConfiguration, InputStream> {
        public BucketCrossOriginConfiguration unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseBucketCrossOriginConfigurationResponse(in)
                    .getConfiguration();
        }
    }

    public static final class BucketDomainConfigurationUnmarshaller
            implements Unmarshaller<BucketDomainConfiguration, InputStream> {
        public BucketDomainConfiguration unmarshall(InputStream in) throws Exception {
            if (in.available() == 0) {
                return null;
            }
            return new XmlResponsesSaxParser().parseBucketDomainConfigurationResponse(in)
                    .getConfiguration();
        }
    }

    public static final class BucketDomainCertificateUnmarshaller
            implements Unmarshaller<BucketGetDomainCertificate, InputStream> {
        public BucketGetDomainCertificate unmarshall(InputStream in) throws Exception {
            if (in.available() == 0) {
                return null;
            }
            return new XmlResponsesSaxParser().parseBucketDomainCertificateResponse(in)
                    .getBucketDomainCertificate();
        }
    }

    public static final class BucketRefererConfigurationUnmarshaller
            implements Unmarshaller<BucketRefererConfiguration, InputStream> {
        public BucketRefererConfiguration unmarshall(InputStream in) throws Exception {
            if (in.available() == 0) {
                return null;
            }
            return new XmlResponsesSaxParser().parseBucketRefererConfigurationResponse(in)
                    .getConfiguration();
        }

    }

    /**
     * Unmarshaller for the BucketNotificationConfiguration XML response.
     */
    public static final class BucketReplicationConfigurationUnmarshaller
            implements Unmarshaller<BucketReplicationConfiguration, InputStream> {
        public BucketReplicationConfiguration unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseReplicationConfigurationResponse(in)
                    .getConfiguration();
        }
    }

    /**
     * Unmarshaller for the BucketWebsiteConfiguration XML response.
     */
    public static final class BucketWebsiteConfigurationUnmarshaller implements
            Unmarshaller<BucketWebsiteConfiguration, InputStream> {
        public BucketWebsiteConfiguration unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseWebsiteConfigurationResponse(in).getConfiguration();
        }
    }

    /**
     * Unmarshaller for the BucketLoggingStatus XML response.
     */
    public static final class BucketLoggingConfigurationnmarshaller implements
            Unmarshaller<BucketLoggingConfiguration, InputStream> {
        public BucketLoggingConfiguration unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseLoggingStatusResponse(in).getBucketLoggingConfiguration();
        }
    }

    /**
     * Unmarshaller for the GetBucketInventoryConfiguration XML response.
     */
    public static final class GetBucketInventoryConfigurationUnmarshaller implements
            Unmarshaller<GetBucketInventoryConfigurationResult, InputStream> {

        public GetBucketInventoryConfigurationResult unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseGetBucketInventoryConfigurationResponse(in).getResult();
        }
    }

    /**
     * Unmarshaller for the ListBucketInventoryConfigurations XML response.
     */
    public static final class ListBucketInventoryConfigurationsUnmarshaller implements
            Unmarshaller<ListBucketInventoryConfigurationsResult, InputStream> {

        public ListBucketInventoryConfigurationsResult unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseBucketListInventoryConfigurationsResponse(in).getResult();
        }
    }

    /**
     * Unmarshaller for the DeleteBucketInventoryConfiguration XML response.
     */
    public static final class DeleteBucketInventoryConfigurationUnmarshaller implements
            Unmarshaller<DeleteBucketInventoryConfigurationResult, InputStream> {

        public DeleteBucketInventoryConfigurationResult unmarshall(InputStream in) throws Exception {
            return new DeleteBucketInventoryConfigurationResult();
        }
    }

    /**
     * Unmarshaller for the SetBucketInventoryConfiguration XML response.
     */
    public static final class SetBucketInventoryConfigurationUnmarshaller implements
            Unmarshaller<SetBucketInventoryConfigurationResult, InputStream> {

        public SetBucketInventoryConfigurationResult unmarshall(InputStream in) throws Exception {
            return new SetBucketInventoryConfigurationResult();
        }
    }

    public static final class PostBucketInventoryConfigurationUnmarshaller implements
            Unmarshaller<PostBucketInventoryConfigurationResult, InputStream> {

        public PostBucketInventoryConfigurationResult unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parsePostBucketInventoryConfigurationsResponse(in).getResult();
        }
    }

    public static final class GetObjectTaggingResponseUnmarshaller implements Unmarshaller<GetObjectTaggingResult, InputStream> {

        @Override
        public GetObjectTaggingResult unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseObjectTaggingResponse(in).getResult();
        }
    }

    /**
     * Unmarshaller for the ListBucketInventoryConfigurations XML response.
     */
    public static final class GetBucketIntelligentTierConfigurationsUnmarshaller implements
            Unmarshaller<BucketIntelligentTierConfiguration, InputStream> {

        public BucketIntelligentTierConfiguration unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseBucketIntelligentTierConfigurationsResponse(in).getConfiguration();
        }
    }

    public static final class ListBucketTieringConfigurationUnmarshaller
            implements Unmarshaller<List<BucketIntelligentTieringConfiguration>, InputStream> {

        public List<BucketIntelligentTieringConfiguration> unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseListBucketIntelligentTierConfigurationsResponse(in).getConfigurations();
        }
    }

    public static final class SetObjectTaggingResponseUnmarshaller implements Unmarshaller<SetObjectTaggingResult, InputStream> {

        @Override
        public SetObjectTaggingResult unmarshall(InputStream in) throws Exception {
            return new SetObjectTaggingResult();
        }
    }

    public static final class DeleteObjectTaggingResponseUnmarshaller implements Unmarshaller<DeleteObjectTaggingResult, InputStream> {

        @Override
        public DeleteObjectTaggingResult unmarshall(InputStream in) throws Exception {
            return new DeleteObjectTaggingResult();
        }
    }

    /**
     * Unmarshaller for the BucketEncryption XML response.
     */
    public static final class BucketEncryptionConfigurationUnmarshaller implements
            Unmarshaller<BucketEncryptionConfiguration, InputStream> {
        public BucketEncryptionConfiguration unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseBucketEncryptionResponse(in).getBucketEncryptionConfiguration();
        }
    }

    /**
     * Unmarshaller for the BucketObjectLock XML response.
     */
    public static final class BucketObjectLockConfigurationUnmarshaller implements
            Unmarshaller<BucketObjectLockConfiguration, InputStream> {
        public BucketObjectLockConfiguration unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseBucketObjectLockConfigurationResponse(in).getBucketObjectLockConfiguration();
        }
    }

    public static final class ListQueueUnmarshaller
            implements Unmarshaller<MediaListQueueResponse, InputStream> {

        public MediaListQueueResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseListQueueResponse(in).getResponse();
        }
    }

    public static final class DocListQueueUnmarshaller
            implements Unmarshaller<DocListQueueResponse, InputStream> {

        public DocListQueueResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseDocListQueueResponse(in).getResponse();
        }
    }

    public static final class QueueUnmarshaller
            implements Unmarshaller<MediaQueueResponse, InputStream> {

        public MediaQueueResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseUpdateMediaQueueResponse(in).getResponse();
        }
    }

    public static final class TemplateUnmarshaller
            implements Unmarshaller<MediaTemplateResponse, InputStream> {

        public MediaTemplateResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseMediaTemplateResponse(in).getResponse();
        }
    }


    public static final class SnapshotUnmarshaller
            implements Unmarshaller<SnapshotResponse, InputStream> {

        public SnapshotResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseSnapshotResponse(in).getResponse();
        }
    }


    public static final class MediaInfoUnmarshaller
            implements Unmarshaller<MediaInfoResponse, InputStream> {

        public MediaInfoResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseGenerateMediainfoResponse(in).getResponse();
        }
    }

    public static final class WorkflowExecutionsUnmarshaller
            implements Unmarshaller<MediaWorkflowExecutionsResponse, InputStream> {

        public MediaWorkflowExecutionsResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseMediaWorkflowExecutionsResponse(in).getResponse();
        }
    }

    public static final class WorkflowListUnmarshaller
            implements Unmarshaller<MediaWorkflowListResponse, InputStream> {

        public MediaWorkflowListResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseWorkflowListResponse(in).getResponse();
        }
    }


    public static final class WorkflowExecutionUnmarshaller
            implements Unmarshaller<MediaWorkflowExecutionResponse, InputStream> {

        public MediaWorkflowExecutionResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseWorkflowExecutionResponse(in).getResponse();
        }
    }


    public static final class ListTemplateUnmarshaller
            implements Unmarshaller<MediaListTemplateResponse, InputStream> {

        public MediaListTemplateResponse unmarshall(InputStream in) throws IOException {
            return new XmlResponsesSaxParser().parseDescribeMediaTemplatesResponse(in).getResponse();
        }
    }

    public static final class ListJobUnmarshaller
            implements Unmarshaller<MediaListJobResponse, InputStream> {

        public MediaListJobResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseMediaJobsRespones(in).getResponse();
        }
    }


    public static final class JobUnmarshaller
            implements Unmarshaller<MediaJobResponse, InputStream> {

        public MediaJobResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseMediaJobRespones(in).getResponse();
        }
    }


    public static final class JobCreatUnmarshaller
            implements Unmarshaller<MediaJobResponse, InputStream> {

        public MediaJobResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseJobCreatResponse(in).getResponse();
        }
    }

    public static final class BatchJobUnmarshaller
            implements Unmarshaller<BatchJobResponse, InputStream> {

        public BatchJobResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseBatchJobResponse(in).getResponse();
        }
    }


    public static final class ListBucketUnmarshaller
            implements Unmarshaller<MediaBucketResponse, InputStream> {

        public MediaBucketResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseListBucketResponse(in).getResponse();
        }
    }

    public static final class DocListBucketUnmarshaller
            implements Unmarshaller<DocBucketResponse, InputStream> {

        public DocBucketResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseDocListBucketResponse(in).getResponse();
        }
    }

    public static final class WorkflowUnmarshaller
            implements Unmarshaller<MediaWorkflowResponse, InputStream> {

        public MediaWorkflowResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseWorkflowResponse(in).getResponse();
        }
    }

    public static final class DocProcessJobUnmarshaller
            implements Unmarshaller<DocJobResponse, InputStream> {

        public DocJobResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseDocJobResponse(in).getResponse();
        }
    }

    public static final class DescribeDocJobUnmarshaller
            implements Unmarshaller<DocJobResponse, InputStream> {

        public DocJobResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseDescribeDocJobResponse(in).getResponse();
        }
    }

    public static final class DescribeDocJobsUnmarshaller
            implements Unmarshaller<DocJobListResponse, InputStream> {

        public DocJobListResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseDocJobListResponse(in).getResponse();
        }
    }

    public static final class ImageAuditingUnmarshaller
            implements Unmarshaller<ImageAuditingResponse, InputStream> {

        public ImageAuditingResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseImageAuditingResponse(in).getResponse();
        }
    }

    public static final class VideoAuditingUnmarshaller
            implements Unmarshaller<VideoAuditingResponse, InputStream> {

        public VideoAuditingResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseVideoAuditingJobResponse(in).getResponse();
        }
    }

    public static final class VideoAuditingJobUnmarshaller
            implements Unmarshaller<VideoAuditingResponse, InputStream> {

        public VideoAuditingResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseDescribeVideoAuditingJobResponse(in).getResponse();
        }
    }

    public static final class AudioAuditingUnmarshaller
            implements Unmarshaller<AudioAuditingResponse, InputStream> {

        public AudioAuditingResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseAudioAuditingJobResponse(in).getResponse();
        }
    }

    public static final class AudioAuditingJobUnmarshaller
            implements Unmarshaller<AudioAuditingResponse, InputStream> {

        public AudioAuditingResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseDescribeAudioAuditingJobResponse(in).getResponse();
        }
    }

    public static final class ImageLabelUnmarshaller
            implements Unmarshaller<ImageLabelResponse, InputStream> {

        public ImageLabelResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseImageLabelResponse(in).getResponse();
        }
    }

    public static final class ImageLabelV2Unmarshaller
            implements Unmarshaller<ImageLabelV2Response, InputStream> {

        public ImageLabelV2Response unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseImageLabelV2Response(in).getResponse();
        }
    }

    public static final class TextAuditingJobUnmarshaller
            implements Unmarshaller<TextAuditingResponse, InputStream> {

        public TextAuditingResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseTextAuditingResponse(in).getResponse();
        }
    }

    public static final class TextAuditingDescribeJobUnmarshaller
            implements Unmarshaller<TextAuditingResponse, InputStream> {

        public TextAuditingResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseTextAuditingDescribeResponse(in).getResponse();
        }
    }

    public static final class DocumentAuditingJobUnmarshaller
            implements Unmarshaller<DocumentAuditingResponse, InputStream> {

        public DocumentAuditingResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseDocumentAuditingResponse(in).getResponse();
        }
    }

    public static final class DocumentAuditingDescribeJobUnmarshaller
            implements Unmarshaller<DocumentAuditingResponse, InputStream> {

        public DocumentAuditingResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseDocumentAuditingDescribeResponse(in).getResponse();
        }
    }

    public static final class BatchImageAuditingJobUnmarshaller
            implements Unmarshaller<BatchImageAuditingResponse, InputStream> {

        public BatchImageAuditingResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseBatchImageAuditingResponse(in).getResponse();
        }
    }

    public static class WebpageAuditingJobUnmarshaller implements Unmarshaller<WebpageAuditingResponse, InputStream> {

        public WebpageAuditingResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseWebpageAuditingJobResponse(in).getResponse();
        }
    }

    public static final class WebpageAuditingDescribeJobUnmarshaller
            implements Unmarshaller<WebpageAuditingResponse, InputStream> {

        public WebpageAuditingResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseDWebpageAuditingDescribeResponse(in).getResponse();
        }
    }

    public static final class ImageAuditingDescribeJobUnmarshaller
            implements Unmarshaller<ImageAuditingResponse, InputStream> {

        public ImageAuditingResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseImageAuditingDescribeResponse(in).getResponse();
        }
    }

    public static final class PrivateM3U8Unmarshaller
            implements Unmarshaller<PrivateM3U8Response, InputStream> {
        public PrivateM3U8Response unmarshall(InputStream in) throws Exception {
            PrivateM3U8Response privateM3U8Response = new PrivateM3U8Response();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = reader.readLine()) != null) {
                sb.append(line + NL);
            }
            privateM3U8Response.setM3u8(sb.toString());
            if (privateM3U8Response.getM3u8() == null)
                privateM3U8Response.setM3u8("Unknown Error");

            return privateM3U8Response;
        }
    }

    public static final class DetectCarUnmarshaller
            implements Unmarshaller<DetectCarResponse, InputStream> {

        public DetectCarResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseDetectCarResponse(in).getResponse();
        }
    }

    public static final class SearchImagesUnmarshaller
            implements Unmarshaller<ImageSearchResponse, InputStream> {

        public ImageSearchResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseSearchImagesResponse(in).getResponse();
        }
    }

    public static final class triggerWorkflowListUnmarshaller
            implements Unmarshaller<MediaWorkflowListResponse, InputStream> {

        public MediaWorkflowListResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parsetriggerWorkflowListResponse(in).getResponse();
        }
    }

    public static final class GenerateQrcodeUnmarshaller
            implements Unmarshaller<String, InputStream> {

        public String unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseGenerateQrcodeResponse(in).getResponse();
        }
    }

    public static final class getImageStyleUnmarshaller
            implements Unmarshaller<ImageStyleResponse, InputStream> {

        public ImageStyleResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseGetImageStyleResponse(in).getResponse();
        }
    }

    public static final class DecompressionResultUnmarshaller
        implements Unmarshaller<DecompressionResult, InputStream> {

        public DecompressionResult unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseDecompressionResult(in)
                .getDecompressionResult();
        }
    }

    public static final class ListJobsResultUnmarshaller
            implements Unmarshaller<ListJobsResult, InputStream> {

        public ListJobsResult unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseListJobsResult(in).getResult();
        }
    }

    public static final class ReportBadCaseUnmarshaller
            implements Unmarshaller<String, InputStream> {

        public String unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseReportBadCase(in).getResponse();
        }
    }

    public static final class FileProcessUnmarshaller
            implements Unmarshaller<FileProcessJobResponse, InputStream> {

        public FileProcessJobResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseFileProcessResponse(in).getResponse();
        }
    }

    public static final class AutoTranslationBlockUnmarshaller
            implements Unmarshaller<AutoTranslationBlockResponse, InputStream> {

        public AutoTranslationBlockResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseAutoTranslationBlockResponse(in).getResponse();
        }
    }

    public static final class DetectFaceUnmarshaller
            implements Unmarshaller<DetectFaceResponse, InputStream> {

        public DetectFaceResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseDetectFaceResponse(in).getResponse();
        }
    }

    public static final class AIGameRecUnmarshaller
            implements Unmarshaller<AIGameRecResponse, InputStream> {

        public AIGameRecResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseAIGameRecResponse(in).getResponse();
        }
    }

    public static final class AuditingStrategyUnmarshaller
            implements Unmarshaller<AuditingStrategyResponse, InputStream> {

        public AuditingStrategyResponse unmarshall(InputStream in) {
            return new AuditingStrategyHandler().getResponse(in);
        }
    }
    public static final class AuditingStrategyListUnmarshaller
            implements Unmarshaller<AuditingStrategyListResponse, InputStream> {

        public AuditingStrategyListResponse unmarshall(InputStream in) {
            return new AuditingStrategyHandler().getResponseList(in);
        }
    }
    public static final class AuditingTextLibUnmarshaller
            implements Unmarshaller<AuditingTextLibResponse, InputStream> {

        public AuditingTextLibResponse unmarshall(InputStream in) {
            return new AuditingTextLibHandler().getResponse(in);
        }
    }

    public static final class CICommonUnmarshaller<T> implements Unmarshaller<T, InputStream> {
        private Class<T> tClass;

        public CICommonUnmarshaller(Class<T> cls) {
            this.tClass = cls;
        }

        public T unmarshall(InputStream in) {
            return new CICommonHandler<T>().getResponse(in,tClass);
        }
    }

    public static final class CICommonJsonUnmarshaller<T> implements Unmarshaller<T, InputStream> {
        private Class<T> tClass;

        public CICommonJsonUnmarshaller(Class<T> cls) {
            this.tClass = cls;
        }

        public T unmarshall(InputStream in) throws IOException {
            return new CICommonJsonResponseHandler<T>().getResponse(in,tClass);
        }
    }

    public static class CIJsonUnmarshaller<T> implements Unmarshaller<T, InputStream>{
        private Class<T> tClass;
        public CIJsonUnmarshaller(Class<T> aClass) {
            this.tClass = aClass;
        }
        @Override
        public T unmarshall(InputStream in) throws Exception {
            return new CIJsonHandler<T>().getResponse(in,tClass);
        }
    }
}
