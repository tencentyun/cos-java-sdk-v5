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
import java.util.LinkedList;
import java.util.List;

public class BucketDomainConfiguration implements Serializable {
    private List<DomainRule> domainRules = new LinkedList<>();
    /* the x-cos-domain-txt-verification reponse header value */
    private String domainTxtVerification;
    public BucketDomainConfiguration() { }

    /**
     * Set the list of domain rules that can be used for configuring custom domain name
     *
     * @param domainRules
     *            The list of domain rules that can be used for configuring custom domain name
     */
    public void setDomainRules(List<DomainRule> domainRules) {
        this.domainRules = domainRules;
    }

    /**
     * Return the list of custom domain name rules that can be used for configuring
     * custom domain name
     */
    public List<DomainRule> getDomainRules() {
        return domainRules;
    }

    /**
     * Set the list of domain rules that can be used for configuring custom domain name
     *
     * @param domainRules
     *            The list of domain rules that can be used for configuring
     *            custom domain name.
     * @return A reference to this object(BucketDomainConfiguration) for method
     *         chaining.
     *
     */
    public BucketDomainConfiguration withDomainRules(List<DomainRule> domainRules) {
        this.domainRules = domainRules;
        return this;
    }

    public String getDomainTxtVerification() {
        return domainTxtVerification;
    }

    public void setDomainTxtVerification(String domainTxtVerification) {
        this.domainTxtVerification = domainTxtVerification;
    }
}
