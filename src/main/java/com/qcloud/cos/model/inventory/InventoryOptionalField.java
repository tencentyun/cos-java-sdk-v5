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
package com.qcloud.cos.model.inventory;

/**
 * The optional fields that can be included in the inventory results.
 */
public enum InventoryOptionalField {

    Size("Size"),

    LastModifiedDate("LastModifiedDate"),

    StorageClass("StorageClass"),

    ETag("ETag"),

    IsMultipartUploaded("IsMultipartUploaded"),

    ReplicationStatus("ReplicationStatus"),

    Tag("Tag"),

    ;

    private final String field;

    InventoryOptionalField(String field) {
        this.field = field;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return field;
    }

    /**
     * Use this in place of valueOf.
     *
     * @param value real value
     * @return InventoryOptionalField corresponding to the value
     * @throws IllegalArgumentException If the specified value does not map to one of the known values in this enum.
     */
    public static InventoryOptionalField fromValue(String value) {
        if (value == null || "".equals(value)) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }

        for (InventoryOptionalField enumEntry : InventoryOptionalField.values()) {
            if (enumEntry.toString().equals(value)) {
                return enumEntry;
            }
        }

        throw new IllegalArgumentException("Cannot create enum from " + value + " value!");
    }
}
