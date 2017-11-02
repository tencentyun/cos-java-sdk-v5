package com.qcloud.cos.model.lifecycle;

import java.io.Serializable;

/**
 * The {@link LifecycleFilter} is used to identify objects that a Lifecycle Rule applies to.
 *
 * This predicate in {@link LifecycleFilter} should be one of
 * {@link LifecyclePrefixPredicate}, {@link LifecycleTagPredicate}, or
 * {@link LifecycleAndOperator}.
 */
public class LifecycleFilter implements Serializable{
    private LifecycleFilterPredicate predicate;

    public LifecycleFilter() {}

    public LifecycleFilter(LifecycleFilterPredicate predicate) {
        this.predicate = predicate;
    }

    /**
     * Returns the {@link LifecycleFilterPredicate} to be applied to {@link BucketLifecycleConfiguration.Rule}.
     *
     * The predicate is one of {@link LifecyclePrefixPredicate},
     * {@link LifecycleTagPredicate} or
     * {@link LifecycleAndOperator} type.
     */
    public LifecycleFilterPredicate getPredicate() {
        return predicate;
    }

    /**
     * Sets the {@link LifecycleFilterPredicate} to be applied to {@link BucketLifecycleConfiguration.Rule}.
     *
     * The predicate should be one of {@link LifecyclePrefixPredicate},
     * {@link LifecycleTagPredicate} or
     * {@link LifecycleAndOperator} type.
     *
     * @param predicate An object of type {@link LifecycleFilterPredicate}.
     */
    public void setPredicate(LifecycleFilterPredicate predicate) {
        this.predicate = predicate;
    }

    /**
     * Sets the {@link LifecycleFilterPredicate} to be applied to {@link BucketLifecycleConfiguration.Rule} and returns the object
     * for method chaining.
     *
     * The predicate should be one of {@link LifecyclePrefixPredicate},
     * {@link LifecycleTagPredicate} or
     * {@link LifecycleAndOperator} type.
     *
     * @param predicate An object of type {@link LifecycleFilterPredicate}.
     *
     * @return This object for method chaining.
     */
    public LifecycleFilter withPredicate(LifecycleFilterPredicate predicate) {
        setPredicate(predicate);
        return this;
    }
}