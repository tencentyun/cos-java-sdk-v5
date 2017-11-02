package com.qcloud.cos.model.lifecycle;

import java.util.List;

/**
 * A logical AND of two or more predicates of type {@link LifecycleFilterPredicate}.
 * The Lifecycle Rule will apply to any object matching all of the predicates configured inside the And operator.
 *
 * The {@link LifecycleAndOperator} can contain at most one {@link LifecyclePrefixPredicate} and any number of {@link LifecycleTagPredicate}s.
 */
public final class LifecycleAndOperator extends LifecycleNAryOperator {

    public LifecycleAndOperator(List<LifecycleFilterPredicate> operands) {
        super(operands);
    }

    @Override
    public void accept(LifecyclePredicateVisitor lifecyclePredicateVisitor) {
        lifecyclePredicateVisitor.visit(this);
    }
}