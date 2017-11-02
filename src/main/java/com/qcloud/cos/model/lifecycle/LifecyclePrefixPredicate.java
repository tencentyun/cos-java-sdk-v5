package com.qcloud.cos.model.lifecycle;


/**
 * A {@link LifecycleFilterPredicate} class to represent the
 * prefix identifying one or more objects to which the
 */
public final class LifecyclePrefixPredicate extends LifecycleFilterPredicate {

    private final String prefix;

    public LifecyclePrefixPredicate(String prefix) {
        this.prefix = prefix;
    }

    /**
     * Returns the key prefix for which the
     */
    public String getPrefix() {
        return prefix;
    }

    @Override
    public void accept(LifecyclePredicateVisitor lifecyclePredicateVisitor) {
        lifecyclePredicateVisitor.visit(this);
    }
}
