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


package com.qcloud.cos.exception;

import com.qcloud.cos.transfer.PauseStatus;

public class PauseException extends CosClientException {

    private static final long serialVersionUID = 1L;

    /**
     * The reason why the pause operation failed.
     */
    private final PauseStatus status;

    public PauseException(PauseStatus status) {
        super("Failed to pause operation; status=" + status);
        if (status == null || status == PauseStatus.SUCCESS)
            throw new IllegalArgumentException();
        this.status = status;
    }

    /**
     * Returns more information on why the pause failed.
     */
    public PauseStatus getPauseStatus() {
        return status;
    }

    /**
     * {@inheritDoc} An paused exception is not intended to be retried.
     */
    @Override
    public boolean isRetryable() {
        return false;
    }
}
