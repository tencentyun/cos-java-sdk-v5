/*
 * Copyright 2011-2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *    http://aws.amazon.com/apache2.0
 *
 * This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qcloud.cos.model.inventory;

import java.io.Serializable;

/**
 * Information about where to publish the inventory results.
 */
public class InventoryDestination implements Serializable {

    /**
     * Contains the COS destination information of where inventory results are published.
     */
    private InventoryCosBucketDestination cosBucketDestination;

    /**
     * Returns the {@link InventoryCosBucketDestination} which contains S3 bucket destination information
     * of where inventory results are published.
     */
    public InventoryCosBucketDestination getCosBucketDestination() {
        return cosBucketDestination;
    }

    /**
     * Sets the {@link InventoryCosBucketDestination} which contains S3 bucket destination information
     * of where inventory results are published.
     */
    public void setCosBucketDestination(InventoryCosBucketDestination cosBucketDestination) {
        this.cosBucketDestination = cosBucketDestination;
    }

    /**
     * Sets the {@link InventoryCosBucketDestination} which contains S3 bucket destination information
     * of where inventory results are published.
     * This {@link InventoryDestination} object is returned for method chaining.
     */
    public InventoryDestination withCosBucketDestination(InventoryCosBucketDestination cosBucketDestination) {
        setCosBucketDestination(cosBucketDestination);
        return this;
    }
}
