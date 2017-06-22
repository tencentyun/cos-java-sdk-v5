package com.qcloud.cos;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.AbortMultipartUploadRequest;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.CompleteMultipartUploadRequest;
import com.qcloud.cos.model.CompleteMultipartUploadResult;
import com.qcloud.cos.model.CopyObjectRequest;
import com.qcloud.cos.model.CopyObjectResult;
import com.qcloud.cos.model.CreateBucketRequest;
import com.qcloud.cos.model.DeleteBucketRequest;
import com.qcloud.cos.model.DeleteObjectRequest;
import com.qcloud.cos.model.GetObjectMetadataRequest;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.InitiateMultipartUploadRequest;
import com.qcloud.cos.model.InitiateMultipartUploadResult;
import com.qcloud.cos.model.ListMultipartUploadsRequest;
import com.qcloud.cos.model.ListNextBatchOfObjectsRequest;
import com.qcloud.cos.model.ListObjectsRequest;
import com.qcloud.cos.model.ListPartsRequest;
import com.qcloud.cos.model.MultipartUploadListing;
import com.qcloud.cos.model.ObjectListing;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PartListing;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.UploadPartRequest;
import com.qcloud.cos.model.UploadPartResult;

public interface COS {

    /**
     * <p>
     * Uploads a new object to the specified Qcloud COS bucket. The <code>PutObjectRequest</code>
     * contains all the details of the request, including the bucket to upload to, the key the
     * object will be uploaded under, and the file or input stream containing the data to upload.
     * </p>
     * <p>
     * Qcloud COS never stores partial objects; if during this call an exception wasn't thrown, the
     * entire object was stored.
     * </p>
     * <p>
     * Depending on whether a file or input stream is being uploaded, this method has slightly
     * different behavior.
     * </p>
     * <p>
     * When uploading a file:
     * </p>
     * <ul>
     * <li>The client automatically computes a checksum of the file. Qcloud COS uses checksums to
     * validate the data in each file.</li>
     * <li>Using the file extension, Qcloud COS attempts to determine the correct content type and
     * content disposition to use for the object.</li>
     * </ul>
     * <p>
     * When uploading directly from an input stream:
     * </p>
     * <ul>
     * <li>Be careful to set the correct content type in the metadata object before directly sending
     * a stream. Unlike file uploads, content types from input streams cannot be automatically
     * determined. If the caller doesn't explicitly set the content type, it will not be set in
     * Qcloud COS.</li>
     * <li>Content length <b>must</b> be specified before data can be uploaded to Qcloud COS. Qcloud
     * COS explicitly requires that the content length be sent in the request headers before it will
     * accept any of the data. If the caller doesn't provide the length, the library must buffer the
     * contents of the input stream in order to calculate it.
     * </ul>
     *
     * <p>
     * The specified bucket must already exist and the caller must have {@link Permission#Write}
     * permission to the bucket to upload an object.
     * </p>
     *
     * @param putObjectRequest The request object containing all the parameters to upload a new
     *        object to Qcloud COS.
     *
     * @return A {@link PutObjectResult} object containing the information returned by Qcloud COS
     *         for the newly created object.
     *
     * @throws CosClientException If any errors are encountered in the client while making the
     *         request or handling the response.
     * @throws CosServiceException If any errors occurred in Qcloud COS while processing the
     *         request.
     *
     * @see COS#putObject(String, String, File)
     * @see COS#putObject(String, String, InputStream, ObjectMetadata)
     */
    public PutObjectResult putObject(PutObjectRequest putObjectRequest)
            throws CosClientException, CosServiceException;

    /**
     * <p>
     * Uploads the specified file to Qcloud COS under the specified bucket and key name.
     * </p>
     * <p>
     * Qcloud COS never stores partial objects; if during this call an exception wasn't thrown, the
     * entire object was stored.
     * </p>
     * <p>
     * The client automatically computes a checksum of the file. Qcloud COS uses checksums to
     * validate the data in each file.
     * </p>
     * <p>
     * Using the file extension, Qcloud COS attempts to determine the correct content type and
     * content disposition to use for the object.
     * </p>
     *
     * <p>
     * The specified bucket must already exist and the caller must have {@link Permission#Write}
     * permission to the bucket to upload an object.
     * </p>
     *
     * @param bucketName The name of an existing bucket, to which you have {@link Permission#Write}
     *        permission.
     * @param key The key under which to store the specified file.
     * @param file The file containing the data to be uploaded to Qcloud COS.
     * 
     * @return A {@link PutObjectResult} object containing the information returned by Qcloud COS
     *         for the newly created object.
     *
     * @throws CosClientException If any errors are encountered in the client while making the
     *         request or handling the response.
     * @throws CosServiceException If any errors occurred in Qcloud COS while processing the
     *         request.
     *
     * @see COS#putObject(PutObjectRequest)
     * @see COS#putObject(String, String, InputStream, ObjectMetadata)
     */
    public PutObjectResult putObject(String bucketName, String key, File file)
            throws CosClientException, CosServiceException;


    /**
     * <p>
     * Uploads the specified file to Qcloud COS under the specified bucket and key name.
     * </p>
     * <p>
     * Qcloud COS never stores partial objects; if during this call an exception wasn't thrown, the
     * entire object was stored.
     * </p>
     * <p>
     * When uploading directly from an input stream:
     * </p>
     * <ul>
     * <li>Be careful to set the correct content type in the metadata object before directly sending
     * a stream. Unlike file uploads, content types from input streams cannot be automatically
     * determined. If the caller doesn't explicitly set the content type, it will not be set in
     * Qcloud COS.</li>
     * <li>Content length <b>must</b> be specified before data can be uploaded to Qcloud COS. Qcloud
     * COS explicitly requires that the content length be sent in the request headers before it will
     * accept any of the data. If the caller doesn't provide the length, the library must buffer the
     * contents of the input stream in order to calculate it.
     * </ul>
     *
     * <p>
     * The specified bucket must already exist and the caller must have {@link Permission#Write}
     * permission to the bucket to upload an object.
     * </p>
     *
     * @param bucketName The name of an existing bucket, to which you have {@link Permission#Write}
     *        permission.
     * @param key The key under which to store the specified file.
     * @param input The input stream containing the data to be uploaded to Qcloud COS.
     * @param metadata Additional metadata instructing Qcloud COS how to handle the uploaded data
     *        (e.g. custom user metadata, hooks for specifying content type, etc.).
     *
     * @return A {@link PutObjectResult} object containing the information returned by Qcloud COS
     *         for the newly created object.
     *
     * @throws CosClientException If any errors are encountered in the client while making the
     *         request or handling the response.
     * @throws CosServiceException If any errors occurred in Qcloud COS while processing the
     *         request.
     *
     * @see COS#putObject(PutObjectRequest)
     * @see COS#putObject(String, String, File)
     */
    public PutObjectResult putObject(String bucketName, String key, InputStream input,
            ObjectMetadata metadata) throws CosClientException, CosServiceException;


    /**
     * <p>
     * Gets the object stored in Qcloud COS under the specified bucket and key.
     * </p>
     * <p>
     * Be extremely careful when using this method; the returned COS object contains a direct stream
     * of data from the HTTP connection. The underlying HTTP connection cannot be closed until the
     * user finishes reading the data and closes the stream. Therefore:
     * </p>
     * <ul>
     * <li>Use the data from the input stream in Qcloud COS object as soon as possible</li>
     * <li>Close the input stream in Qcloud COS object as soon as possible</li>
     * </ul>
     * If these rules are not followed, the client can run out of resources by allocating too many
     * open, but unused, HTTP connections.
     * </p>
     * <p>
     * To get an object from Qcloud COS, the caller must have {@link Permission#Read} access to the
     * object.
     * </p>
     * <p>
     * If the object fetched is publicly readable, it can also read it by pasting its URL into a
     * browser.
     * </p>
     * <p>
     * For more advanced options (such as downloading only a range of an object's content, or
     * placing constraints on when the object should be downloaded) callers can use
     * {@link #getObject(GetObjectRequest)}.
     * </p>
     *
     * @param bucketName The name of the bucket containing the desired object.
     * @param key The key under which the desired object is stored.
     *
     * @return The object stored in Qcloud COS in the specified bucket and key.
     *
     * @throws CosClientException If any errors are encountered in the client while making the
     *         request or handling the response.
     * @throws CosServiceException If any errors occurred in Qcloud COS while processing the
     *         request.
     *
     * @see COS#getObject(GetObjectRequest)
     * @see COS#getObject(GetObjectRequest, File)
     */
    public COSObject getObject(String bucketName, String key)
            throws CosClientException, CosServiceException;

    /**
     * <p>
     * Gets the object stored in Qcloud COS under the specified bucket and key. Returns
     * <code>null</code> if the specified constraints weren't met.
     * </p>
     * <p>
     * Be extremely careful when using this method; the returned COS object contains a direct stream
     * of data from the HTTP connection. The underlying HTTP connection cannot be closed until the
     * user finishes reading the data and closes the stream. Therefore:
     * </p>
     * <ul>
     * <li>Use the data from the input stream in Qcloud COS object as soon as possible,</li>
     * <li>Close the input stream in Qcloud COS object as soon as possible.</li>
     * </ul>
     * <p>
     * If callers do not follow those rules, then the client can run out of resources if allocating
     * too many open, but unused, HTTP connections.
     * </p>
     * <p>
     * To get an object from Qcloud COS, the caller must have {@link Permission#Read} access to the
     * object.
     * </p>
     * <p>
     * If the object fetched is publicly readable, it can also read it by pasting its URL into a
     * browser.
     * </p>
     * <p>
     * When specifying constraints in the request object, the client needs to be prepared to handle
     * this method returning <code>null</code> if the provided constraints aren't met when Qcloud
     * COS receives the request.
     * </p>
     * <p>
     * If the advanced options provided in {@link GetObjectRequest} aren't needed, use the simpler
     * {@link COS#getObject(String bucketName, String key)} method.
     * </p>
     *
     * @param getObjectRequest The request object containing all the options on how to download the
     *        object.
     *
     * @return The object stored in Qcloud COS in the specified bucket and key. Returns
     *         <code>null</code> if constraints were specified but not met.
     *
     * @throws CosClientException If any errors are encountered in the client while making the
     *         request or handling the response.
     * @throws CosServiceException If any errors occurred in Qcloud COS while processing the
     *         request.
     * @see COS#getObject(String, String)
     * @see COS#getObject(GetObjectRequest, File)
     */
    public COSObject getObject(GetObjectRequest getObjectRequest)
            throws CosClientException, CosServiceException;


    /**
     * <p>
     * Gets the object metadata for the object stored in Qcloud COS under the specified bucket and
     * key, and saves the object contents to the specified file. Returns <code>null</code> if the
     * specified constraints weren't met.
     * </p>
     * <p>
     * Instead of using {@link COS#getObject(GetObjectRequest)}, use this method to ensure that the
     * underlying HTTP stream resources are automatically closed as soon as possible. The Qcloud COS
     * clients handles immediate storage of the object contents to the specified file.
     * </p>
     * <p>
     * To get an object from Qcloud COS, the caller must have {@link Permission#Read} access to the
     * object.
     * </p>
     * <p>
     * If the object fetched is publicly readable, it can also read it by pasting its URL into a
     * browser.
     * </p>
     * <p>
     * When specifying constraints in the request object, the client needs to be prepared to handle
     * this method returning <code>null</code> if the provided constraints aren't met when Qcloud
     * COS receives the request.
     * </p>
     * 
     * @param getObjectRequest The request object containing all the options on how to download the
     *        Qcloud COS object content.
     * @param destinationFile Indicates the file (which might already exist) where to save the
     *        object content being downloading from Qcloud COS.
     *
     * @return All COS object metadata for the specified object. Returns <code>null</code> if
     *         constraints were specified but not met.
     *
     * @throws CosClientException If any errors are encountered in the client while making the
     *         request, handling the response, or writing the incoming data from COS to the
     *         specified destination file.
     * @throws CosServiceException If any errors occurred in Qcloud COS while processing the
     *         request.
     *
     * @see COS#getObject(String, String)
     * @see COS#getObject(GetObjectRequest)
     */
    public ObjectMetadata getObject(GetObjectRequest getObjectRequest, File destinationFile)
            throws CosClientException, CosServiceException;

    /**
     * <p>
     * Gets the metadata for the specified Qcloud COS object without actually fetching the object
     * itself. This is useful in obtaining only the object metadata, and avoids wasting bandwidth on
     * fetching the object data.
     * </p>
     * <p>
     * The object metadata contains information such as content type, content disposition, etc., as
     * well as custom user metadata that can be associated with an object in Qcloud COS.
     * </p>
     *
     * @param bucketName bucket name
     * @param key cos path
     *
     * @return All COS object metadata for the specified object.
     *
     * @throws CosClientException If any errors are encountered on the client while making the
     *         request or handling the response.
     * @throws CosServiceException If any errors occurred in Qcloud COS while processing the
     *         request.
     *
     */
    public ObjectMetadata getobjectMetadata(String bucketName, String key)
            throws CosClientException, CosServiceException;

    /**
     * <p>
     * Gets the metadata for the specified Qcloud COS object without actually fetching the object
     * itself. This is useful in obtaining only the object metadata, and avoids wasting bandwidth on
     * fetching the object data.
     * </p>
     * <p>
     * The object metadata contains information such as content type, content disposition, etc., as
     * well as custom user metadata that can be associated with an object in Qcloud COS.
     * </p>
     *
     * @param getObjectMetadataRequest The request object specifying the bucket, key and optional
     *        version ID of the object whose metadata is being retrieved.
     *
     * @return All COS object metadata for the specified object.
     *
     * @throws CosClientException If any errors are encountered on the client while making the
     *         request or handling the response.
     * @throws CosServiceException If any errors occurred in Qcloud COS while processing the
     *         request.
     *
     * @see COS#getObjectMetadata(String, String)
     */
    public ObjectMetadata getObjectMetadata(GetObjectMetadataRequest getObjectMetadataRequest)
            throws CosClientException, CosServiceException;

    /**
     * <p>
     * Deletes the specified object in the specified bucket. Once deleted, the object can only be
     * restored if versioning was enabled when the object was deleted.
     * </p>
     * <p>
     * If attempting to delete an object that does not exist, Qcloud COS will return a success
     * message instead of an error message.
     * </p>
     *
     * @param deleteObjectRequest The request object containing all options for deleting an Qcloud
     *        COS object.
     *
     * @throws CosClientException If any errors are encountered in the client while making the
     *         request or handling the response.
     * @throws CosServiceException If any errors occurred in Qcloud COS while processing the
     *         request.
     *
     * @see COSClient#deleteObject(String, String)
     */
    public void deleteObject(DeleteObjectRequest deleteObjectRequest)
            throws CosClientException, CosServiceException;


    /**
     * <p>
     * Creates a new Qcloud COS bucket in the region which is set in ClientConfig
     * </p>
     * <p>
     * Every object stored in Qcloud COS is contained within a bucket. Buckets partition the
     * namespace of objects stored in Qcloud COS at the top level. Within a bucket, any name can be
     * used for objects. However, bucket names must be unique across the user of Qcloud COS.
     * </p>
     * <p>
     * Bucket ownership is similar to the ownership of Internet domain names. Within Qcloud COS,
     * only a single user owns each bucket. Once a uniquely named bucket is created in Qcloud COS,
     * organize and name the objects within the bucket in any way. Ownership of the bucket is
     * retained as long as the owner has an Qcloud COS account.
     * </p>
     * <p>
     * There are no limits to the number of objects that can be stored in a bucket. Performance does
     * not vary based on the number of buckets used. Store all objects within a single bucket or
     * organize them across several buckets.
     * </p>
     * <p>
     * Buckets cannot be nested; buckets cannot be created within other buckets.
     * </p>
     * <p>
     * Do not make bucket create or delete calls in the high availability code path of an
     * application. Create or delete buckets in a separate initialization or setup routine that runs
     * less often.
     * </p>
     * <p>
     * To create a bucket, authenticate with an account that has a valid Qcloud Access Key ID and is
     * registered with Qcloud COS. Anonymous requests are never allowed to create buckets.
     * </p>
     *
     * @param createBucketRequest The request object containing all options for creating an Qcloud
     *        COS bucket.
     * @return The newly created bucket.
     *
     * @throws CosClientException If any errors are encountered in the client while making the
     *         request or handling the response.
     * @throws CosServiceException If any errors occurred in Qcloud COS while processing the
     *         request.
     */
    public Bucket createBucket(CreateBucketRequest createBucketRequest)
            throws CosClientException, CosServiceException;

    /**
     * <p>
     * Deletes the specified bucket. All objects (and all object versions, if versioning was ever
     * enabled) in the bucket must be deleted before the bucket itself can be deleted.
     * </p>
     * <p>
     * Only the owner of a bucket can delete it, regardless of the bucket's access control policy
     * (ACL).
     * </p>
     *
     * @param deleteBucketRequest The request object containing all options for deleting an Qcloud
     *        COS bucket.
     * @throws CosClientException If any errors are encountered in the client while making the
     *         request or handling the response.
     * @throws CosServiceException If any errors occurred in Qcloud COS while processing the
     *         request.
     *
     * @see COS#deleteBucket(String)
     */
    public void deleteBucket(DeleteBucketRequest deleteBucketRequest)
            throws CosClientException, CosServiceException;

    /**
     * Initiates a multipart upload and returns an InitiateMultipartUploadResult which contains an
     * upload ID. This upload ID associates all the parts in the specific upload and is used in each
     * of your subsequent {@link #uploadPart(UploadPartRequest)} requests. You also include this
     * upload ID in the final request to either complete, or abort the multipart upload request.
     * <p>
     * <b>Note:</b> After you initiate a multipart upload and upload one or more parts, you must
     * either complete or abort the multipart upload in order to stop getting charged for storage of
     * the uploaded parts. Once you complete or abort the multipart upload Qcloud COS will release
     * the stored parts and stop charging you for their storage.
     * </p>
     *
     * @param request The InitiateMultipartUploadRequest object that specifies all the parameters of
     *        this operation.
     *
     * @return An InitiateMultipartUploadResult from Qcloud COS.
     *
     * @throws CosClientException If any errors are encountered in the client while making the
     *         request or handling the response.
     * @throws CosServiceException If any errors occurred in Qcloud COS while processing the
     *         request.
     */
    public InitiateMultipartUploadResult initiateMultipartUpload(
            InitiateMultipartUploadRequest request) throws CosClientException, CosServiceException;

    /**
     * Uploads a part in a multipart upload. You must initiate a multipart upload before you can
     * upload any part.
     * <p>
     * Your UploadPart request must include an upload ID and a part number. The upload ID is the ID
     * returned by Qcloud COS in response to your Initiate Multipart Upload request. Part number can
     * be any number between 1 and 10,000, inclusive. A part number uniquely identifies a part and
     * also defines its position within the object being uploaded. If you upload a new part using
     * the same part number that was specified in uploading a previous part, the previously uploaded
     * part is overwritten.
     * <p>
     * To ensure data is not corrupted traversing the network, specify the Content-MD5 header in the
     * Upload Part request. Qcloud COS checks the part data against the provided MD5 value. If they
     * do not match, Qcloud COS returns an error.
     * <p>
     * When you upload a part, the returned UploadPartResult contains an ETag property. You should
     * record this ETag property value and the part number. After uploading all parts, you must send
     * a CompleteMultipartUpload request. At that time Qcloud COS constructs a complete object by
     * concatenating all the parts you uploaded, in ascending order based on the part numbers. The
     * CompleteMultipartUpload request requires you to send all the part numbers and the
     * corresponding ETag values.
     * <p>
     * <b>Note:</b> After you initiate a multipart upload and upload one or more parts, you must
     * either complete or abort the multipart upload in order to stop getting charged for storage of
     * the uploaded parts. Once you complete or abort the multipart upload Qcloud COS will release
     * the stored parts and stop charging you for their storage.
     * </p>
     * 
     * @param request The UploadPartRequest object that specifies all the parameters of this
     *        operation.
     *
     * @return An UploadPartResult from Qcloud COS containing the part number and ETag of the new
     *         part.
     *
     * @throws CosClientException If any errors are encountered in the client while making the
     *         request or handling the response.
     * @throws CosServiceException If any errors occurred in Qcloud COS while processing the
     *         request.
     */
    public UploadPartResult uploadPart(UploadPartRequest uploadPartRequest)
            throws CosClientException, CosServiceException;

    /**
     * Lists the parts that have been uploaded for a specific multipart upload.
     * <p>
     * This method must include the upload ID, returned by the
     * {@link #initiateMultipartUpload(InitiateMultipartUploadRequest)} operation. This request
     * returns a maximum of 1000 uploaded parts by default. You can restrict the number of parts
     * returned by specifying the MaxParts property on the ListPartsRequest. If your multipart
     * upload consists of more parts than allowed in the ListParts response, the response returns a
     * IsTruncated field with value true, and a NextPartNumberMarker property. In subsequent
     * ListParts request you can include the PartNumberMarker property and set its value to the
     * NextPartNumberMarker property value from the previous response.
     *
     * @param request The ListPartsRequest object that specifies all the parameters of this
     *        operation.
     *
     * @return Returns a PartListing from Qcloud COS.
     *
     * @throws CosClientException If any errors are encountered in the client while making the
     *         request or handling the response.
     * @throws CosServiceException If any errors occurred in Qcloud COS while processing the
     *         request.
     */
    public PartListing listParts(ListPartsRequest request)
            throws CosClientException, CosServiceException;

    /**
     * Aborts a multipart upload. After a multipart upload is aborted, no additional parts can be
     * uploaded using that upload ID. The storage consumed by any previously uploaded parts will be
     * freed.
     *
     * @param request The AbortMultipartUploadRequest object that specifies all the parameters of
     *        this operation.
     *
     * @throws CosClientException If any errors are encountered in the client while making the
     *         request or handling the response.
     * @throws CosServiceException If any errors occurred in Qcloud COS while processing the
     *         request.
     */
    public void abortMultipartUpload(AbortMultipartUploadRequest request)
            throws CosClientException, CosServiceException;

    /**
     * Completes a multipart upload by assembling previously uploaded parts.
     * <p>
     * You first upload all parts using the {@link #uploadPart(UploadPartRequest)} method. After
     * successfully uploading all individual parts of an upload, you call this operation to complete
     * the upload. Upon receiving this request, Qcloud COS concatenates all the parts in ascending
     * order by part number to create a new object. In the CompleteMultipartUpload request, you must
     * provide the parts list. For each part in the list, you provide the part number and the ETag
     * header value, returned after that part was uploaded.
     * <p>
     * Processing of a CompleteMultipartUpload request may take several minutes to complete.
     * </p>
     *
     * @param request The CompleteMultipartUploadRequest object that specifies all the parameters of
     *        this operation.
     *
     * @return A CompleteMultipartUploadResult from COS containing the ETag for the new object
     *         composed of the individual parts.
     *
     * @throws CosClientException If any errors are encountered in the client while making the
     *         request or handling the response.
     * @throws CosServiceException If any errors occurred in Qcloud COS while processing the
     *         request.
     */
    public CompleteMultipartUploadResult completeMultipartUpload(
            CompleteMultipartUploadRequest request) throws CosClientException, CosServiceException;

    /**
     * Lists in-progress multipart uploads. An in-progress multipart upload is a multipart upload
     * that has been initiated, using the InitiateMultipartUpload request, but has not yet been
     * completed or aborted.
     * <p>
     * This operation returns at most 1,000 multipart uploads in the response by default. The number
     * of multipart uploads can be further limited using the MaxUploads property on the request
     * parameter. If there are additional multipart uploads that satisfy the list criteria, the
     * response will contain an IsTruncated property with the value set to true. To list the
     * additional multipart uploads use the KeyMarker and UploadIdMarker properties on the request
     * parameters.
     *
     * @param request The ListMultipartUploadsRequest object that specifies all the parameters of
     *        this operation.
     *
     * @return A MultipartUploadListing from Qcloud COS.
     *
     * @throws CosClientException If any errors are encountered in the client while making the
     *         request or handling the response.
     * @throws CosServiceException If any errors occurred in Qcloud COS while processing the
     *         request.
     */
    public MultipartUploadListing listMultipartUploads(ListMultipartUploadsRequest request)
            throws CosClientException, CosServiceException;

    /**
     * <p>
     * Returns a list of summary information about the objects in the specified buckets. List
     * results are <i>always</i> returned in lexicographic (alphabetical) order.
     * </p>
     * <p>
     * Because buckets can contain a virtually unlimited number of keys, the complete results of a
     * list query can be extremely large. To manage large result sets, Qcloud COS uses pagination to
     * split them into multiple responses. Always check the {@link ObjectListing#isTruncated()}
     * method to see if the returned listing is complete or if additional calls are needed to get
     * more results. Alternatively, use the {@link COS#listNextBatchOfObjects(ObjectListing)} method
     * as an easy way to get the next page of object listings.
     * </p>
     * <p>
     * The total number of keys in a bucket doesn't substantially affect list performance.
     * </p>
     *
     * @param bucketName The name of the Qcloud COS bucket to list.
     *
     * @return A listing of the objects in the specified bucket, along with any other associated
     *         information, such as common prefixes (if a delimiter was specified), the original
     *         request parameters, etc.
     *
     * @throws CosClientException If any errors are encountered in the client while making the
     *         request or handling the response.
     * @throws CosServiceException If any errors occurred in Qcloud COS while processing the
     *         request.
     *
     * @see COS#listObjects(String, String)
     * @see COS#listObjects(ListObjectsRequest)
     */
    public ObjectListing listObjects(String bucketName)
            throws CosClientException, CosServiceException;

    /**
     * <p>
     * Returns a list of summary information about the objects in the specified bucket. Depending on
     * request parameters, additional information is returned, such as common prefixes if a
     * delimiter was specified. List results are <i>always</i> returned in lexicographic
     * (alphabetical) order.
     * </p>
     * <p>
     * Because buckets can contain a virtually unlimited number of keys, the complete results of a
     * list query can be extremely large. To manage large result sets, Qcloud COS uses pagination to
     * split them into multiple responses. Always check the {@link ObjectListing#isTruncated()}
     * method to see if the returned listing is complete or if additional calls are needed to get
     * more results. Alternatively, use the {@link COS#listNextBatchOfObjects(ObjectListing)} method
     * as an easy way to get the next page of object listings.
     * </p>
     * <p>
     * For example, consider a bucket that contains the following keys:
     * <ul>
     * <li>"foo/bar/baz"</li>
     * <li>"foo/bar/bash"</li>
     * <li>"foo/bar/bang"</li>
     * <li>"foo/boo"</li>
     * </ul>
     * If calling <code>listObjects</code> with a <code>prefix</code> value of "foo/" and a
     * <code>delimiter</code> value of "/" on this bucket, an <code>ObjectListing</code> is returned
     * that contains one key ("foo/boo") and one entry in the common prefixes list ("foo/bar/"). To
     * see deeper into the virtual hierarchy, make another call to <code>listObjects</code> setting
     * the prefix parameter to any interesting common prefix to list the individual keys under that
     * prefix.
     * </p>
     * <p>
     * The total number of keys in a bucket doesn't substantially affect list performance.
     * </p>
     *
     * @param bucketName The name of the Qcloud COS bucket to list.
     * @param prefix An optional parameter restricting the response to keys beginning with the
     *        specified prefix. Use prefixes to separate a bucket into different sets of keys,
     *        similar to how a file system organizes files into directories.
     *
     * @return A listing of the objects in the specified bucket, along with any other associated
     *         information, such as common prefixes (if a delimiter was specified), the original
     *         request parameters, etc.
     *
     * @throws CosClientException If any errors are encountered in the client while making the
     *         request or handling the response.
     * @throws CosServiceException If any errors occurred in Qcloud COS while processing the
     *         request.
     *
     * @see COS#listObjects(String)
     * @see COS#listObjects(ListObjectsRequest)
     */
    public ObjectListing listObjects(String bucketName, String prefix)
            throws CosClientException, CosServiceException;

    /**
     * <p>
     * Returns a list of summary information about the objects in the specified bucket. Depending on
     * the request parameters, additional information is returned, such as common prefixes if a
     * delimiter was specified. List results are <i>always</i> returned in lexicographic
     * (alphabetical) order.
     * </p>
     * <p>
     * Because buckets can contain a virtually unlimited number of keys, the complete results of a
     * list query can be extremely large. To manage large result sets, Qcloud COS uses pagination to
     * split them into multiple responses. Always check the {@link ObjectListing#isTruncated()}
     * method to see if the returned listing is complete or if additional calls are needed to get
     * more results. Alternatively, use the {@link COS#listNextBatchOfObjects(ObjectListing)} method
     * as an easy way to get the next page of object listings.
     * </p>
     * <p>
     * Calling {@link ListObjectsRequest#setDelimiter(String)} sets the delimiter, allowing groups
     * of keys that share the delimiter-terminated prefix to be included in the returned listing.
     * This allows applications to organize and browse their keys hierarchically, similar to how a
     * file system organizes files into directories. These common prefixes can be retrieved through
     * the {@link ObjectListing#getCommonPrefixes()} method.
     * </p>
     * <p>
     * For example, consider a bucket that contains the following keys:
     * <ul>
     * <li>"foo/bar/baz"</li>
     * <li>"foo/bar/bash"</li>
     * <li>"foo/bar/bang"</li>
     * <li>"foo/boo"</li>
     * </ul>
     * If calling <code>listObjects</code> with a prefix value of "foo/" and a delimiter value of
     * "/" on this bucket, an <code>ObjectListing</code> is returned that contains one key
     * ("foo/boo") and one entry in the common prefixes list ("foo/bar/"). To see deeper into the
     * virtual hierarchy, make another call to <code>listObjects</code> setting the prefix parameter
     * to any interesting common prefix to list the individual keys under that prefix.
     * </p>
     * <p>
     * The total number of keys in a bucket doesn't substantially affect list performance.
     * </p>
     *
     * @param listObjectsRequest The request object containing all options for listing the objects
     *        in a specified bucket.
     *
     * @return A listing of the objects in the specified bucket, along with any other associated
     *         information, such as common prefixes (if a delimiter was specified), the original
     *         request parameters, etc.
     *
     * @throws CosClientException If any errors are encountered in the client while making the
     *         request or handling the response.
     * @throws CosServiceException If any errors occurred in Qcloud COS while processing the
     *         request.
     *
     * @see COS#listObjects(String)
     * @see COS#listObjects(String, String)
     */
    public ObjectListing listObjects(ListObjectsRequest listObjectsRequest)
            throws CosClientException, CosServiceException;

    /**
     * <p>
     * Provides an easy way to continue a truncated object listing and retrieve the next page of
     * results.
     * </p>
     * <p>
     * To continue the object listing and retrieve the next page of results, call the initial
     * {@link ObjectListing} from one of the <code>listObjects</code> methods. If truncated
     * (indicated when {@link ObjectListing#isTruncated()} returns <code>true</code>), pass the
     * <code>ObjectListing</code> back into this method in order to retrieve the next page of
     * results. Continue using this method to retrieve more results until the returned
     * <code>ObjectListing</code> indicates that it is not truncated.
     * </p>
     * 
     * @param previousObjectListing The previous truncated <code>ObjectListing</code>. If a
     *        non-truncated <code>ObjectListing</code> is passed in, an empty
     *        <code>ObjectListing</code> is returned without ever contacting Qcloud COS.
     *
     * @return The next set of <code>ObjectListing</code> results, beginning immediately after the
     *         last result in the specified previous <code>ObjectListing</code>.
     *
     * @throws CosClientException If any errors are encountered in the client while making the
     *         request or handling the response.
     * @throws CosServiceException If any errors occurred in Qcloud COS while processing the
     *         request.
     *
     * @see COS#listObjects(String)
     * @see COS#listObjects(String, String)
     * @see COS#listObjects(ListObjectsRequest)
     * @see COS#listNextBatchOfObjects(ListNextBatchOfObjectsRequest)
     */
    public ObjectListing listNextBatchOfObjects(ObjectListing previousObjectListing)
            throws CosClientException, CosServiceException;

    /**
     * <p>
     * Provides an easy way to continue a truncated object listing and retrieve the next page of
     * results.
     * </p>
     * <p>
     * To continue the object listing and retrieve the next page of results, call the initial
     * {@link ObjectListing} from one of the <code>listObjects</code> methods. If truncated
     * (indicated when {@link ObjectListing#isTruncated()} returns <code>true</code>), pass the
     * <code>ObjectListing</code> back into this method in order to retrieve the next page of
     * results. Continue using this method to retrieve more results until the returned
     * <code>ObjectListing</code> indicates that it is not truncated.
     * </p>
     * 
     * @param listNextBatchOfObjectsRequest The request object for listing next batch of objects
     *        using the previous truncated <code>ObjectListing</code>. If a non-truncated
     *        <code>ObjectListing</code> is passed in by the request object, an empty
     *        <code>ObjectListing</code> is returned without ever contacting Qcloud COS.
     *
     * @return The next set of <code>ObjectListing</code> results, beginning immediately after the
     *         last result in the specified previous <code>ObjectListing</code>.
     *
     * @throws CosClientException If any errors are encountered in the client while making the
     *         request or handling the response.
     * @throws CosServiceException If any errors occurred in Qcloud COS while processing the
     *         request.
     *
     * @see COS#listObjects(String)
     * @see COS#listObjects(String, String)
     * @see COS#listObjects(ListObjectsRequest)
     * @see COS#listNextBatchOfObjects(ObjectListing)
     */
    public ObjectListing listNextBatchOfObjects(
            ListNextBatchOfObjectsRequest listNextBatchOfObjectsRequest)
                    throws CosClientException, CosServiceException;


    public CopyObjectResult copyObject(CopyObjectRequest copyObjectRequest)
            throws CosClientException, CosServiceException;

}
