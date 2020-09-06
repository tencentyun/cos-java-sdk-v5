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


package com.qcloud.cos.model;

import java.io.Serializable;

import com.qcloud.cos.internal.CosServiceRequest;

public class SetBucketAclRequest extends CosServiceRequest implements Serializable {
   /** The name of the bucket whose ACL is being set. */
   private String bucketName;

   /** The custom ACL to apply to the specified bucket. */
   private AccessControlList acl;

   /** The canned ACL to apply to the specified bucket. */
   private CannedAccessControlList cannedAcl;

   /**
    * Constructs a new SetBucketAclRequest object, ready to set the specified
    * ACL on the specified bucket when this request is executed.
    *
    * @param bucketName
    *            The name of the bucket whose ACL will be set by this request.
    * @param acl
    *            The custom Access Control List containing the access rules to
    *            apply to the specified bucket when this request is executed.
    */
   public SetBucketAclRequest(String bucketName, AccessControlList acl) {
       this.bucketName = bucketName;
       this.acl = acl;
       this.cannedAcl = null;
   }

   /**
    * Constructs a new SetBucketAclRequest object, ready to set the specified
    * canned ACL on the specified bucket when this request is executed.
    *
    * @param bucketName
    *            The name of the bucket whose ACL will be set by this request.
    * @param acl
    *            The Canned Access Control List to apply to the specified
    *            bucket when this request is executed.
    */
   public SetBucketAclRequest(String bucketName, CannedAccessControlList acl) {
       this.bucketName = bucketName;
       this.acl = null;
       this.cannedAcl = acl;
   }

   /**
    * Returns the name of the bucket whose ACL will be modified by this request
    * when executed.
    *
    * @return The name of the bucket whose ACL will be modified by this request
    *         when executed.
    */
   public String getBucketName() {
       return bucketName;
   }

   /**
    * Returns the custom ACL to be applied to the specified bucket when this
    * request is executed. A request can use either a custom ACL or a canned
    * ACL, but not both.
    *
    * @return The custom ACL to be applied to the specified bucket when this
    *         request is executed.
    */
   public AccessControlList getAcl() {
       return acl;
   }

   /**
    * Returns the canned ACL to be applied to the specified bucket when this
    * request is executed. A request can use either a custom ACL or a canned
    * ACL, but not both.
    *
    * @return The canned ACL to be applied to the specified bucket when this
    *         request is executed.
    */
   public CannedAccessControlList getCannedAcl() {
       return cannedAcl;
   }
}