package com.qcloud.cos.model.lifecycle;

import java.io.Serializable;

/**
 * Base class to represent the root predicate in
 * {@link LifecycleFilter} class.
 *
 * @see LifecyclePrefixPredicate
 * @see LifecycleTagPredicate
 * @see LifecycleAndOperator
 */
public abstract class LifecycleFilterPredicate implements Serializable {

    /**
     * Helper method that accepts an implemenation of {@link LifecyclePredicateVisitor}
     * and invokes the most applicable visit method in the visitor.
     */
    public abstract void accept(LifecyclePredicateVisitor lifecyclePredicateVisitor);
}