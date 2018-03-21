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

}
