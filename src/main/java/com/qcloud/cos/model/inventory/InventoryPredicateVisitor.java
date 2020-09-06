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
 * Interface to invoke specific behavior based on the type of {@link InventoryFilterPredicate} visited.
 *
 * When an implementation of this visitor is passed to an
 * {@link InventoryFilterPredicate#accept(InventoryPredicateVisitor)} method,
 * the visit method most applicable to that element is invoked.
 */
public interface InventoryPredicateVisitor {

    /**
     * Implement this method to add behaviour performed when
     * {@link InventoryPrefixPredicate} is visited.
     */
    public void visit(InventoryPrefixPredicate inventoryPrefixPredicate);
}
