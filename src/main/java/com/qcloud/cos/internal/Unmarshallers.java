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

import java.io.InputStream;
import java.util.List;

import com.qcloud.cos.internal.XmlResponsesSaxParser.CompleteMultipartUploadHandler;
import com.qcloud.cos.internal.XmlResponsesSaxParser.CopyObjectResultHandler;
import com.qcloud.cos.model.AccessControlList;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.BucketCrossOriginConfiguration;
import com.qcloud.cos.model.BucketLifecycleConfiguration;
import com.qcloud.cos.model.BucketReplicationConfiguration;
import com.qcloud.cos.model.BucketVersioningConfiguration;
import com.qcloud.cos.model.InitiateMultipartUploadResult;
import com.qcloud.cos.model.MultipartUploadListing;
import com.qcloud.cos.model.ObjectListing;
import com.qcloud.cos.model.PartListing;
import com.qcloud.cos.model.VersionListing;
import com.qcloud.cos.model.BucketWebsiteConfiguration;
import com.qcloud.cos.model.BucketDomainConfiguration;
import com.qcloud.cos.model.BucketLoggingConfiguration;
import com.qcloud.cos.model.DeleteBucketInventoryConfigurationResult;
import com.qcloud.cos.model.GetBucketInventoryConfigurationResult;
import com.qcloud.cos.model.SetBucketInventoryConfigurationResult;
import com.qcloud.cos.model.ListBucketInventoryConfigurationsResult;
import com.qcloud.cos.model.BucketTaggingConfiguration;
import com.qcloud.cos.model.GetObjectTaggingResult;
import com.qcloud.cos.model.SetObjectTaggingResult;
import com.qcloud.cos.model.DeleteObjectTaggingResult;
import com.qcloud.cos.model.ciModel.bucket.MediaBucketRequest;
import com.qcloud.cos.model.ciModel.bucket.MediaBucketResponse;
import com.qcloud.cos.model.ciModel.job.*;
import com.qcloud.cos.model.ciModel.mediaInfo.MediaInfoRequest;
import com.qcloud.cos.model.ciModel.mediaInfo.MediaInfoResponse;
import com.qcloud.cos.model.ciModel.queue.*;
import com.qcloud.cos.model.ciModel.template.*;
import com.qcloud.cos.model.ciModel.snapshot.*;
import com.qcloud.cos.model.ciModel.workflow.*;

import com.thoughtworks.xstream.XStream;

/*** Collection of unmarshallers for COS XML responses. */

public class Unmarshallers {

    /**
     * Unmarshaller for the ListBuckets XML response.
     */
    public static final class ListBucketsUnmarshaller
            implements Unmarshaller<List<Bucket>, InputStream> {
        public List<Bucket> unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseListMyBucketsResponse(in).getBuckets();
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
            return new XmlResponsesSaxParser().parseBucketDomainConfigurationResponse(in)
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
    public static final class GetObjectTaggingResponseUnmarshaller implements Unmarshaller<GetObjectTaggingResult, InputStream> {

        @Override
        public GetObjectTaggingResult unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseObjectTaggingResponse(in).getResult();
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


    public static final class ListQueueUnmarshaller
            implements Unmarshaller<MediaListQueueResponse, InputStream> {

        public MediaListQueueResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseListQueueResponse(in).getResponse();
        }
    }

    public static final class QueueUnmarshaller
            implements Unmarshaller<MediaQueueResponse, InputStream> {

        public MediaQueueResponse unmarshall(InputStream in) throws Exception {
            XStream xStream = new XStream();
            xStream.autodetectAnnotations(true);
            xStream.alias("Response", MediaQueueResponse.class);
            return (MediaQueueResponse) xStream.fromXML(in);
        }
    }

    public static final class TemplateUnmarshaller
            implements Unmarshaller<MediaTemplateResponse, InputStream> {

        public MediaTemplateResponse unmarshall(InputStream in) throws Exception {
            XStream xStream = new XStream();
            xStream.autodetectAnnotations(true);
            xStream.alias("Response", MediaTemplateResponse.class);
            return (MediaTemplateResponse) xStream.fromXML(in);
        }
    }


    public static final class SnapshotUnmarshaller
            implements Unmarshaller<SnapshotResponse, InputStream> {

        public SnapshotResponse unmarshall(InputStream in) throws Exception {
            XStream xStream = new XStream();
            xStream.autodetectAnnotations(true);
            xStream.alias("Response", SnapshotResponse.class);
            return (SnapshotResponse) xStream.fromXML(in);
        }
    }


    public static final class MediaInfoUnmarshaller
            implements Unmarshaller<MediaInfoResponse, InputStream> {

        public MediaInfoResponse unmarshall(InputStream in) throws Exception {
            XStream xStream = new XStream();
            xStream.autodetectAnnotations(true);
            xStream.alias("Response", MediaInfoResponse.class);
            return (MediaInfoResponse) xStream.fromXML(in);
        }
    }

    public static final class WorkflowExecutionsUnmarshaller
            implements Unmarshaller<MediaWorkflowExecutionsResponse, InputStream> {

        public MediaWorkflowExecutionsResponse unmarshall(InputStream in) throws Exception {
            XStream xStream = new XStream();
            xStream.autodetectAnnotations(true);
            xStream.alias("Response", MediaWorkflowExecutionsResponse.class);
            return (MediaWorkflowExecutionsResponse) xStream.fromXML(in);
        }
    }

    public static final class WorkflowUnmarshaller
            implements Unmarshaller<MediaWorkflowResponse, InputStream> {

        public MediaWorkflowResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseWorkflowResponse(in).getResponse();
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

        public MediaListTemplateResponse unmarshall(InputStream in) {
            XStream xStream = new XStream();
            xStream.autodetectAnnotations(true);
            xStream.alias("Response", MediaListTemplateResponse.class);
            xStream.alias("Template", MediaTemplateObject.class);
            return (MediaListTemplateResponse) xStream.fromXML(in);
        }
    }

    public static final class ListJobUnmarshaller
            implements Unmarshaller<MediaListJobResponse, InputStream> {

        public MediaListJobResponse unmarshall(InputStream in) throws Exception {
            XStream xStream = new XStream();
            xStream.autodetectAnnotations(true);
            xStream.alias("Response", MediaListJobResponse.class);
            return (MediaListJobResponse) xStream.fromXML(in);
        }
    }


    public static final class JobUnmarshaller
            implements Unmarshaller<MediaJobResponse, InputStream> {

        public MediaJobResponse unmarshall(InputStream in) throws Exception {
            XStream xStream = new XStream();
            xStream.autodetectAnnotations(true);
            xStream.alias("Response", MediaJobResponse.class);
            return (MediaJobResponse) xStream.fromXML(in);
        }
    }


    public static final class JobCreatUnmarshaller
            implements Unmarshaller<MediaJobResponse, InputStream> {

        public MediaJobResponse unmarshall(InputStream in) throws Exception {
            XStream xStream = new XStream();
            xStream.autodetectAnnotations(true);
            xStream.alias("Response", MediaJobResponse.class);
            return (MediaJobResponse) xStream.fromXML(in);
        }
    }


    public static final class ListBucketUnmarshaller
            implements Unmarshaller<MediaBucketResponse, InputStream> {

        public MediaBucketResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser()
                    .parseListBucketResponse(in).getResponse();
        }
    }


}
