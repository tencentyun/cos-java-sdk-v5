package com.qcloud.cos.model.Tag;

import com.qcloud.cos.model.lifecycle.LifecycleFilterPredicate;
import com.qcloud.cos.model.lifecycle.LifecyclePredicateVisitor;

/**
 * A {@link LifecycleFilterPredicate} class to represent the {@link Tag} object
 * that must exist in the object's tag set in order for the
 * {@link com.qcloud.cos.modle.BucketLifecycleConfiguration.Rule} to apply.
 */
public final class LifecycleTagPredicate extends LifecycleFilterPredicate {

    private final Tag tag;

    public LifecycleTagPredicate(Tag tag) {
        this.tag = tag;
    }

    public Tag getTag() {
        return tag;
    }

    @Override
    public void accept(LifecyclePredicateVisitor lifecyclePredicateVisitor) {
        lifecyclePredicateVisitor.visit(this);
    }
}